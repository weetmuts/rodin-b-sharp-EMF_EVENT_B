/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eventb.emf.edit.properties;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBNamed;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.EventBPredicate;
import org.eventb.emf.core.machine.Action;
import org.eventb.emf.core.machine.Convergence;
import org.eventb.eventBKeyboard.EventBTextModifyListener;
import org.eventb.eventBKeyboard.preferences.PreferenceConstants;


/**
 *
 *
 */


public class SetValuesDialog extends Dialog {

	private class DefaultValue {

		protected Method method;

		protected String label;

		protected Object value;

		protected Object widget;

		protected DefaultValue(final Method method) {
			this.method = method;
			label = method.getName() + ":";//$NON-NLS-1$
		}

		@Override
		public String toString() {
			return method.toString();
		}
	}

	//private EventBTextModifyListener eventBListener;


	private EventBObject owner = null;

	private EventBObject child = null;

	private ArrayList<DefaultValue> defaultValues = null;
	private String featureName;
	private EStructuralFeature feature;


	public SetValuesDialog(final Command command) {
		super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		Object o; Object c;
		if (command instanceof AddCommand){
			o = ((AddCommand)command).getOwner();
			c = ((AddCommand)command).getCollection().iterator().next();
			feature = ((AddCommand)command).getFeature();
		}else if (command instanceof SetCommand){
			o = ((SetCommand)command).getOwner();
			c = ((SetCommand)command).getValue();
			feature = ((SetCommand)command).getFeature();
		}else return;
		if (!(o instanceof EventBObject && c instanceof EventBObject)) return;
		this.owner = (EventBObject) o;
		this.child = (EventBObject) c;
		this.defaultValues = getDefaultValues(child);
		setFeatureName();
		setPropertiesOrder();
	}

	private void setPropertiesOrder() {
//		// Swap the order in which the properties are displayed
//		if (child instanceof UMLBAttribute){
//			Collections.swap(defaultValues, 6, 0);
//			Collections.swap(defaultValues, 5, 1);
//			Collections.swap(defaultValues, 6, 2);
//			Collections.swap(defaultValues, 5, 3);
//			Collections.swap(defaultValues, 6, 4);
//		}else if (child instanceof UMLBConstantAttribute){
//			Collections.swap(defaultValues, 5, 0);
//			Collections.swap(defaultValues, 4, 1);
//			Collections.swap(defaultValues, 5, 2);
//			Collections.swap(defaultValues, 4, 3);
//			Collections.swap(defaultValues, 5, 4);
//		}else if (child instanceof UMLBStatemachine) Collections.swap(defaultValues, 0, 1);
//		else if (child instanceof UMLBPredicate) Collections.swap(defaultValues, 0, 1);
//		else if (child instanceof UMLBEventVariable) Collections.swap(defaultValues, 0, 2);
//		else if (child instanceof UMLBAction) 	Collections.swap(defaultValues, 0, 1);
//		else if (child instanceof UMLBVariable) Collections.swap(defaultValues, 0, 2);
//		else if (child instanceof UMLBConstant) Collections.swap(defaultValues, 0, 1);
//		else if (child instanceof UMLBEvent)	Collections.swap(defaultValues, 0, 2);
//		else if (child instanceof UMLBMachine) ; //no swap needed
//		else if (child instanceof UMLBContext) ; //no swap needed
	}

	private void setFeatureName(){
		featureName = feature.getName();
		if (featureName.charAt(featureName.length()-1) == 's'){
			featureName=featureName.substring(0, featureName.length()-1);
		}
		featureName= featureName.replaceFirst(featureName.substring(0, 1), featureName.substring(0, 1).toUpperCase());

//		if (featureName.equals("Component")){
//			if (owner instanceof BObject){
//				BComponent bComponent = (BComponent)owner.getContaining(ProjectPackage.eINSTANCE.getBComponent());
//				if (bComponent instanceof BMachine) featureName= "Invariant";
//				if (bComponent instanceof BContext) featureName= "Axiom";
//			}
//		}else if (featureName.equals("Construct")){
//			if (child instanceof BMachine) featureName= "Machine";
//			if (child instanceof BContext) featureName= "Context";
//
//		}else if (featureName.equals("Variable") && owner instanceof BguardedAction){
//			featureName = "Parameter";
//		}
	}

	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
		shell.setText("Add New " + featureName);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		Group group = createGroup(composite, child.eClass().getName(), 2);

