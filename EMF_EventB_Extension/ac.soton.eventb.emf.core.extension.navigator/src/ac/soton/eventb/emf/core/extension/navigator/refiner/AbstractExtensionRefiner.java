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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eventb.core.IEventBRoot;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.machine.MachinePackage;
import org.eventb.emf.persistence.EMFRodinDB;
import org.rodinp.core.IInternalElement;
import org.rodinp.core.IRefinementParticipant;
import org.rodinp.core.RodinDBException;

import ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage;
import ac.soton.eventb.emf.core.extension.persistence.ISerialisedExtension;
import ac.soton.eventb.emf.core.extension.persistence.SerialisedExtensionSynchroniser;

/**
 * Abstract Refiner for serialised EventB EMF Extensions.
 * This should be extended for each Extension ID
 * 
 * @author cfsnook
 *
 */
public abstract class AbstractExtensionRefiner implements IRefinementParticipant {

	/**
	 * Create an EMFRodinDB for loading extensions into EMF
	 */
	private final static EMFRodinDB emfRodinDB = new EMFRodinDB();
	
	/**
	 * The ID for this kind of Serialised Event-B EMF Extension
	 */
	private final String EXTENSION_ID;
	/**
	 * Extenders must implement this to return the ID string of their extension
	 * @return
	 */
	protected abstract String getExtensionID();

	/**
	 * A list of the EClasses which should not be copied into the refinement
	 */
	private	List<EClass> filterList = new ArrayList<EClass>();

	/**
	 * Extenders may override this method to populate the list of EClasses 
	 * which should not be copied into a refinement.
	 * 
	 * Extenders may call super.populateFilterByTypeList(filterList) to filter
	 *   MachinePackage.Literals.WITNESS and 
	 *   MachinePackage.Literals.INVARIANT
	 *   
	 * @param filterList
	 */
	protected void populateFilterByTypeList(List<EClass> filterList) {
		filterList.add(MachinePackage.Literals.WITNESS);
		filterList.add(MachinePackage.Literals.INVARIANT);
	}
	
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
	 * A map of the references which need to be dealt with in the new refined model.
	 * For each EReference, the RefHandling indicates whether it should be dealt with as a reference back to 
	 * the source element (e.g. refines) or a normal reference within the same resource level which
	 * will be copied to simulate the abstract one, or it should be copied or dropped in the refined model.
	 */
	private Map<EReference,RefHandling> referencemap = new HashMap<EReference,RefHandling>();
	
	/**
	 * Extenders may override this method to populate the reference mapping with a list of 
	 * EReference features in their model extension. For each one indicate whether it should be dealt 
	 * with as a reference to the original source element (e.g. refines).
	 * (If the reference mapping is not populated the default behaviour is to not copy any references
	 * into the refinement).
	 * 
	 * Extenders may call super.populateReferenceMap(referencemap) to set 
	 * 	EVENT_BDATA_ELABORATION__ELABORATES, false and
	 * 	EVENT_BEVENT_GROUP__ELABORATES, false
	 */
	protected void populateReferenceMap(Map<EReference,RefHandling> referencemap){
		referencemap.put(CoreextensionPackage.Literals.EVENT_BDATA_ELABORATION__ELABORATES, RefHandling.EQUIV);
		referencemap.put(CoreextensionPackage.Literals.EVENT_BEVENT_GROUP__ELABORATES, RefHandling.EQUIV);
	}
	
	/** 
	 * a synchroniser for saving these models 
	 */
	private SerialisedExtensionSynchroniser synchroniser = new SerialisedExtensionSynchroniser();;
	
	/**
	 * The URIs for the abstract and concrete Resources
	 */
	private URI abstractResourceURI;
	private URI concreteResourceURI;

	/**
	 * The names for the abstract and concrete components
	 */
	private String abstractComponentName;
	private String concreteComponentName;
	
	/**
	 * The instance of the copier.
	 * Note that this is also a mapping from source to target once the copy has been performed
	 */
	private Copier copier = new EcoreUtil.Copier(true,false);
	
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
	protected AbstractExtensionRefiner() {
		EXTENSION_ID = getExtensionID();
		populateFilterByTypeList(filterList);
		populateReferenceMap(referencemap);
	}

