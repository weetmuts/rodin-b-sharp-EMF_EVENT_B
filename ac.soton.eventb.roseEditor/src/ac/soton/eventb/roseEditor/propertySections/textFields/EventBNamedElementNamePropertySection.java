/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.propertySections.textFields;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.viewers.IFilter;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBNamed;
import org.eventb.emf.core.EventBObject;

import ac.soton.eventb.roseEditor.propertySections.abstracts.AbstractStringPropertySection;

/**
 * A section for the name property of a selected B Object.
 *
 * @author Colin Snook
 */


public class EventBNamedElementNamePropertySection extends AbstractStringPropertySection implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try{
			EventBObject element = (EventBObject)selected;
			if (element instanceof EventBNamed) return true;
			return false;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	protected String getLabelText() {
		return "Name:"; //$NON-NLS-1$
	}

	@Override
	protected EAttribute getFeature() {
		return CorePackage.eINSTANCE.getEventBNamed_Name();
	}

	/**
	 * Handle the text modified event.
	 */
	@Override
	protected void handleTextModified() {
		String newText = getTextWidget().getText();
		if (!isCurrent(newText)){
//			String oldText = getFeatureAsText();
//
//			if (DiagramUtilities.nameCollision(owner, newText, owner.eContainer(), getFeature())) {
//				MessageDialog.openError(getPart().getSite().getShell(),"Warning", "The element cannot be renamed because another element already exists with that name");
//				text.setFocus();
//				text.setText(oldText);
//				return;
//			}//else...
//			//rename any diagram files that are affected by this change
//			text.setFocus();
//			DiagramUtilities.renameFiles(owner, oldText, newText);

			//defer to super to handle modification of the name feature
			super.handleTextModified();
		}
	}
}