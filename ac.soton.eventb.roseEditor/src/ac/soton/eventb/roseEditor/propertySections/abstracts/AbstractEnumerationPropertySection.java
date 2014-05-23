/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.propertySections.abstracts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eventb.emf.core.CoreFactory;
import org.eventb.emf.core.EventBNamed;
import org.eventb.emf.core.Project;
import org.eventb.emf.core.util.NameUtils;
import org.rodinp.keyboard.ui.preferences.PreferenceConstants;

/**
 * An abstract implementation of a section with an enumeration field using a
 * combo box (pulldown).
 *
 * @author cfs
 */

public abstract class AbstractEnumerationPropertySection extends AbstractEventBPropertySection {

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		widget = new Combo(composite, SWT.READ_ONLY);
		
		FormData data;
		data = new FormData();
		data.left = new FormAttachment(0, getStandardLabelWidth(composite, new String[] {getLabelText()}));
		data.right = new FormAttachment(50, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		((Control)widget).setLayoutData(data);

		Font font = JFaceResources.getFont(PreferenceConstants.RODIN_MATH_FONT);
		((Control)widget).setFont(font);

		CLabel nameLabel = getWidgetFactory().createCLabel(composite,getLabelText());
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(((Control)widget), -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(((Control)widget), 0, SWT.CENTER);
		nameLabel.setLayoutData(data);
		((Combo)widget).addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {handleComboModified();}
		});
	}

	/**
	 * Handle the combo modified event.
	 */
	protected void handleComboModified() {
		Object newValue = getNewValue(((Combo)widget).getSelectionIndex());
		if (newValue!=owner.eGet(getFeature())) {
			editingDomain.getCommandStack().execute(SetCommand.create(editingDomain, owner, getFeature(), newValue));
		}
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		((Combo)widget).setItems(getComboValuesAsText().toArray(new String[0]));
		((Combo)widget).setText(getCurrentValueAsText());
		super.refresh();
	}

	/**
	 * Get the enumeration values of the feature as text.
	 *
	 * @return the list of values of the feature as text.
	 */
	protected List<String> getComboValuesAsText() {
		return NameUtils.getNames(getComboValues());
	}

	/**
	 * Get the feature for the combo field for the section.
	 *
	 * @return the feature for the text.
	 */
	protected abstract EStructuralFeature getFeature();

	/**
	 * Get the enumeration values of the feature.
	 *
	 * @return the list of values of the feature as text.
	 */
	@SuppressWarnings("rawtypes")
	protected List getComboValues(){
		Object feature = getFeature();
		if (feature instanceof EReference){
			EClass eClass = ((EReference)feature).getEReferenceType();
			Project project = CoreFactory.eINSTANCE.createProject();
			project = (Project) owner.getContaining(project.eClass());
			return project.getAllContained(eClass,false);
		}else if (feature instanceof EAttribute) {
			EDataType dataType = ((EAttribute)feature).getEAttributeType();
			if (dataType instanceof EEnum){
				return ((EEnum)dataType).getELiterals();
			}else if (dataType.getName().equals("EBoolean")){
				List<Object> l = new ArrayList<Object>(); l.add(true); l.add(false); return l;
			}else return null;
		}else{
			return null;
		}
	}

	/**
	 * Get the value of the feature as text for the combo field for the section.
	 *
	 * @return the value of the feature as text.
	 */
	@SuppressWarnings("rawtypes")
	protected  String getCurrentValueAsText(){
		String value = "<unknown feature>";
		if (getFeature() instanceof EStructuralFeature){
			Object featureValue = owner.eGet(getFeature());
			if (featureValue instanceof String){
				value=(String)featureValue;
			}else if (featureValue instanceof List){
				value = "";
				for (Object element : (List)featureValue) {
					if (element instanceof EventBNamed) {
						if (value!="") value=value+",";
						value = value+((EventBNamed)element).getName();
					}
				}
			}else if (featureValue instanceof EventBNamed){
				value = ((EventBNamed)featureValue).getName();
			}else if (featureValue instanceof Boolean){
				value = ((Boolean)featureValue).toString();
			}else if (featureValue instanceof Enumerator){
				value = ((Enumerator)featureValue).getLiteral();
			}else if (featureValue == null){
				value = "<null>";
			}
		}
		return value;
	}

	/**
	 * Get the new value of the feature for the text field for the section.
	 *
	 * @param index
	 *            the new index in the enumeration.
	 * @return the new value of the feature.
	 */
	@SuppressWarnings("unchecked")
	protected Object getNewValue(final int index){
		List<Object> possVals = getComboValues();
		if (possVals!=null) {
			Object newValue = possVals.get(index);
			if (newValue instanceof EEnumLiteral) newValue = ((EEnumLiteral)newValue).getInstance();		// for enumerations need to convert to the associated EEnum instance
			return newValue;
		}
		return null;
	}

	/**
	 * Get the label for the combo field for the section.
	 *
	 * @return the label for the text field.
	 */
	protected String getLabelText(){
		String label = "<unknown feature>";
		if (getFeature() instanceof EStructuralFeature){
			label= getFeature().getName();
			if (label.startsWith("B")) label=label.substring(1);
			label=label.substring(0,1).toUpperCase()+label.substring(1);
		}
		return label;
	}
}