/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package ac.soton.eventb.emf.core.extension.coreextension;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eventb.emf.core.machine.MachinePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see ac.soton.eventb.emf.core.extension.coreextension.CoreextensionFactory
 * @model kind="package"
 * @generated
 */
public interface CoreextensionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "coreextension";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://soton.ac.uk/models/eventb/coreextension";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "coreextension";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CoreextensionPackage eINSTANCE = ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl.init();

	/**
	 * The meta object id for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.TypedParameterImpl <em>Typed Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.TypedParameterImpl
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getTypedParameter()
	 * @generated
	 */
	int TYPED_PARAMETER = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER__ANNOTATIONS = MachinePackage.PARAMETER__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER__EXTENSIONS = MachinePackage.PARAMETER__EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER__ATTRIBUTES = MachinePackage.PARAMETER__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER__REFERENCE = MachinePackage.PARAMETER__REFERENCE;

	/**
	 * The feature id for the '<em><b>Generated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER__GENERATED = MachinePackage.PARAMETER__GENERATED;

	/**
	 * The feature id for the '<em><b>Local Generated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER__LOCAL_GENERATED = MachinePackage.PARAMETER__LOCAL_GENERATED;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER__COMMENT = MachinePackage.PARAMETER__COMMENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER__NAME = MachinePackage.PARAMETER__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER__TYPE = MachinePackage.PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Typed Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_PARAMETER_FEATURE_COUNT = MachinePackage.PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.TypeImpl
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link ac.soton.eventb.emf.core.extension.coreextension.TypedParameter <em>Typed Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Parameter</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.TypedParameter
	 * @generated
	 */
	EClass getTypedParameter();

	/**
	 * Returns the meta object for class '{@link ac.soton.eventb.emf.core.extension.coreextension.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the attribute '{@link ac.soton.eventb.emf.core.extension.coreextension.Type#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.Type#getType()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CoreextensionFactory getCoreextensionFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.TypedParameterImpl <em>Typed Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.TypedParameterImpl
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getTypedParameter()
		 * @generated
		 */
		EClass TYPED_PARAMETER = eINSTANCE.getTypedParameter();

		/**
		 * The meta object literal for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.TypeImpl
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__TYPE = eINSTANCE.getType_Type();

	}

} //CoreextensionPackage
