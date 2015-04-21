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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.context.ContextPackage;


/**
 * Event-B EMF Context Element Refiner 
 * 
 * @author cfsnook
 *
 */
public class ContextElementRefiner extends CoreElementRefiner {

	/**
	 * populate the given list with the meta-classes that the refiner needs to filter out
	 *  from the copy for refinement.
	 *  
	 *  for Context everything is filtered out
	 *   
	 */
	@Override
	protected void populateFilterByTypeList(final List<EClass> filterList){
		filterList.add(ContextPackage.Literals.AXIOM);
		filterList.add(ContextPackage.Literals.CARRIER_SET);
		filterList.add(ContextPackage.Literals.CONSTANT);
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
		referencemap.put(ContextPackage.Literals.CONTEXT__EXTENDS, RefHandling.CHAIN);
	}
	
	/**
	 * Change this to specialise the meaning of 'equivalent' 
	 * (used when finding reference targets in the refined model)
	 * 
	 */
	public EventBObject getEquivalentObject(EObject concreteParent, EObject abstractObject) {
		return super.getEquivalentObject(concreteParent, abstractObject);
	}
	
}