	/**
	 * Extenders should not override this
	 * @throws CoreException 
	 */
	@Override
	public void process(IInternalElement targetRoot, IInternalElement sourceRoot, IProgressMonitor monitor) throws CoreException {
		final IEventBRoot concreteEventBRoot = (IEventBRoot) targetRoot;
		final IEventBRoot abstractEventBRoot = (IEventBRoot) sourceRoot;
		// machine URIs
		String projectName = sourceRoot.getRodinProject().getElementName();
		abstractComponentName = abstractEventBRoot.getComponentName();
		concreteComponentName = concreteEventBRoot.getComponentName();
		abstractResourceURI = URI.createURI("platform:/resource/" + projectName + "/" + abstractEventBRoot.getComponentName() + ".bum");
		concreteResourceURI = URI.createURI("platform:/resource/" + projectName + "/" + concreteEventBRoot.getComponentName() + ".bum");
		refineExtensions(concreteEventBRoot, abstractEventBRoot, monitor);
	}

	/**
	 * Refines all Serialised Event-B EMF extensions with the given ID.
	 * 
	 * @param concreteEventBRoot concrete machine root
	 * @param abstractEventBRoot abstract machine root
	 * @param map refinement map
	 * @param monitor progress monitor
	 * @throws RodinDBException 
	 */
	private void refineExtensions(IEventBRoot concreteEventBRoot, IEventBRoot abstractEventBRoot, IProgressMonitor monitor) throws RodinDBException, CoreException {
		for (ISerialisedExtension extension : abstractEventBRoot.getChildrenOfType(ISerialisedExtension.ELEMENT_TYPE)) {
			if (extension.hasExtensionId() && extension.getExtensionId().startsWith(EXTENSION_ID))
				refineExtension(concreteEventBRoot, abstractEventBRoot, extension, monitor);
		}
	}

	/**
	 * Refines component extension.
	 * 
	 * @param concreteEventBRoot concrete EventB Component root
	 * @param abstractExtensionRoot the root of the abstract extension
	 * @param map refinement map
	 * @param monitor progress monitor
	 * @throws RodinDBException 
	 */
	private void refineExtension(IEventBRoot concreteEventBRoot, IEventBRoot abstractEventBRoot, ISerialisedExtension abstractExtensionRoot, IProgressMonitor monitor) throws RodinDBException, CoreException 	{		
		//obtain the EMF version of the abstract serialised extension by loading it into a Rodin Resource
		EventBObject extension = emfRodinDB.loadElement(abstractExtensionRoot);
		//serialise refined extension back into the RodinDB concreteEventBRoot (not into EMF resource as this does not exist yet)
		synchroniser.save(refineEventBElement(extension), concreteEventBRoot, monitor);
	}

	
	/**
	 * Returns refined component from root component.
	 * 
	 * 
	 * @param extension
	 * @return
	 */
	private EventBElement refineEventBElement(EventBObject element) {
		copier = new Copier(true,false);
		// create refined Component using copier.
		// this does a deep copy of all the children and properties of the copied element
		// but it does not copy any references
		EventBElement concreteEventBElement = (EventBElement) copier.copy(element); 
		//copier.copyReferences();  <--THIS DOES NOT WORK - INSTEAD SEE BELOW
		copyReferences(concreteEventBElement);
		//having copied everything we may need to remove some kinds of elements that are not supposed to be
		//copied into a refinement
		filterElements(concreteEventBElement);
		return concreteEventBElement;
	}

	

