/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eventb.emf.edit.propertySections.abstracts;

import java.util.ArrayList;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.structuredEditor.EventBStructuredEditor;


/**
 * Abstract basis for custom property sheets that show model properties
 * (as opposed to the model property tab which shows a more EMF style list of model properties)
 *
 * This property sheet is intended to work on a single selected model element. If more are selected only one will be used.
 *
 * This code was originally based on the hockey league example available from the eclipse website
 *
 * @author cfs
 */

public abstract class AbstractEventBPropertySection extends AbstractPropertySection implements IPropertySourceProvider {

	/**
	 * the property sheet page for this section.
	 */
	protected TabbedPropertySheetPage propertySheetPage;

	/**
	 * the GMF editing domain for the current diagram editor
	 */
	protected EditingDomain editingDomain = null;
	public EditingDomain getEditingDomain() { return editingDomain;}

	/**
	 * The current selected UMLB element or the first object in the selection when
	 * multiple objects are selected.
	 */
	protected EventBElement owner;


	/**
	 *
	 */
	protected Composite parent;

	/* Doesn't work because don't have the labels so disabled
	 * Get the standard label width when labels for sections line up on the left
	 * hand side of the composite. We line up to a fixed position, but if a
	 * string is wider than the fixed position, then we use that widest string.
	 *
	 * @param parent
	 *            The parent composite used to create a GC.
	 * @param labels
	 *            The list of labels.
	 * @return the standard label width.
	 */
	protected int getStandardLabelWidth(final Composite parent, final String[] labels) {
		return 100;

//		int standardLabelWidth = STANDARD_LABEL_WIDTH;
//		GC gc = new GC(parent);
//		int indent = gc.textExtent("XXX").x; //$NON-NLS-1$
//		for (String element : labels) {
//			int width = gc.textExtent(element).x;
//			if (width + indent > standardLabelWidth) standardLabelWidth = width + indent;
//		}
//		gc.dispose();
//		return standardLabelWidth;
	}


	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage propertySheetPage) {
		super.createControls(parent, propertySheetPage);
		this.parent = parent;
		this.propertySheetPage = propertySheetPage;
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (activeEditor instanceof EventBStructuredEditor){
			editingDomain=((EventBStructuredEditor)activeEditor).getEditingDomain();
		}
	}


	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		owner=null;
		if (selection.isEmpty() || false == selection instanceof StructuredSelection) {
			super.setInput(part, selection);
			return;
		}
		final StructuredSelection structuredSelection = (StructuredSelection) selection;
		ArrayList<Object> transformedSelection = new ArrayList<Object>(structuredSelection.size());
		for (Object object : structuredSelection.toList()){
			if (owner==null && object instanceof EventBElement) owner=(EventBElement)object;
			if (object != null) {
				transformedSelection.add(object);
			}
		}
		super.setInput(part, new StructuredSelection(transformedSelection));
	}

	/* property source provider implementation (copied from generated DiagramPropertySection)*/

	/**
	 * @generated
	 */
	public IPropertySource getPropertySource(final Object object) {
		if (object instanceof IPropertySource) { return (IPropertySource) object; }
		AdapterFactory af = getAdapterFactory(object);
		if (af != null) {
			IItemPropertySource ips = (IItemPropertySource) af.adapt(object, IItemPropertySource.class);
			if (ips != null) { return new PropertySource(object, ips); }
		}
		if (object instanceof IAdaptable) { return (IPropertySource) ((IAdaptable) object).getAdapter(IPropertySource.class); }
		return null;
	}

	/**
	 * @generated
	 */
	protected IPropertySourceProvider getPropertySourceProvider() {
		return this;
	}

	/**
	 * @generated
	 */
	protected AdapterFactory getAdapterFactory(final Object object) {
		if (getEditingDomain() instanceof AdapterFactoryEditingDomain) { return ((AdapterFactoryEditingDomain) getEditingDomain()).getAdapterFactory(); }
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(object);
		if (editingDomain != null) { return ((AdapterFactoryEditingDomain) editingDomain).getAdapterFactory(); }
		return null;
	}

}