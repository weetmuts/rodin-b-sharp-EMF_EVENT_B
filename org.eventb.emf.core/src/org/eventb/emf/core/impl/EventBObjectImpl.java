/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eventb.emf.core.impl;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EModelElementImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event BObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class EventBObjectImpl extends EModelElementImpl implements EventBObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventBObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.EVENT_BOBJECT;
	}

	/**
	 *
	 * returns the nearest container of this element that is a sub-type eClass
	 * or null if no container of that type
	 *
	 * @param 	the EClass that is the super-type of the returned elements
	 * @return 	containing EventBObject that is a sub-type of eClass
	 * @custom
	 * @generated NOT
	 */
	public EventBObject getContaining(EClass eClass){
		EventBObject bObject = this;
		while (!eClass.isSuperTypeOf(bObject.eClass()))
			if (bObject.eContainer() instanceof EventBObject) bObject=(EventBObject)bObject.eContainer();
			else return null;
		return bObject;
	}

	/**
	 *
	 * returns a list of elements that sub-type eClass and
	 * that are contained (directly or indirectly) by this element
	 *
	 * @param eClass	the EClass that is the super-type of the returned elements
	 * @param resolve	whether to resolve proxies
	 * @return
	 * @generated NOT
	 */
	public EList<EObject> getAllContained(EClass eClass, boolean resolve){
		EList<EObject> typeObjects = new BasicEList<EObject>();
		typeObjects.add(null);	//include the null object
		for (TreeIterator<EObject>trit = EcoreUtil.getAllContents(this, resolve); trit.hasNext();){
			EObject o = trit.next();
			if (eClass.isSuperTypeOf(o.eClass())) typeObjects.add(o);
		}
		return typeObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * returns the URI of this element.
	 * (If the element is not loaded, the URI is obtained from the proxy without loading the element)
	 *
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public URI getURI() {
		if (eIsProxy()){
			return ((InternalEObject)this).eProxyURI();
		}else{
			return eResource().getURI();
		}
	}

} //EventBObjectImpl
