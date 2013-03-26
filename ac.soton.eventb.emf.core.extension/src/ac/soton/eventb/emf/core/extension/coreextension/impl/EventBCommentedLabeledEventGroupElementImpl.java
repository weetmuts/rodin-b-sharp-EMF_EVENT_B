/**
 * Copyright (c) 2012/13 - University of Southampton.
 * All rights reserved. This program and the accompanying materials  are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this 
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package ac.soton.eventb.emf.core.extension.coreextension.impl;

import ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage;
import ac.soton.eventb.emf.core.extension.coreextension.EventBCommentedLabeledEventGroupElement;
import ac.soton.eventb.emf.core.extension.coreextension.EventBEventGroup;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eventb.emf.core.machine.Event;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event BCommented Labeled Event Group Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBCommentedLabeledEventGroupElementImpl#getElaborates <em>Elaborates</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EventBCommentedLabeledEventGroupElementImpl extends EventBCommentedLabeledElementImpl implements EventBCommentedLabeledEventGroupElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2012/13 - University of Southampton.\rAll rights reserved. This program and the accompanying materials  are made\ravailable under the terms of the Eclipse Public License v1.0 which accompanies this \rdistribution, and is available at http://www.eclipse.org/legal/epl-v10.html\n";

	/**
	 * The cached value of the '{@link #getElaborates() <em>Elaborates</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElaborates()
	 * @generated
	 * @ordered
	 */
	protected EList<Event> elaborates;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventBCommentedLabeledEventGroupElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoreextensionPackage.Literals.EVENT_BCOMMENTED_LABELED_EVENT_GROUP_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Event> getElaborates() {
		if (elaborates == null) {
			elaborates = new EObjectResolvingEList<Event>(Event.class, this, CoreextensionPackage.EVENT_BCOMMENTED_LABELED_EVENT_GROUP_ELEMENT__ELABORATES);
		}
		return elaborates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoreextensionPackage.EVENT_BCOMMENTED_LABELED_EVENT_GROUP_ELEMENT__ELABORATES:
				return getElaborates();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CoreextensionPackage.EVENT_BCOMMENTED_LABELED_EVENT_GROUP_ELEMENT__ELABORATES:
				getElaborates().clear();
				getElaborates().addAll((Collection<? extends Event>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CoreextensionPackage.EVENT_BCOMMENTED_LABELED_EVENT_GROUP_ELEMENT__ELABORATES:
				getElaborates().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CoreextensionPackage.EVENT_BCOMMENTED_LABELED_EVENT_GROUP_ELEMENT__ELABORATES:
				return elaborates != null && !elaborates.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == EventBEventGroup.class) {
			switch (derivedFeatureID) {
				case CoreextensionPackage.EVENT_BCOMMENTED_LABELED_EVENT_GROUP_ELEMENT__ELABORATES: return CoreextensionPackage.EVENT_BEVENT_GROUP__ELABORATES;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == EventBEventGroup.class) {
			switch (baseFeatureID) {
				case CoreextensionPackage.EVENT_BEVENT_GROUP__ELABORATES: return CoreextensionPackage.EVENT_BCOMMENTED_LABELED_EVENT_GROUP_ELEMENT__ELABORATES;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //EventBCommentedLabeledEventGroupElementImpl
