/*******************************************************************************
 * Copyright (c) 2011 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package ac.soton.eventb.emf.core.extension.navigator.refiner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBNamed;
import org.eventb.emf.core.EventBNamedCommentedComponentElement;
import org.eventb.emf.core.EventBObject;

/**
 * Abstract Element Refiner for EventB EMF Extensions.
 * This should be extended for each Extension ID
 * 
 * @author cfsnook
 *
 */
public abstract class AbstractElementRefiner {

	/**
	 * A list of the EClasses which should not be copied into the refinement
	 */
	private	List<EClass> filterList = new ArrayList<EClass>();

	/**
	 * Extenders provide this method to populate the list of EClasses 
	 * which should not be copied into a refinement.
	 *  
	 * @param filterList
	 */
	protected abstract void populateFilterByTypeList(List<EClass> filterList);
	
	/**
	 * This enumeration gives the options for dealing with references
	 * CHAIN = the refined reference will target the abstract parent object that contained the abstract reference
	 * EQUIV = the refined reference will target the refined version of the target of the abstract reference if it exists,
	 *  when no refined version of the target exists (e.g. where the target is a filtered class or in another component) then this option acts like COPY
	 * COPY = the refined reference will target the exact same object that the abstract reference did
	 * DROP = the refined reference is left unset (this is the default behaviour if no entry is given for a reference feature)
	 *
	 */
	public enum RefHandling {
		COPY,CHAIN,EQUIV,DROP
	}
	
	/**
	 * this is an internal flag which is set in order to disable CHAIN references.
	 * (see clone)
	 */
	private boolean breakChain = false;

	private boolean clone = false;
	
	/**
	 * A map of the references which need to be dealt with in the new refined model.
	 * For each EReference, the RefHandling indicates whether it should be dealt with as a reference back to 
	 * the source element (e.g. refines) or a normal reference within the same resource level which
	 * will be copied to simulate the abstract one, or it should be copied or dropped in the refined model.
	 */
	private Map<EReference,RefHandling> referencemap = new HashMap<EReference,RefHandling>();

	
	/**
	 * Extenders provide this method to populate the reference mapping with a list of 
	 * EReference features in their model extension. For each one indicate whether it should be dealt 
	 * with as a reference to the original source element (e.g. refines).
	 * 
	 */
	protected abstract void populateReferenceMap(Map<EReference,RefHandling> referencemap);
	
	/**
	 * This gets an object from the contents of the concrete parent which is considered to be equivalent 
	 * to the given abstract object. 
	 * 
	 * (In this context, equivalence means that if a reference in the abstract model refers to the abstract object,
	 * then a corresponding reference in the concrete model should refer to the returned equivalent object).
	 *
	 * This default implementation returns the first element, of the same class, discovered with the same name as the abstract object 
	 * (when a specific feature is provided this is sufficient (allowing disconnected elements to be matched), otherwise the full parentage is checked)
	 * or,
	 * failing that, the first element of the same class discovered with a refines relationship to the  abstract object.
	 * 
	 * Extenders may override this default implementation to give other equivalances.
	 * 
	 * @param concreteParent
	 * @param abstractObject
	 * @return
	 */
	public EventBObject getEquivalentObject(EObject concreteParent, EObject abstractObject) {
		return getEquivalentObject(concreteParent, null, abstractObject);
	}
	
