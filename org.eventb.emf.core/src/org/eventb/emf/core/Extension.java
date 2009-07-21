/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eventb.emf.core;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extension</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eventb.emf.core.Extension#getExtensionId <em>Extension Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eventb.emf.core.CorePackage#getExtension()
 * @model
 * @generated
 */
public interface Extension extends EventBElement {
	/**
	 * Returns the value of the '<em><b>Extension Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension Id</em>' attribute.
	 * @see #setExtensionId(String)
	 * @see org.eventb.emf.core.CorePackage#getExtension_ExtensionId()
	 * @model required="true"
	 * @generated
	 */
	String getExtensionId();

	/**
	 * Sets the value of the '{@link org.eventb.emf.core.Extension#getExtensionId <em>Extension Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension Id</em>' attribute.
	 * @see #getExtensionId()
	 * @generated
	 */
	void setExtensionId(String value);

} // Extension
