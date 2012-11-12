/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package ac.soton.eventb.emf.core.extension.coreextension;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2012 - University of Southampton.\rAll rights reserved. This program and the accompanying materials  are made\ravailable under the terms of the Eclipse Public License v1.0 which accompanies this \rdistribution, and is available at http://www.eclipse.org/legal/epl-v10.html\n";

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
	 * The meta object id for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBLabeledImpl <em>Event BLabeled</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.EventBLabeledImpl
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getEventBLabeled()
	 * @generated
	 */
	int EVENT_BLABELED = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BLABELED__LABEL = 0;

	/**
	 * The number of structural features of the '<em>Event BLabeled</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BLABELED_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBRelationKindImpl <em>Event BRelation Kind</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.EventBRelationKindImpl
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getEventBRelationKind()
	 * @generated
	 */
	int EVENT_BRELATION_KIND = 3;

	/**
	 * The feature id for the '<em><b>Surjective</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BRELATION_KIND__SURJECTIVE = 0;

	/**
	 * The feature id for the '<em><b>Injective</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BRELATION_KIND__INJECTIVE = 1;

	/**
	 * The feature id for the '<em><b>Total</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BRELATION_KIND__TOTAL = 2;

	/**
	 * The feature id for the '<em><b>Functional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BRELATION_KIND__FUNCTIONAL = 3;

	/**
	 * The number of structural features of the '<em>Event BRelation Kind</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BRELATION_KIND_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBDataElaborationImpl <em>Event BData Elaboration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.EventBDataElaborationImpl
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getEventBDataElaboration()
	 * @generated
	 */
	int EVENT_BDATA_ELABORATION = 4;

	/**
	 * The feature id for the '<em><b>Elaborates</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BDATA_ELABORATION__ELABORATES = 0;

	/**
	 * The feature id for the '<em><b>Data Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BDATA_ELABORATION__DATA_KIND = 1;

	/**
	 * The number of structural features of the '<em>Event BData Elaboration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BDATA_ELABORATION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBEventGroupImpl <em>Event BEvent Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.EventBEventGroupImpl
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getEventBEventGroup()
	 * @generated
	 */
	int EVENT_BEVENT_GROUP = 5;

	/**
	 * The feature id for the '<em><b>Elaborates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BEVENT_GROUP__ELABORATES = 0;

	/**
	 * The number of structural features of the '<em>Event BEvent Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_BEVENT_GROUP_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link ac.soton.eventb.emf.core.extension.coreextension.DataKind <em>Data Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ac.soton.eventb.emf.core.extension.coreextension.DataKind
	 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getDataKind()
	 * @generated
	 */
	int DATA_KIND = 6;


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
	 * Returns the meta object for class '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBLabeled <em>Event BLabeled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event BLabeled</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBLabeled
	 * @generated
	 */
	EClass getEventBLabeled();

	/**
	 * Returns the meta object for the attribute '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBLabeled#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBLabeled#getLabel()
	 * @see #getEventBLabeled()
	 * @generated
	 */
	EAttribute getEventBLabeled_Label();

	/**
	 * Returns the meta object for class '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind <em>Event BRelation Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event BRelation Kind</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind
	 * @generated
	 */
	EClass getEventBRelationKind();

	/**
	 * Returns the meta object for the attribute '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind#isSurjective <em>Surjective</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Surjective</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind#isSurjective()
	 * @see #getEventBRelationKind()
	 * @generated
	 */
	EAttribute getEventBRelationKind_Surjective();

	/**
	 * Returns the meta object for the attribute '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind#isInjective <em>Injective</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Injective</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind#isInjective()
	 * @see #getEventBRelationKind()
	 * @generated
	 */
	EAttribute getEventBRelationKind_Injective();

	/**
	 * Returns the meta object for the attribute '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind#isTotal <em>Total</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind#isTotal()
	 * @see #getEventBRelationKind()
	 * @generated
	 */
	EAttribute getEventBRelationKind_Total();

	/**
	 * Returns the meta object for the attribute '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind#isFunctional <em>Functional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Functional</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBRelationKind#isFunctional()
	 * @see #getEventBRelationKind()
	 * @generated
	 */
	EAttribute getEventBRelationKind_Functional();

	/**
	 * Returns the meta object for class '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBDataElaboration <em>Event BData Elaboration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event BData Elaboration</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBDataElaboration
	 * @generated
	 */
	EClass getEventBDataElaboration();

	/**
	 * Returns the meta object for the reference '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBDataElaboration#getElaborates <em>Elaborates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Elaborates</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBDataElaboration#getElaborates()
	 * @see #getEventBDataElaboration()
	 * @generated
	 */
	EReference getEventBDataElaboration_Elaborates();

	/**
	 * Returns the meta object for the attribute '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBDataElaboration#getDataKind <em>Data Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Kind</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBDataElaboration#getDataKind()
	 * @see #getEventBDataElaboration()
	 * @generated
	 */
	EAttribute getEventBDataElaboration_DataKind();

	/**
	 * Returns the meta object for class '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBEventGroup <em>Event BEvent Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event BEvent Group</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBEventGroup
	 * @generated
	 */
	EClass getEventBEventGroup();

	/**
	 * Returns the meta object for the reference list '{@link ac.soton.eventb.emf.core.extension.coreextension.EventBEventGroup#getElaborates <em>Elaborates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Elaborates</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.EventBEventGroup#getElaborates()
	 * @see #getEventBEventGroup()
	 * @generated
	 */
	EReference getEventBEventGroup_Elaborates();

	/**
	 * Returns the meta object for enum '{@link ac.soton.eventb.emf.core.extension.coreextension.DataKind <em>Data Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Kind</em>'.
	 * @see ac.soton.eventb.emf.core.extension.coreextension.DataKind
	 * @generated
	 */
	EEnum getDataKind();

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

		/**
		 * The meta object literal for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBLabeledImpl <em>Event BLabeled</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.EventBLabeledImpl
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getEventBLabeled()
		 * @generated
		 */
		EClass EVENT_BLABELED = eINSTANCE.getEventBLabeled();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_BLABELED__LABEL = eINSTANCE.getEventBLabeled_Label();

		/**
		 * The meta object literal for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBRelationKindImpl <em>Event BRelation Kind</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.EventBRelationKindImpl
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getEventBRelationKind()
		 * @generated
		 */
		EClass EVENT_BRELATION_KIND = eINSTANCE.getEventBRelationKind();

		/**
		 * The meta object literal for the '<em><b>Surjective</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_BRELATION_KIND__SURJECTIVE = eINSTANCE.getEventBRelationKind_Surjective();

		/**
		 * The meta object literal for the '<em><b>Injective</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_BRELATION_KIND__INJECTIVE = eINSTANCE.getEventBRelationKind_Injective();

		/**
		 * The meta object literal for the '<em><b>Total</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_BRELATION_KIND__TOTAL = eINSTANCE.getEventBRelationKind_Total();

		/**
		 * The meta object literal for the '<em><b>Functional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_BRELATION_KIND__FUNCTIONAL = eINSTANCE.getEventBRelationKind_Functional();

		/**
		 * The meta object literal for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBDataElaborationImpl <em>Event BData Elaboration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.EventBDataElaborationImpl
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getEventBDataElaboration()
		 * @generated
		 */
		EClass EVENT_BDATA_ELABORATION = eINSTANCE.getEventBDataElaboration();

		/**
		 * The meta object literal for the '<em><b>Elaborates</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_BDATA_ELABORATION__ELABORATES = eINSTANCE.getEventBDataElaboration_Elaborates();

		/**
		 * The meta object literal for the '<em><b>Data Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_BDATA_ELABORATION__DATA_KIND = eINSTANCE.getEventBDataElaboration_DataKind();

		/**
		 * The meta object literal for the '{@link ac.soton.eventb.emf.core.extension.coreextension.impl.EventBEventGroupImpl <em>Event BEvent Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.EventBEventGroupImpl
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getEventBEventGroup()
		 * @generated
		 */
		EClass EVENT_BEVENT_GROUP = eINSTANCE.getEventBEventGroup();

		/**
		 * The meta object literal for the '<em><b>Elaborates</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_BEVENT_GROUP__ELABORATES = eINSTANCE.getEventBEventGroup_Elaborates();

		/**
		 * The meta object literal for the '{@link ac.soton.eventb.emf.core.extension.coreextension.DataKind <em>Data Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ac.soton.eventb.emf.core.extension.coreextension.DataKind
		 * @see ac.soton.eventb.emf.core.extension.coreextension.impl.CoreextensionPackageImpl#getDataKind()
		 * @generated
		 */
		EEnum DATA_KIND = eINSTANCE.getDataKind();

	}

} //CoreextensionPackage
