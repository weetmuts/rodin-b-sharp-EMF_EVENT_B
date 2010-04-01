/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.propertySections.editTables;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.IFilter;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.machine.Event;
import org.eventb.emf.core.machine.MachinePackage;

import ac.soton.eventb.roseEditor.propertySections.abstracts.AbstractPredicateTablePropertySection;

/**
 * Witness's tab table property section.
 *
 * @author Colin Snook (adpated from Guards by Andrew Tillman)
 */

public class EventWitnessesPropertySection extends AbstractPredicateTablePropertySection implements IFilter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try{
			EventBObject element = (EventBObject)selected;
			if (element instanceof Event) return true;
			return false;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	protected String getButtonLabelText() {
		return "Witness";//$NON-NLS-1$
	}

	@Override
	protected EReference getFeature() {
		return MachinePackage.eINSTANCE.getEvent_Witnesses();
	}

}