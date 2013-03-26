/**
 * Copyright (c) 2012/13 - University of Southampton.
 * All rights reserved. This program and the accompanying materials  are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this 
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package ac.soton.eventb.emf.core.extension.coreextension;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eventb.emf.core.machine.Event;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event BEvent Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ac.soton.eventb.emf.core.extension.coreextension.EventBEventGroup#getElaborates <em>Elaborates</em>}</li>
 * </ul>
 * </p>
 *
 * @see ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage#getEventBEventGroup()
 * @model abstract="true"
 * @generated
 */
public interface EventBEventGroup extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2012/13 - University of Southampton.\rAll rights reserved. This program and the accompanying materials  are made\ravailable under the terms of the Eclipse Public License v1.0 which accompanies this \rdistribution, and is available at http://www.eclipse.org/legal/epl-v10.html\n";

	/**
	 * Returns the value of the '<em><b>Elaborates</b></em>' reference list.
	 * The list contents are of type {@link org.eventb.emf.core.machine.Event}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elaborates</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elaborates</em>' reference list.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage#getEventBEventGroup_Elaborates()
	 * @model
	 * @generated
	 */
	EList<Event> getElaborates();

} // EventBEventGroup
