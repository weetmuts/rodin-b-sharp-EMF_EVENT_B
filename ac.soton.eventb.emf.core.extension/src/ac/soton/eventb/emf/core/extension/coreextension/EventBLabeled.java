/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package ac.soton.eventb.emf.core.extension.coreextension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event BLabeled</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ac.soton.eventb.emf.core.extension.coreextension.EventBLabeled#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage#getEventBLabeled()
 * @model abstract="true"
 * @generated
 */
public interface EventBLabeled extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage#getEventBLabeled_Label()
	 * @model default="" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getLabel();

} // EventBLabeled
