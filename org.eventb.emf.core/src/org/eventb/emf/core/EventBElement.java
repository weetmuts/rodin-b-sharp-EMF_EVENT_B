/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eventb.emf.core;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event BElement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eventb.emf.core.EventBElement#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.eventb.emf.core.EventBElement#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eventb.emf.core.CorePackage#getEventBElement()
 * @model abstract="true"
 * @generated
 */
public interface EventBElement extends EventBObject {
	/**
	 * Returns the value of the '<em><b>Extensions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eventb.emf.core.Extension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extensions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extensions</em>' containment reference list.
	 * @see org.eventb.emf.core.CorePackage#getEventBElement_Extensions()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Extension> getExtensions();

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eventb.emf.core.Attribute},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' map.
	 * @see org.eventb.emf.core.CorePackage#getEventBElement_Attributes()
	 * @model mapType="org.eventb.emf.core.StringToAttributeMapEntry<org.eclipse.emf.ecore.EString, org.eventb.emf.core.Attribute>"
	 * @generated
	 */
	EMap<String, Attribute> getAttributes();

} // EventBElement
