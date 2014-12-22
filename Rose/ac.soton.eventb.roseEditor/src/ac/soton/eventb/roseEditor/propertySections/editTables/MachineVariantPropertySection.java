/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.propertySections.editTables;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.IFilter;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.machine.Machine;
import org.eventb.emf.core.machine.MachinePackage;

import ac.soton.eventb.roseEditor.propertySections.abstracts.AbstractTablePropertySection;

/**
 * The Variant tab table section.
 *
 * @author Colin Snook
 */

public class MachineVariantPropertySection extends AbstractTablePropertySection implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try{
			EventBObject element = (EventBObject)selected;
			if (element instanceof Machine) return true;
			return false;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	protected String getTableLabel(){
		return "Variant:";
	}

	@Override
	protected EAttribute getFeatureForCol(final int col) {
		switch (col) {
		case 0 : return CorePackage.eINSTANCE.getEventBExpression_Expression();
		case 1 : return CorePackage.eINSTANCE.getEventBCommented_Comment();
		default : return null;
		}
	}

	@Override
	protected EReference getFeature() {
		return MachinePackage.eINSTANCE.getMachine_Variant();
	}

	@Override
	protected int columnWidth(final int col){
		switch (col) {
		case 0 : return 400;	//expression field
		case 1 : return 400;	//comment field
		default : return -1;	//unknown
		}
	}

	@Override
	protected boolean isRodinKeyboard(final int col){
		return col==0;
	}

	@Override
	protected boolean isMulti(final int col){
		return true;
	}

}