		for (DefaultValue defaultValue : defaultValues) {

			//if label is "Refines:", do not add to feature to the dialog box (skip this iteration)
			if (defaultValue.label.equals("Refines:")) continue;
			//if label is "Refines:", do not add to feature to the dialog box (skip this iteration)
			if (defaultValue.label.equals("Variant:")) continue;

			Label label = createLabel(group, defaultValue.label);

			label.setToolTipText(""); //$NON-NLS-1$
			Class<?> setType = defaultValue.method.getParameterTypes()[0];

			String defText = getDefaultTextName(defaultValue);

			if (setType.equals(String.class)) {
				defaultValue.widget = createTextField(group);
				((Text) defaultValue.widget).setText(defText);
			} else if (setType.equals(int.class)) {
				defaultValue.widget = createTextField(group);
				((Text) defaultValue.widget).setText("0");//$NON-NLS-1$
			} else if (setType.equals(float.class)) {
				defaultValue.widget = createTextField(group);
				((Text) defaultValue.widget).setText("0.0F");//$NON-NLS-1$
			} else if (setType.getName().equals("boolean")) defaultValue.widget = createCheckBox(group, "Yes"); //$NON-NLS-1$
			else if (setType.getName().equals("org.eventb.emf.core.BConvergence")) defaultValue.widget = createCombo(group,Convergence.VALUES);
		}
		return composite;
	}


	private String getDefaultTextName(final DefaultValue defaultValue){

		//If label text is "Name", set the default value of the field to be the predicate name + next number. (e.g. "Theorem2")
		//If the label is a predicate value (e.g. Theorem or Constraint), set the default value of the field to be 1=1
		if 	(defaultValue.label.equals("Comment:")) return "";
		else if (defaultValue.label.equals("Name:"))
			if (child instanceof EventBNamed){
				return NameUtils.getSafeName(((EventBElement)child), featureName, owner, feature);
			}else
				return "ERROR - element is not a named element";
		else if (defaultValue.label.equals("Invariant:")||
				defaultValue.label.equals("Axiom:")||
				defaultValue.label.equals("Theorem:")||
				defaultValue.label.equals("Predicate:")||
				defaultValue.label.equals("Witness:")||
				defaultValue.label.equals("Guard:")||
				defaultValue.label.equals("Attribute:")||
				defaultValue.label.equals("Action:")){
			if (child instanceof EventBPredicate) return ((EventBPredicate)child).getPredicate();
			else if (child instanceof Action) return ((Action)child).getAction();
		}
		return defaultValue.method.getName().substring(3);
	}


	protected Group createGroup(final Composite parent, final String text, final int numColumns) {
		Group composite = new Group(parent, SWT.NONE);
		composite.setText(text);
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		layout.makeColumnsEqualWidth = false;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(data);
		return composite;
	}

	protected Label createLabel(final Composite parent, final String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	protected Text createTextField(final Composite parent) {
		Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = GridData.CENTER;
		data.grabExcessVerticalSpace = false;
		data.widthHint = 250;
		text.setLayoutData(data);
		text.addModifyListener(new EventBTextModifyListener());
		Font font = JFaceResources.getFont(PreferenceConstants.EVENTB_MATH_FONT);
		text.setFont(font);
		return text;
	}

	protected Button createCheckBox(final Composite group, final String label) {
		Button button = new Button(group, SWT.CHECK | SWT.LEFT);
		button.setText(label);
		GridData data = new GridData();
		button.setLayoutData(data);
		return button;
	}

	protected Combo createCombo(final Composite parent, final List<? extends Object> list) {
		if (list==null) return null;
		String[] items = new String[list.size()];
		int i=0;
		for (Object o: list) {
			if (o instanceof Enumerator ) {
				items[i]=((Enumerator)o).getName();
				if (items[i]==null) items[i]="<null>";
			}else if (o instanceof String){
				items[i] = (String) o;
			}
			i++;
		}

		return createCombo(parent,items);
	}

	protected Combo createCombo(final Composite parent, final String[] items) {
		Combo combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.setFont(JFaceResources.getFont(PreferenceConstants.EVENTB_MATH_FONT));
		combo.setItems(items);
		combo.select(0);
		return combo;
	}

	@Override
	protected void okPressed() {
		for (DefaultValue defaultValue : defaultValues) {
			if (defaultValue.widget==null) continue;
			Class<?> setType = defaultValue.method.getParameterTypes()[0];
			if (setType.equals(String.class)) {
				defaultValue.value = ((Text) defaultValue.widget).getText();
				if (child instanceof EventBNamed){
					boolean exists = NameUtils.nameCollision(((EventBElement)child),defaultValue.value.toString(),owner,feature);
					if (exists==true){
						MessageDialog.openError(this.getParentShell(),"ERROR", "The element cannot be renamed because another element already exists with that name.");
						return;
					}
				}

			} else if (setType.equals(int.class)) {
				String text = ((Text) defaultValue.widget).getText();
				defaultValue.value = Integer.valueOf(text);
			} else if (setType.equals(float.class)) {
				String text = ((Text) defaultValue.widget).getText();
				defaultValue.value = Float.valueOf(text);
			} else if (setType.getName().equals("boolean")) {
				defaultValue.value = ((Button) defaultValue.widget)
				.getSelection() ? true
				: false;
			}
			else if (setType.getName().equals("org.eventb.emf.core.BConvergence")) defaultValue.value = Convergence.get(((Combo) defaultValue.widget).getSelectionIndex());
		}
		setDefaultValues(defaultValues, child);
		super.okPressed();
	}

	private ArrayList<DefaultValue> getDefaultValues(final EObject aChild) {
		ArrayList<DefaultValue> ret = new ArrayList<DefaultValue>();
		Class<?> childClassImpl = aChild.getClass();
		Method[] methods = childClassImpl.getMethods();
		for (Method element : methods)
			if (element.getName().startsWith("set")) {//$NON-NLS-1$
				Class<?> setType = element.getParameterTypes()[0];
				if (!setType.equals(Class.class)
					&& !setType.equals(EList.class)) ret.add(new DefaultValue(element));
			}
		return ret;
	}

	private void setDefaultValues(final ArrayList<DefaultValue> defaultValues, final EObject child) {
		for (DefaultValue defaultValue : defaultValues) {
			if (defaultValue.label.equals("Refines:")) continue;	//Note - Refines is not set by this Dialog
			try {
				defaultValue.method.invoke(child, new Object[] {defaultValue.value});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}