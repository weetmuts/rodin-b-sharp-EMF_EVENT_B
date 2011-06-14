/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package ac.soton.eventb.emf.core.extension.coreextension.impl;

import ac.soton.eventb.emf.core.extension.coreextension.CoreextensionFactory;
import ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage;
import ac.soton.eventb.emf.core.extension.coreextension.EventBLabeled;
import ac.soton.eventb.emf.core.extension.coreextension.Type;
import ac.soton.eventb.emf.core.extension.coreextension.TypedParameter;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eventb.emf.core.CorePackage;

import org.eventb.emf.core.machine.MachinePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CoreextensionPackageImpl extends EPackageImpl implements CoreextensionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typedParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventBLabeledEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see ac.soton.eventb.emf.core.extension.coreextension.CoreextensionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CoreextensionPackageImpl() {
		super(eNS_URI, CoreextensionFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link CoreextensionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CoreextensionPackage init() {
		if (isInited) return (CoreextensionPackage)EPackage.Registry.INSTANCE.getEPackage(CoreextensionPackage.eNS_URI);

		// Obtain or create and register package
		CoreextensionPackageImpl theCoreextensionPackage = (CoreextensionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CoreextensionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CoreextensionPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		CorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCoreextensionPackage.createPackageContents();

		// Initialize created meta-data
		theCoreextensionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCoreextensionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CoreextensionPackage.eNS_URI, theCoreextensionPackage);
		return theCoreextensionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTypedParameter() {
		return typedParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getType_Type() {
		return (EAttribute)typeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventBLabeled() {
		return eventBLabeledEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventBLabeled_Label() {
		return (EAttribute)eventBLabeledEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoreextensionFactory getCoreextensionFactory() {
		return (CoreextensionFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		typedParameterEClass = createEClass(TYPED_PARAMETER);

		typeEClass = createEClass(TYPE);
		createEAttribute(typeEClass, TYPE__TYPE);

		eventBLabeledEClass = createEClass(EVENT_BLABELED);
		createEAttribute(eventBLabeledEClass, EVENT_BLABELED__LABEL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		MachinePackage theMachinePackage = (MachinePackage)EPackage.Registry.INSTANCE.getEPackage(MachinePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		typedParameterEClass.getESuperTypes().add(theMachinePackage.getParameter());
		typedParameterEClass.getESuperTypes().add(this.getType());

		// Initialize classes and features; add operations and parameters
		initEClass(typedParameterEClass, TypedParameter.class, "TypedParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(typeEClass, Type.class, "Type", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getType_Type(), ecorePackage.getEString(), "type", null, 1, 1, Type.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventBLabeledEClass, EventBLabeled.class, "EventBLabeled", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventBLabeled_Label(), ecorePackage.getEString(), "label", "", 0, 1, EventBLabeled.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CoreextensionPackageImpl
