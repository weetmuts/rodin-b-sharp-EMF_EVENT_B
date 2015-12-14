/*******************************************************************************
 * Copyright (c) 2012 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package ac.soton.eventb.emf.core.extension.navigator.refiner;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eventb.emf.core.EventBNamedCommentedComponentElement;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.machine.Event;
import org.eventb.emf.core.machine.MachinePackage;

/**
 * Event-B EMF Machine Element Refiner 
 * 
 * @author cfsnook
 *
 */
public class MachineElementRefiner extends CoreElementRefiner {

	/**
	 * populate the given list with the meta-classes that the refiner needs to filter out
	 *  from the copy for refinement.
	 *  
	 * Extenders may call super.populateFilterByTypeList(filterList) to filter
	 *   MachinePackage.Literals.WITNESS and 
	 *   MachinePackage.Literals.INVARIANT
	 *   
	 */
	@Override
	protected void populateFilterByTypeList(final List<EClass> filterList){
		super.populateFilterByTypeList(filterList);
		filterList.add(MachinePackage.Literals.WITNESS);
		filterList.add(MachinePackage.Literals.INVARIANT);
	}
	
	/**
	 * populate the given map with the reference features that the refiner needs to copy for statemachine refinement.
	 * This is refines (as references to their abstract counterparts) and
	 * incoming, outgoing, source and target (as intra-level references).
	 * Instances
	 */
	@Override
	protected void populateReferenceMap(final Map<EReference,RefHandling> referencemap){
		super.populateReferenceMap(referencemap);
		referencemap.put(MachinePackage.Literals.EVENT__REFINES, RefHandling.CHAIN);
		referencemap.put(MachinePackage.Literals.MACHINE__REFINES, RefHandling.CHAIN);
		referencemap.put(MachinePackage.Literals.MACHINE__SEES, RefHandling.EQUIV);
	}
	
	/**
	 * Change this to specialise the meaning of 'equivalent' 
	 * (used when finding reference targets in the refined model)
	 * 
	 */
	public EventBObject getEquivalentObject(EObject concreteParent, EObject abstractObject) {
		return super.getEquivalentObject(concreteParent, abstractObject);
	}
	
	/**
	 * Overridden to ensure INITIALISATION event does not refine anything
	 */
	@Override
	protected void copyReferences(EObject concreteElement, Copier copier, URI abstractUri, 
			URI concreteResourceURI, EventBNamedCommentedComponentElement concreteComponent, String concreteComponentName ) {

		super.copyReferences(concreteElement, copier, abstractUri, concreteResourceURI, concreteComponent, concreteComponentName);

		if(concreteElement instanceof Event && "INITIALISATION".equals(((Event)concreteElement).getName())){
			((Event)concreteElement).getRefines().clear();
		}
		
	}
	
}
