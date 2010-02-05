/**
 * Copyright (c) 2006, 2009 
 * University of Southampton, Heinrich-Heine University Dusseldorf and others.
 * All rights reserved. This program and the accompanying materials  are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this 
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
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
 *   <li>{@link org.eventb.emf.core.EventBElement#getReference <em>Reference</em>}</li>
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
	 * The list contents are of type {@link org.eventb.emf.core.AbstractExtension}.
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
	EList<AbstractExtension> getExtensions();

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

	/**
	 * Returns the value of the '<em><b>Reference</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * Each element has a reference which is constructed from the element type and the element name.
	 * This can be used to uniquely reference the element from other components. 
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference</em>' attribute.
	 * @see #setReference(String)
	 * @see org.eventb.emf.core.CorePackage#getEventBElement_Reference()
	 * @model default="" id="true" required="true"
	 * @generated
	 */
	String getReference();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns the 'reference' attribute of this element.
	 * If this element is a proxy, the reference is obtained from the proxy URI fragment. Otherwise the value of the reference attribute is returned
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (this.eIsProxy()){\n\treturn ((InternalEObject)this).eProxyURI().fragment();\n}else{\n\treturn reference;\n}'"
	 * @generated
	 */
	String getReferenceWithoutResolving();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Sets the 'reference' attribute of this element.
	 * If this element is a proxy, the reference is seet in the proxy URI fragment. Otherwise the value of the reference attribute is set.
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='if (this.eIsProxy()){\n\t((InternalEObject)this).eProxyURI().appendFragment(newReference);\n}else{\n\treference = newReference;\n}'"
	 * @generated
	 */
	void doSetReference(String newReference);

	/**
	 * Sets the value of the '{@link org.eventb.emf.core.EventBElement#getReference <em>Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' attribute.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(String value);

} // EventBElement
