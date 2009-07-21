/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eventb.emf.edit.propertySections.textFields;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.viewers.IFilter;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.machine.Action;
import org.eventb.emf.edit.propertySections.abstracts.AbstractStringPropertySection;

/**
 * Action property section.
 *
 * @author Colin Snook
 */
public class ActionActionPropertySection extends AbstractStringPropertySection implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try{
			EventBObject element = (EventBObject)selected;
			if (element instanceof Action) return true;
			return false;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	protected String getLabelText() {
		return "Action:"; //$NON-NLS-1$
	}

	@Override
	protected EAttribute getFeature() {
		return CorePackage.eINSTANCE.getEventBAction_Action();
	}
}