	public EventBObject getEquivalentObject(EObject concreteParent, EStructuralFeature feature, EObject abstractObject) {

		if (abstractObject == null) return null;
		
		if (abstractObject instanceof EventBNamedCommentedComponentElement && concreteParent.eClass()==abstractObject.eClass()){
			return (EventBObject) concreteParent;
		}
		
		List<Object> contents = new ArrayList<Object>();
		contents.add(concreteParent);
		if (feature != null){
			Object featureValue =	concreteParent.eGet(feature);
			if (featureValue instanceof EList<?>) {
				contents.addAll(((EList<?>) concreteParent.eGet(feature)));
			}
		}else{
			Iterator<EObject> iter = concreteParent.eAllContents();
			while (iter.hasNext()){
				contents.add(iter.next());
			}
		}

		EClass clazz = abstractObject.eClass();
		EStructuralFeature nameFeature = clazz.getEStructuralFeature("name");
		Object name = nameFeature==null? null : abstractObject.eGet(nameFeature);
		EStructuralFeature refinesFeature = clazz.getEStructuralFeature("refines");
		
		for (Object possible : contents){
			if (possible instanceof EObject && ((EObject) possible).eClass() == clazz){
				
				// if name is the same..
				if (nameFeature!=null && name!=null && name.equals(((EObject) possible).eGet(nameFeature))){
					if (feature!=null){  //if looking in specific feature this is enough (allows disconnected elements to be matched)
						return (EventBObject) possible;
					}else{
						//when not looking in a specific feature be more specific by checking parentage
						if (abstractObject.eIsProxy()){
							ResourceSet rs = concreteParent.eResource().getResourceSet();
							abstractObject = EcoreUtil.resolve(abstractObject, rs);
						}
						EObject abstractsParent = abstractObject.eContainer();
						// get a refiner for the ePackage containing this abstract object
						AbstractElementRefiner refiner = ElementRefinerRegistry.getRegistry().getRefiner(abstractsParent);
						if (refiner==null) continue;
						EventBObject equivalentParent = refiner.getEquivalentObject(concreteParent, abstractsParent);
						if (((EObject) possible).eContainer() == equivalentParent){
							return (EventBObject) possible;
						}
					}
				}

				// if refines the abstract object
				if (refinesFeature!=null && !refinesFeature.isMany() && ((EObject) possible).eGet(refinesFeature) == abstractObject){
					return (EventBObject) possible;
				}
				
			}
		}
		return null;
	}
		
