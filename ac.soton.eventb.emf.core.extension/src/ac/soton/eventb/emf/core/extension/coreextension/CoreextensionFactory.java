/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package ac.soton.eventb.emf.core.extension.coreextension;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage
 * @generated
 */
public interface CoreextensionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CoreextensionFactory eINSTANCE = ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Typed Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Typed Parameter</em>'.
	 * @generated
	 */
	TypedParameter createTypedParameter();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CoreextensionPackage getCoreextensionPackage();

} //CoreextensionFactory
