/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.propertySections.editTables;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.jface.viewers.IFilter;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.machine.Machine;
import org.eventb.emf.core.machine.MachinePackage;

import ac.soton.eventb.roseEditor.propertySections.abstracts.AbstractTablePropertySection;

/**
 * The Sees table section.
 *
 * @author Colin Snook
 */

public class MachineSeesNamesPropertySection extends AbstractTablePropertySection implements IFilter{

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
	protected String getTableLabel(){ return "Sees";}

	@Override
	protected EStructuralFeature getFeature() {
		return MachinePackage.eINSTANCE.getMachine_SeesNames();
	}

	@Override
	protected String getButtonLabelText(){
		return "Sees";
	}

	@Override
	protected List<String> getColumnLabelText() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("Seen Context Name");
		return  ret;
	}

	@Override
	protected Object getFeatureForCol(int col) {
		if (col==0) return EcorePackage.eINSTANCE.getEString();
		else return null;
	}

}