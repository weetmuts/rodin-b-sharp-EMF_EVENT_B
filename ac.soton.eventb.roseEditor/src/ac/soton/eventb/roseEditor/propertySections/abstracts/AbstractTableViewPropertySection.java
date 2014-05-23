/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.propertySections.abstracts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.rodinp.keyboard.ui.preferences.PreferenceConstants;


/**
 * An abstract implementation of a section with a table field to be used as an 'overview'
 * abstact Table.
 *
 * @author cfs
 */
public abstract class AbstractTableViewPropertySection
	extends AbstractEventBPropertySection {

	/**
	 * the table control for the section.
	 */
	protected Table table;

	/**
	 * the title columns for the section.
	 */
	protected List<TableColumn> columns;


	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(final Composite parent,
			final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		Composite composite = getWidgetFactory()
			.createFlatFormComposite(parent);


		FormData data;

		table = getWidgetFactory().createTable(composite,
				SWT.FULL_SELECTION | SWT.READ_ONLY | SWT.SCROLL_LOCK); //SWT.H_SCROLL | SWT.V_SCROLL

		Font font = JFaceResources.getFont(PreferenceConstants.RODIN_MATH_FONT);
		table.setFont(font);


		data = new FormData();
		data.left = new FormAttachment(0, getStandardLabelWidth(composite,
			new String[] {getTableLabel()}));
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		table.setLayoutData(data);

		CLabel nameLabel = getWidgetFactory().createCLabel(composite,
			getTableLabel());
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(table, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(table, 0, SWT.CENTER);
		nameLabel.setLayoutData(data);



		List<?> labels = getColumnLabelText();
		columns = new ArrayList<TableColumn>();

		for (Iterator<?> i = labels.iterator(); i.hasNext();) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText((String) i.next());
			columns.add(column);
		}


		if (columns.size()>2) {
			table.setHeaderVisible(true);
		} else {
			table.setHeaderVisible(false);
		}

		table.setLinesVisible(false);
		table.setEnabled(false);

		Shell shell = new Shell();
		GC gc = new GC(shell);
		gc.setFont(shell.getFont());

		gc.dispose();
		shell.dispose();

	}


	@Override
	public boolean shouldUseExtraSpace() {
		return false;
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		table.removeAll();
		table.redraw();
		for (Iterator<?> i = getOwnedRows().iterator(); i.hasNext();) {
			Object next = i.next();
			String key = getKeyForRow(next);

			// find index (for sorting purposes)
			int k = 0;
			int size = table.getItemCount();
			while (k < size) {
				String currentKey = table.getItem(k).getText();
				if (key.compareToIgnoreCase(currentKey) < 0) {
					break;
				}
				k++;
			}

			// create the table item
			TableItem item = new TableItem(table, SWT.NONE, k);
			String[] values = new String[columns.size()];
			List<?> valuesForRow = getValuesForRow(next);
			for (int j = 0; j < columns.size(); j++) {
				values[j] = (String) valuesForRow.get(j);
			}
		//	item.setBackground(getColour(next));
			item.setText(values);
			item.setData(next);
		}

		if (getOwnedRows().size()==0){
			table.setHeaderVisible(false);
			table.setVisible(false);
			table.setItemCount(1);
		} else {
			table.setHeaderVisible(isHeadingNeeded());
			table.setVisible(true);
		}
		for (Object element : columns) {
			((TableColumn) element).pack();
		}
	}


	/**
	 * Get the row objects for the table.
	 *
	 * @return the list of the row objects.
	 */
	protected abstract List<?> getOwnedRows();

	/**
	 * Get the feature for the table field for the section.
	 *
	 * @return the feature for the table.
	 */
	protected abstract EReference getFeature();

	/**
	 * Get the key for the table that is used for sorting. Usually the table is
	 * sorted by Name or some key string..
	 *
	 * @param object
	 *            an object in the row of the table.
	 * @return the string for the key.
	 */
	protected abstract String getKeyForRow(Object object);

	/**
	 * Get the values for the row in the table.
	 *
	 * @param object
	 *            an object in the row of the table.
	 * @return the list of string values for the row.
	 */
	protected abstract List<?> getValuesForRow(Object object);

	/**
	 * Get the labels for the columns for the table.
	 *
	 * @return the labels for the columns.
	 */
	protected abstract List<?> getColumnLabelText();

	/**
	 * Get a new child instance for the result of clicking the add button.
	 *
	 * @return a new child instance.
	 */
	protected abstract Object getNewChild();

	protected abstract String getTableLabel();

	protected  boolean isHeadingNeeded() {return true;}

}