/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.propertySections.abstracts;

import java.text.MessageFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import ac.soton.eventb.roseEditor.properties.TextChangeHelper;


/**
 * Create a button which does something
 * @author Andrew Tillman / Colin Snook
 *
 */
// cfs 100407 - made more abstract (like class diagram one)
//cfs 30/11/07 Common UMLBPropertySheetPage class, replaces individual classes from each diagram

public abstract class AbstractButtonPropertySection extends AbstractEventBPropertySection {


	protected TextChangeHelper listener;

	@Override
	public void createControls(final Composite parent,
			final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		FormData data;
		Shell shell = new Shell();
		GC gc = new GC(shell);
		gc.setFont(shell.getFont());
		Point point = gc.textExtent("");//$NON-NLS-1$
		int buttonHeight = point.y + 11;
		gc.dispose();
		shell.dispose();
		widget = getWidgetFactory().createButton(composite,
				MessageFormat.format("{0}...",//$NON-NLS-1$
					new Object[] {getButtonLabelText()}), SWT.PUSH);
			data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.bottom = new FormAttachment(100, 0);
			data.top = new FormAttachment(100, -buttonHeight);
			((Control)widget).setLayoutData(data);
			((Button)widget).addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent event) {buttonAction(event);}
			}
		);
	}


	protected abstract void buttonAction(SelectionEvent event);

	protected abstract String getButtonLabelText();
}