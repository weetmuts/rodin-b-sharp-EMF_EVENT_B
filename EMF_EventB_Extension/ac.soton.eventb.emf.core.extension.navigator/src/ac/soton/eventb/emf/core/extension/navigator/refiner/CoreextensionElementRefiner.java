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

import ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage;

/**
 * Coreextension Element Refiner 
 * 
 * @author cfsnook
 *
 */
public class CoreextensionElementRefiner extends CoreElementRefiner {

	/**
	 * populate the given list with the meta-classes that the refiner needs to filter out
	 *  from the refinement.
	 */
	@Override
	protected void populateFilterByTypeList(final List<EClass> filterList){
		super.populateFilterByTypeList(filterList);
	}
	
	/**
	 * populate the given map with the reference features that the refiner needs to copy 
	 * for refinement.
	 * 
	 * 	Extenders may call super.populateReferenceMap(referencemap) to set 
	 * 	EVENT_BDATA_ELABORATION__ELABORATES, false and
	 * 	EVENT_BEVENT_GROUP__ELABORATES, false
	 * 
	 */
	@Override
	protected void populateReferenceMap(final Map<EReference,RefHandling> referencemap){
		super.populateReferenceMap(referencemap);
		referencemap.put(CoreextensionPackage.Literals.EVENT_BDATA_ELABORATION__ELABORATES, RefHandling.EQUIV);
		referencemap.put(CoreextensionPackage.Literals.EVENT_BEVENT_GROUP__ELABORATES, RefHandling.EQUIV);
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
