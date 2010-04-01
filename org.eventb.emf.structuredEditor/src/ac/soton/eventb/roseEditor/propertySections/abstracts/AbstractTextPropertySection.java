/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.propertySections.abstracts;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eventb.eventBKeyboard.EventBTextModifyListener;
import org.eventb.eventBKeyboard.preferences.PreferenceConstants;

import ac.soton.eventb.roseEditor.properties.TextChangeHelper;

/**
 * An abstract implementation of a section with a text field.
 *
 * @author cfs
 */


public abstract class AbstractTextPropertySection extends AbstractEventBPropertySection {

	/**
	 * the text control for the section.
	 */
	protected Text text;

	/**
	 * A helper to listen for events that indicate that a text field has been
	 * changed.
	 */
	protected TextChangeHelper listener;
	private final EventBTextModifyListener eventBListener=new EventBTextModifyListener();


	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		FormData data;
		text = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, getStandardLabelWidth(composite,
			new String[] {getLabelText()}));
		data.right = new FormAttachment(getTextWidth(), 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		text.setLayoutData(data);

		CLabel nameLabel = getWidgetFactory().createCLabel(composite,getLabelText());
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(text, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(text, 0, SWT.CENTER);
		nameLabel.setLayoutData(data);
		text.addModifyListener(eventBListener);
		// Using a special fonts for showing Event-B symbols.
		Font font = JFaceResources.getFont(PreferenceConstants.EVENTB_MATH_FONT);
		text.setFont(font);

		listener = new TextChangeHelper() {

			@Override
			public void textChanged(final Control control) {
				handleTextModified();
			}
		};
		listener.startListeningTo(text);
		listener.startListeningForEnter(text);
	}

	//the default  width may be overridden in extensions
	protected int getTextWidth(){
		return 100;
	}

	/**
	 * Handle the text modified event.
	 */
	protected void handleTextModified() {
		String newText = text.getText();
		if (!isCurrent(newText)){
			editingDomain.getCommandStack().execute(SetCommand.create(editingDomain, owner, getFeature(),getFeatureValue(newText)));
		}
	}


	@Override
	public void refresh() {
		if (text==null) return;
		text.setText(getFeatureAsText());
	}

	/**
	 * Determine if the provided string value is an equal representation of the
	 * current setting of the text property.
	 *
	 * @param newText
	 *            the new string value.
	 * @return <code>true</code> if the new string value is equal to the
	 *         current property setting.
	 */
	protected abstract boolean isCurrent(String newText);

	/**
	 * Get the feature for the text field for the section.
	 *
	 * @return the feature for the text.
	 */
	protected abstract EStructuralFeature getFeature();

	/**
	 * Get the value of the feature as text for the text field for the section.
	 *
	 * @return the value of the feature as text.
	 */
	protected abstract String getFeatureAsText();

	/**
	 * Get the new value of the feature for the text field for the section.
	 *
	 * @param newText
	 *            the new value of the feature as a string.
	 * @return the new value of the feature.
	 */
	protected abstract Object getFeatureValue(String newText);

	/**
	 * Get the label for the text field for the section.
	 *
	 * @return the label for the text field.
	 */
	protected abstract String getLabelText();
}