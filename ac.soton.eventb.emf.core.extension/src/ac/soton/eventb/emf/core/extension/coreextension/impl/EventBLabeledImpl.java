/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package ac.soton.eventb.emf.core.extension.coreextension.impl;

import java.util.ArrayList;

import ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage;
import ac.soton.eventb.emf.core.extension.coreextension.EventBLabeled;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eventb.emf.core.EventBNamed;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event BLabeled</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBLabeledImpl#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EventBLabeledImpl extends EObjectImpl implements EventBLabeled {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2012 - University of Southampton.\rAll rights reserved. This program and the accompanying materials  are made\ravailable under the terms of the Eclipse Public License v1.0 which accompanies this \rdistribution, and is available at http://www.eclipse.org/legal/epl-v10.html\n";
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = "";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventBLabeledImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoreextensionPackage.Literals.EVENT_BLABELED;
	}

	/**
	 * <!-- begin-user-doc -->
	 * The default implementation provides a label from other features that appear suitable
	 * according to their feature name.
	 * 
	 * I.e.
	 * if this element has an attribute feature called 'name', the value of name is returned,
	 * else, if the element has a relationship feature called refines, inherits or elaborates
	 * the label of the element targeted by this relationship is returned (note this may be recursive).
	 * if the relationship is 'many' a comma separated list is returned 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * 
	 */
	public String getLabel() {
		Object label=null;
		EStructuralFeature labelFeature = eClass().getEStructuralFeature("name");
		//if this has a name return that
		if (labelFeature != null && labelFeature.getEType() == EcorePackage.eINSTANCE.getEString()) 
			return  (String)eGet(labelFeature);
		//otherwise look for a reference to something that may have a suitable label
		labelFeature = eClass().getEStructuralFeature("refines");
		if (labelFeature == null) 
			labelFeature = eClass().getEStructuralFeature("inherits");
		if (labelFeature == null) 
			labelFeature = eClass().getEStructuralFeature("elaborates");		
		if (labelFeature != null)
			label = labelFeature.eGet(labelFeature);
		if (labelFeature.isMany() && label instanceof EList){
			EList<?> elements = (EList<?>) label;
			if (elements.isEmpty())
				return "";//"
			ArrayList<String> result = new ArrayList<String>(elements.size());
			for (Object element : elements)
				if (element instanceof EventBNamed){
					result.add(((EventBNamed)element).getName());
				}else if (element instanceof EventBLabeled){
					result.add(((EventBLabeled)element).getLabel());
				}
			return result.toString().replaceAll("(^.)|(.$)", "");
		}else{
			if (label instanceof EventBNamed){
				return ((EventBNamed)label).getName();
			}else if (label instanceof EventBLabeled){
				return ((EventBLabeled)label).getLabel();
			}
		}
		if (label instanceof String){
			return (String)label;
		}else{
			return "";
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoreextensionPackage.EVENT_BLABELED__LABEL:
				return getLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CoreextensionPackage.EVENT_BLABELED__LABEL:
				return LABEL_EDEFAULT == null ? getLabel() != null : !LABEL_EDEFAULT.equals(getLabel());
		}
		return super.eIsSet(featureID);
	}

} //EventBLabeledImpl