	/*
	 * This removes any elements that are of a type (EClass) listed in filterList
	 */
	@SuppressWarnings("unchecked")
	private void filterElements(EventBElement concreteEventBElement) {
		List<EObject> removeList = new ArrayList<EObject>();
		for (EClass removeClass : filterList){
			removeList.addAll(concreteEventBElement.getAllContained(removeClass, true));
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

	/*
	 * This sets up the references in the new refined model according to the 
	 * settings in the referenceMap
	 */
	@SuppressWarnings("unchecked")
	private void copyReferences(EventBElement concreteEventBElement) {
		// Set up references in the new concrete model  (note that copier.copyReferences() does not work for this)
		//get all the content of the root Element including itself
		EList<EObject> contents = concreteEventBElement.getAllContained(CorePackage.Literals.EVENT_BELEMENT, true);
		contents.add(concreteEventBElement);
		// iterate through the contents looking for references corresponding to those declared in the referencemap
		// and copy them in the appropriate way according to multiplicity and the refencemap.
		for (EObject concreteElement : contents){
			if (concreteElement instanceof EventBElement){
				EReference referenceFeature;
				for (Entry<EReference, RefHandling> referenceEntry : referencemap.entrySet()){
					referenceFeature = referenceEntry.getKey(); 
					if (referenceFeature.getEContainingClass().isSuperTypeOf(concreteElement.eClass())){
						EObject abstractElement = getKeyByValue(copier, concreteElement);
						//NOTE: *** Cannot use the concrete elements to create URIs because their parentage is not complete ***
						if (referenceFeature.isMany()){
							for (EObject abstractReferencedElement : (EList<EObject>)(getKeyByValue(copier, concreteElement)).eGet(referenceFeature)){		
								EObject newValue = getNewReferenceValue(
										abstractElement,
										abstractReferencedElement,
										referenceEntry.getValue());
								if (newValue!=null){
									((EList<EObject>)concreteElement.eGet(referenceFeature)).add(newValue);
								}
							}
						}else{
							if (referenceFeature.getEType() instanceof EClass){
								EObject newValue = getNewReferenceValue(
										abstractElement,
										abstractElement.eGet(referenceFeature,false),
										referenceEntry.getValue());
								if (newValue!=null){
									concreteElement.eSet(referenceFeature, newValue);
								}
							}
						}
					}
				}
			}
		}
	}


	/**
	 * @param abstractElement
	 * @param abstractReferencedElement
	 * @param handling
	 * @return
	 */
	private EObject getNewReferenceValue(EObject abstractElement, Object abstractReferencedElement, RefHandling handling) {
		EClass eclass = null;
		URI uri = null;
		switch (handling){
		case CHAIN:
			uri = getURI((EObject)abstractElement);
			uri = uri==null? uri : uri.appendFragment(EcoreUtil.getID((EObject)abstractElement));
			eclass = abstractElement.eClass();	
			break;
		case EQUIV:
			//abstractReferencedElement = abstractElement.eGet(referenceFeature,false);
			if (abstractReferencedElement instanceof EObject){
				uri = getURI((EObject)abstractReferencedElement);
				if (uri !=null && uri.path().equals(abstractResourceURI.path())){ //equiv only works for intra-machine refs
					uri = concreteResourceURI.appendFragment(
							EcoreUtil.getID((EObject)abstractReferencedElement).replaceAll("::"+abstractComponentName+"\\.", "::"+concreteComponentName+".") );
					eclass = ((EObject)abstractReferencedElement).eClass();
					break;
				}
			//when equiv is not possible default to copy
			}
		case COPY:
			//abstractReferencedElement = abstractElement.eGet(referenceFeature,false);
			if (abstractReferencedElement instanceof EObject){
				uri = getURI((EObject)abstractReferencedElement);
				uri = uri==null? uri : uri.appendFragment(EcoreUtil.getID((EObject)abstractReferencedElement));
				eclass = ((EObject)abstractReferencedElement).eClass();
			}
			break;
		case DROP:
			uri = null;
			eclass = null;
		}
		return (uri==null || eclass==null)? null : EMFCoreUtil.createProxy(eclass, uri);
	}

	
	/**
	 * this returns the URI without loading the eObject
	 * (copied from EventBObject to support references to non-EventBObjects in refinement)
	 * @param eObject
	 * @return
	 */
	private static  URI getURI(EObject eObject) {
		if (eObject==null) return null;
		if (eObject.eIsProxy()){
			return ((InternalEObject)eObject).eProxyURI();
		}else{
			return  eObject.eResource()==null? null : eObject.eResource().getURI();
		}
	}
}
