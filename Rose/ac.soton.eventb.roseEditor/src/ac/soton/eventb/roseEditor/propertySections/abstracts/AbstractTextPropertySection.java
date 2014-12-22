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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.rodinp.keyboard.ui.RodinKeyboardUIPlugin;
import org.rodinp.keyboard.ui.preferences.PreferenceConstants;
import ac.soton.eventb.roseEditor.properties.TextChangeHelper;

/**
 * An abstract implementation of a section with a text field. 
 * The text field may be multi-line or single line as determined by isMultiLine().
 * The text may be Rodin Keyboard or plain text as determined by isRodinKeyboard().
 *
 * @author cfs
 */

public abstract class AbstractTextPropertySection extends AbstractEventBPropertySection {

	final int TEXT_MARGIN = 3;
	
	/**
	 * A helper to listen for events that indicate that a text field has been
	 * changed.
	 */
	protected TextChangeHelper listener = new TextChangeHelper()
	{
		@Override
		public void textChanged(final Control control) {
			handleTextModified();
		}
	};

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		widget = createTextWidget(getWidgetFactory().createFlatFormComposite(parent));
	}

	//the default  width may be overridden in extensions
	protected int getTextWidth(){
		return 100;
	}
	
	protected Text createTextWidget(Composite composite) {
		Text text;
		if (isMultiLine()){
			text = getWidgetFactory().createText(composite, "", SWT.MULTI); //$NON-NLS-1$
		}else{
			text = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$			
		}
		if (isRodinKeyboard()) {
			text.setFont(JFaceResources.getFont(PreferenceConstants.RODIN_MATH_FONT));
			text.addModifyListener(RodinKeyboardUIPlugin.getDefault().createRodinModifyListener());
		}
		FormData data;
		data = new FormData();
		data.left = new FormAttachment(0, getStandardLabelWidth(composite, new String[] {getLabelText()}));
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		text.setLayoutData(data);
		CLabel nameLabel = getWidgetFactory().createCLabel(composite,getLabelText());
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(text, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(text, 0, SWT.CENTER);
		nameLabel.setLayoutData(data);
		listener.startListeningTo(text);
		if (!isMultiLine()){
			listener.startListeningForEnter(text);
		}
		return text;
	}

	/**
	 * Handle the text modified event.
	 */
	protected void handleTextModified() {
		String newText = ((Text)widget).getText();
		if (!isCurrent(newText)){
			editingDomain.getCommandStack().execute(SetCommand.create(editingDomain, owner, getFeature(),getFeatureValue(newText)));
			reSizeTextWidget(newText);			
		}
		
	}
	
	@Override
	public void refresh() { 
		if (widget==null) return;
		String text = getFeatureAsText();
		((Text)widget).setText(text);
		reSizeTextWidget(text);
		super.refresh();
	}
	

	private void reSizeTextWidget(String text){
		if (!isMultiLine()) return;
		GC gc = new GC(((Text)widget));
		Point size = gc.textExtent(text);
		((Text)widget).setSize(((Text)widget).getSize().x, size.y + 2*TEXT_MARGIN);
		gc.dispose();
	}
	
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection){
		super.setInput(part, selection);
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
	
	/**
	 * By default the text field is NOT treated as a Rodin Keyboard.
	 * This method should be overridden to return true if the Rodin key combo modification is required.
	 * 
	 * @return whether or not to use a Rodin Keyboard modify listener for this text
	 */
	protected boolean isRodinKeyboard(){return false;}

	/**
	 * Override to return more than 1 for a multi line text widget
	 * @return
	 */
	protected boolean isMultiLine(){
		return false;
	}

}