	/**
	 * Used to get the key (source element) from the copier map using the value (target element)
	 * 
	 * @param map
	 * @param value
	 * @return
	 */
	private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (value.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	

	/**
	 * Constructor.
	 */
	protected AbstractElementRefiner() {
		populateFilterByTypeList(filterList);
		populateReferenceMap(referencemap);
	}

	/**
	 * Creates a clone from the given source element extracting it from the refinement chain. 
	 * This is equivalent to refine except that no vertical chain references (such as refines)
	 *  will be set to reference the source model. (i.e. the clone is independent and not a refinement of anything)
	 *  Any other references will be handled as in refines.
	 * 
	 * The source element must be contained in a resource.
	 * 
	 * @param source element
	 * @param target component (optional - used for copying references to external elements) 
	 * @return a map from the source elements to the target elements
	 */
	public Map<EObject,EObject> cloneAndExtractFromRefinementChain(EventBObject sourceElement,  EventBNamedCommentedComponentElement targetComponent, URI concreteResourceUri){
		breakChain = true;
		Map<EObject, EObject> ret = refine(null, sourceElement, targetComponent, concreteResourceUri);
		breakChain = false;
		return ret;
	}
	
	/**
	 * Creates a clone from the given source element adding it as an alternative refinement to the source element one. 
	 * This is equivalent to refine except that vertical chain references (such as refines)
	 *  are copied instead of extended (i.e. the clone is an exact copy of the source element in all ways, not a further refinement).
	 *  Any other references will be handled as in refines.
	 * 
	 * The source element must be contained in a resource.
	 * 
	 * @param source element
	 * @param target component (optional - used for copying references to external elements) 
	 * @return a map from the source elements to the target elements
	 */
	public Map<EObject,EObject> cloneAsAlternativeRefinement(EventBObject sourceElement,  EventBNamedCommentedComponentElement targetComponent, URI concreteResourceUri){
		clone = true;
		Map<EObject, EObject> ret = refine(null, sourceElement, targetComponent, concreteResourceUri);
		clone = false;
		return ret;
	}

	
	/**
	 * Creates a refined element from the given abstract one and a separate abstract URI.
	 * In this case the abstract element need not be contained in a resource.
	 * It will be used (copied) to make a refined element but any references to the abstract element 
	 * (e.g. refines) will be based on the given abstract URI.
	 * 
	 * @param abstractUri - uri of an element in the abstract component resource (used to complete references)
	 * @param abstractElement  - element to be copied (does not need to be contained)
	 * @param concrete component (optional - used for copying references to external elements) 
	 * @return a map from the abstract elements to the concrete elements
	 */
	public Map<EObject,EObject> refine(URI abstractUri, EventBObject abstractElement, EventBNamedCommentedComponentElement concreteComponent) {
		return refine(abstractUri, abstractElement, concreteComponent, null);
	}
	
	/**
	 * Creates a refined element from the given abstract one.
	 * The abstract element must be contained in a resource.
	 * 
	 * @param abstract element
	 * @param concrete component
	 * @return a map from the abstract elements to the concrete elements
	 */
	public Map<EObject,EObject> refine(EventBObject abstractElement,  EventBNamedCommentedComponentElement concreteComponent){
		return refine(null, abstractElement, concreteComponent, null);
	}

		
	/**
	 * Creates a refined element from the given abstract one.
	 * 
	 * Either the abstract element must be contained in a resource or if not,
	 * A separate URI must be given which can be used to derive a full platform uri for the refined element.
	 * 
	 * Optionally, a containing concrete Component may be given in order to find equivalent reference targets
	 * when there may be references that target elements outside of the newly created concrete elements. 
	 * If all EQUIV references are contained within the copied element (e.g. for refining a complete component) this may be null.
	 * 
	 * @param abstract element
	 * @return
	 */
	private Map<EObject,EObject> refine(URI abstractUri, EventBObject abstractElement,  EventBNamedCommentedComponentElement concreteComponent, URI concreteResourceURI) {
		if (abstractUri==null){
			abstractUri = EcoreUtil.getURI(abstractElement);
		}

		if (!isCorrectURIType(abstractUri,abstractElement.eClass())){
			return null;
		}
		
		Copier copier = new Copier(true,false);
		// create refined Component using copier.
		// this does a deep copy of all the children and properties of the copied element
		// but it does not copy any references
		EventBElement concreteEventBElement = (EventBElement) copier.copy(abstractElement); 
		
		//get all the content of the root Element including itself
		EList<EObject> contents = concreteEventBElement.getAllContained(CorePackage.Literals.EVENT_BELEMENT, true);
		contents.add(concreteEventBElement);
		
		// iterate through the contents finding the correct refiner for copying references and filtering elements
		for (EObject concreteElement : contents){
			AbstractElementRefiner refiner = ElementRefinerRegistry.getRegistry().getRefiner(concreteElement);
			if (refiner==null) continue;
			
			// Set up references in the new concrete model  (note that copier.copyReferences() does not work for this)
			// (this looks for references corresponding to those declared in the reference map
			//   and copy them in the appropriate way according to multiplicity and the reference map).
			if (abstractElement instanceof EventBNamedCommentedComponentElement) {
				if (concreteComponent == null){
					concreteComponent = (EventBNamedCommentedComponentElement) copier.get(abstractElement);
				}
			}
			if (concreteResourceURI==null && concreteComponent!=null ){
				if (concreteComponent.eResource()==null){
					concreteResourceURI= URI.createPlatformResourceURI(abstractUri.toPlatformString(true), true);
					concreteResourceURI = concreteResourceURI.trimFragment();
					String fileExtension = concreteResourceURI.fileExtension();
					concreteResourceURI = concreteResourceURI.trimSegments(1);	
					concreteResourceURI = concreteResourceURI.appendSegment(concreteComponent.getName());
					concreteResourceURI = concreteResourceURI.appendFileExtension(fileExtension);
					concreteResourceURI = concreteResourceURI.appendFragment(EcoreUtil.getURI(concreteComponent).fragment());
				}else{
					concreteResourceURI = EcoreUtil.getURI(concreteComponent);
				}
			}
			refiner.copyReferences(concreteElement, copier, abstractUri, concreteResourceURI, concreteComponent, concreteComponent==null? null: concreteComponent.getName() );
			
			//having copied everything we may need to remove some kinds of elements that are not supposed to be copied into a refinement
			refiner.filterElements(concreteElement);
			
		}
		
		return copier;
	}

	/*
	 * This removes any elements that are of a type (EClass) listed in filterList
	 */
	@SuppressWarnings("unchecked")
	protected void filterElements(EObject element) {
		if (!(element instanceof EventBElement)) return;
		List<EObject> removeList = new ArrayList<EObject>();
		for (EClass removeClass : filterList){
			removeList.addAll(((EventBElement)element).getAllContained(removeClass, true));
		}
		for (EObject eObject : removeList){
			if (eObject != null){
				EStructuralFeature feature = eObject.eContainingFeature();
				EObject parent = eObject.eContainer();
				if (parent != null && feature!= null && parent.eClass().getEStructuralFeatures().contains(feature)) {
					if (feature.isMany()){
						((EList<EObject>) parent.eGet(feature)).remove(eObject);
					}else{
						parent.eUnset(feature);
					}
				}
			}
		}
	}


	/**
	 * 
	 * This constructs references for the given concreteElement according to the reference map.
	 * The copier map is used to find corresponding elements in the abstract model.
	 * The abstract URI is used to construct suitable proxy elements for references - hence it is not necessary for the 
	 * abstract or concrete elements to be contained in a resource.
	 * 
	 * @param concreteElement
	 * @param copier
	 * @param abstractUri
	 * @param concreteResourceURI
	 * @param concreteComponent
	 * @param concreteComponentName
	 */
	@SuppressWarnings("unchecked")
	protected void copyReferences(EObject concreteElement, Copier copier, URI abstractUri, 
			URI concreteResourceURI, EventBNamedCommentedComponentElement concreteComponent, String concreteComponentName ) {
		EReference referenceFeature;
		EventBElement abstractElement = (EventBElement) getKeyByValue(copier, (EventBElement)concreteElement);

		for (Entry<EReference, RefHandling> referenceEntry : referencemap.entrySet()){
			referenceFeature = referenceEntry.getKey(); 
			if (referenceFeature.getEContainingClass().isSuperTypeOf(concreteElement.eClass())){

				//NOTE: *** Cannot use the concrete elements to create URIs because their parentage is not complete ***
				RefHandling handling = referenceEntry.getValue();
				if (referenceFeature.isMany()){
					EList<EObject> refList;
					if (handling==RefHandling.CHAIN){
						refList = new BasicEList<EObject>();
						refList.add(null);
					}else{
						refList = (EList<EObject>)(getKeyByValue(copier, concreteElement)).eGet(referenceFeature);
					}
					for (EObject abstractReferencedElement : refList){		
						EObject newValue = getNewReferenceValue(
								constructElementURI(abstractUri,abstractElement),
								(EventBObject)abstractElement,
								(EventBObject)abstractReferencedElement,
								concreteResourceURI, concreteComponent, concreteComponentName,
								referenceEntry.getValue(), copier);
						if (newValue!=null){
							((EList<EObject>)concreteElement.eGet(referenceFeature)).add(newValue);
						}
					}
				}else{
					if (referenceFeature.getEType() instanceof EClass){
						EObject newValue = getNewReferenceValue(
								constructElementURI(abstractUri,abstractElement),
								(EventBObject)abstractElement,
								(EventBObject)abstractElement.eGet(referenceFeature,false),
								concreteResourceURI, concreteComponent, concreteComponentName,
								referenceEntry.getValue(), copier);
						if (newValue!=null){
							concreteElement.eSet(referenceFeature, newValue);
						}
					}
				}
			}
		}
	}

	/**
	 * constructs a uri for an orphaned element using the given potential parent uri
	 * @param uri
	 * @param element
	 * @return
	 */
	private URI constructElementURI(URI parentUri, EventBElement element) {
		URI uri = parentUri.trimFragment();
		String[] ref = element.getReference().split("::");
		String[] prefix = parentUri.fragment().split("::");
		String fragment = ref[0]+"::"+ref[1]+"::"+prefix[2]+(ref[2].startsWith(prefix[2])? ref[2].substring(prefix[2].length()) : ref);
		uri = uri.appendFragment(fragment);
		return uri;
	}

	private boolean isCorrectURIType(URI uri, EClass eclass){
		if (uri==null || eclass==null) return true;
		if (uri.fragment()==null) return false;
		String[] frags = uri.fragment().split("::");
		String className = eclass.getName();
		return className.equals(frags[1]);
	}
	
	/**
	 * Constructs a new value (possibly an element) to be referenced.
	 * 
	 * @param abstractElement
	 * @param abstractReferencedElement
	 * @param handling
	 * @return
	 */
	protected EObject getNewReferenceValue(URI abstractElementUri, EventBObject abstractElement, EventBObject abstractReferencedElement, 
			URI concreteResourceURI, EventBNamedCommentedComponentElement concreteComponent, String concreteComponentName,
			RefHandling handling, Copier copier) {
		
		EClass eclass = null;
		URI uri = null;
		if (abstractReferencedElement!=null && abstractReferencedElement.eIsProxy()){
			abstractReferencedElement = (EventBObject) EcoreUtil.resolve(abstractReferencedElement, abstractElement.eResource());
		}
		RefHandling handling1;
		if (breakChain && handling == RefHandling.CHAIN){
			handling1 = RefHandling.DROP;
		}else if (clone && handling == RefHandling.CHAIN){
			handling1 = RefHandling.COPY;
		}else{
			handling1 = handling;
		}
		switch (handling1){
		case CHAIN:
			uri = breakChain? null : abstractElementUri;
			eclass = breakChain? null : abstractElement.eClass();	
			break;
		case EQUIV:
			if (abstractReferencedElement instanceof EObject){
				uri = EcoreUtil.getURI((EObject)abstractReferencedElement);
				if (uri !=null && uri.path().length()>0){
					URI abstractResourceURI = ((EObject)abstractElement).eResource().getURI();
					if (uri.path().equals(abstractResourceURI.path())){ //equiv only works for intra-machine refs
						EventBObject abstractComponent = ((EventBObject) abstractReferencedElement).getContaining(CorePackage.Literals.EVENT_BNAMED_COMMENTED_COMPONENT_ELEMENT);
						String abstractComponentName = "null";
						if (abstractComponent instanceof EventBNamed){
							abstractComponentName = ((EventBNamed)abstractComponent).getName();
						}else{
							//FIXME: not sure if this is necessary.. or works.. better to make sure abstractReferencedElement is contained?
							abstractComponentName = abstractElementUri.fragment();
							abstractComponentName = abstractComponentName.substring(abstractComponentName.lastIndexOf("::")+2);
							abstractComponentName = abstractComponentName.substring(0,abstractComponentName.indexOf("."));
						}
						if (copier.containsKey(abstractReferencedElement)){
							String id = EcoreUtil.getID((EObject)abstractReferencedElement).replaceAll("::"+abstractComponentName+"\\.", "::"+concreteComponentName+".");
							uri = concreteResourceURI.appendFragment(id);
							eclass = ((EObject)abstractReferencedElement).eClass();						
						}else if (concreteComponent!=null){
							EObject target = getEquivalentObject(concreteComponent, abstractReferencedElement);
							if (target != null){
								uri = EcoreUtil.getURI(target);
								eclass = target.eClass();
							}
						}
						break;
					}
				}
				break;
			}
		case COPY:
			if (abstractReferencedElement instanceof EObject){
				uri = EcoreUtil.getURI((EObject)abstractReferencedElement);
				uri = uri==null? uri : uri.appendFragment(EcoreUtil.getID((EObject)abstractReferencedElement));
				eclass = ((EObject)abstractReferencedElement).eClass();
			}
			break;
		case DROP:
			uri = null;
			eclass = null;
		}
		if (!isCorrectURIType(uri,eclass)){
			return null;
		}
		return (uri==null || eclass==null)? null : EMFCoreUtil.createProxy(eclass, uri);
	}



}
