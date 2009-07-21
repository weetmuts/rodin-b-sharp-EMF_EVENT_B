/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eventb.emf.core;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event BObject</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eventb.emf.core.CorePackage#getEventBObject()
 * @model abstract="true"
 * @generated
 */
public interface EventBObject extends EObject, EModelElement {
	/**
	 * <!-- begin-user-doc -->
	 *
	 * returns the nearest container of this element that is a sub-type eClass
	 * or null if no container of that type
	 *
	 * @param 	the EClass that is the super-type of the returned elements
	 * @return 	containing EventBObject that is a sub-type of eClass
	 * <!-- end-user-doc -->
	 * @model required="true" eClassRequired="true"
	 * @generated
	 */
	EventBObject getContaining(EClass eClass);

	/**
	 * <!-- begin-user-doc -->
	 * returns a list of elements that sub-type eClass and
	 * that are contained (directly or indirectly) by this element
	 *
	 * @param eClass	the EClass that is the super-type of the returned elements
	 * @param resolve	whether to resolve proxies
	 * @return list of EObjects contained in this element that sub-type eClass
	 * <!-- end-user-doc -->
	 * @model many="false" eClassRequired="true" resolveRequired="true"
	 * @generated
	 */
	EList<EObject> getAllContained(EClass eClass, boolean resolve);

	/**
	 * <!-- begin-user-doc -->
	 * returns the URI of this element.
	 * (If the element is not loaded, the URI is obtained from the proxy without loading the element)
	 *
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated NOT
	 */
	URI getURI();

} // EventBObject
