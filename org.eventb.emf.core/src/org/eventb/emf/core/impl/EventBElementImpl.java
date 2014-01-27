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
package org.eventb.emf.core.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eventb.emf.core.AbstractExtension;
import org.eventb.emf.core.Attribute;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBNamed;
import org.eventb.emf.core.Project;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event BElement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eventb.emf.core.impl.EventBElementImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.eventb.emf.core.impl.EventBElementImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eventb.emf.core.impl.EventBElementImpl#getReference <em>Reference</em>}</li>
 *   <li>{@link org.eventb.emf.core.impl.EventBElementImpl#isGenerated <em>Generated</em>}</li>
 *   <li>{@link org.eventb.emf.core.impl.EventBElementImpl#isLocalGenerated <em>Local Generated</em>}</li>
 *   <li>{@link org.eventb.emf.core.impl.EventBElementImpl#getInternalId <em>Internal Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EventBElementImpl extends EventBObjectImpl implements EventBElement {
	/**
	 * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensions()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractExtension> extensions;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Attribute> attributes;

	/**
	 * The default value of the '{@link #getReference() <em>Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCE_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #isGenerated() <em>Generated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATED_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isLocalGenerated() <em>Local Generated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLocalGenerated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOCAL_GENERATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLocalGenerated() <em>Local Generated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLocalGenerated()
	 * @generated
	 * @ordered
	 */
	protected boolean localGenerated = LOCAL_GENERATED_EDEFAULT;

	/**
	 * This is true if the Local Generated attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean localGeneratedESet;

	/**
	 * The default value of the '{@link #getInternalId() <em>Internal Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInternalId()
	 * @generated
	 * @ordered
	 */
	protected static final String INTERNAL_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInternalId() <em>Internal Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInternalId()
	 * @generated
	 * @ordered
	 */
	protected String internalId = INTERNAL_ID_EDEFAULT;

	/**
	 * This is true if the Internal Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean internalIdESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventBElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.EVENT_BELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AbstractExtension> getExtensions() {
		if (extensions == null) {
			extensions = new EObjectContainmentEList.Resolving<AbstractExtension>(AbstractExtension.class, this, CorePackage.EVENT_BELEMENT__EXTENSIONS);
		}
		return extensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EcoreEMap<String,Attribute>(CorePackage.Literals.STRING_TO_ATTRIBUTE_MAP_ENTRY, StringToAttributeMapEntryImpl.class, this, CorePackage.EVENT_BELEMENT__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * returns a fully qualified reference id in the format
	 * <PackageNSURI>::<class>::<parentageBelowProject>.<id>
	 * Where id is either the 'name' attribute or a UUID
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getReference() {
		return doGetReference();
	}

	/**
	 * element identification - uses name if possible,
	 * failing this, the element is given a uuid
	 * @return
	 */
	private String getElementIdentification(){
		if (this instanceof EventBNamed){
			return ((EventBNamed)this).getName();
		}else{
			if (getInternalId() == null) {
				setInternalId(EcoreUtil.generateUUID());
			}
			return getInternalId();
		}
	}
	
	/**
	 * This returns the prefix part of a reference for the type of this element
	 * @return
	 */
	public String getElementTypePrefix(){
		return this.eClass().getEPackage().getNsURI()+"::"+this.eClass().getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * this returns whether the element is considered to have been generated.
	 * An element is considered generated if either it's own 'generated' attribute is set to true,
	 * or an element that contains it is generated.
	 * 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isGenerated() {
		return localGeneratedESet && localGenerated ? true :
				eContainer() instanceof EventBElement ?
					((EventBElement)eContainer()).isGenerated() :
					false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * This sets the element to generated or not generated.
	 * Setting the current element to generated = true sets its localGenerated Flag to true. This means that all its children
	 * are also interpreted as generated (but no changes are made to their localGenerated flags).
	 * Setting the current element to generated = false will either set the local Generated flag of this element to false if it
	 * is currently set to true or, if it is unset, leave it unset,
	 * AND do the same for all of its parents.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setGenerated(boolean newGenerated) {
		if (newGenerated == true) {
			setLocalGenerated(true);
		}else{
			if (isSetLocalGenerated()){ 
				setLocalGenerated(false);
			}
			if (eContainer instanceof EventBElement){
				((EventBElement)eContainer()).setGenerated(false);
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLocalGenerated() {
		return localGenerated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalGenerated(boolean newLocalGenerated) {
		boolean oldLocalGenerated = localGenerated;
		localGenerated = newLocalGenerated;
		boolean oldLocalGeneratedESet = localGeneratedESet;
		localGeneratedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.EVENT_BELEMENT__LOCAL_GENERATED, oldLocalGenerated, localGenerated, !oldLocalGeneratedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLocalGenerated() {
		boolean oldLocalGenerated = localGenerated;
		boolean oldLocalGeneratedESet = localGeneratedESet;
		localGenerated = LOCAL_GENERATED_EDEFAULT;
		localGeneratedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CorePackage.EVENT_BELEMENT__LOCAL_GENERATED, oldLocalGenerated, LOCAL_GENERATED_EDEFAULT, oldLocalGeneratedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLocalGenerated() {
		return localGeneratedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String doGetReference() {
		if (this.eIsProxy()){
			return ((InternalEObject)this).eProxyURI().fragment();
		}else{
			String ref = getElementIdentification();
			EObject container = this.eContainer();
			while (container instanceof EventBElementImpl && !(container instanceof Project)){
				ref = ((EventBElementImpl)container).getElementIdentification()+"."+ref;
				container = container.eContainer();
			}
			ref = getElementTypePrefix()+"::"+ref;
			return ref;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.EVENT_BELEMENT__EXTENSIONS:
				return ((InternalEList<?>)getExtensions()).basicRemove(otherEnd, msgs);
			case CorePackage.EVENT_BELEMENT__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * eGet gets a feature of an object via a feature ID.
	 * This has been customised for feature ID= 3 (reference attribute)
	 * to prevent it resolving proxies if resolve is false;
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorePackage.EVENT_BELEMENT__EXTENSIONS:
				return getExtensions();
			case CorePackage.EVENT_BELEMENT__ATTRIBUTES:
				if (coreType) return getAttributes();
				else return getAttributes().map();
			case CorePackage.EVENT_BELEMENT__REFERENCE:
				return getReference();
			case CorePackage.EVENT_BELEMENT__GENERATED:
				return isGenerated();
			case CorePackage.EVENT_BELEMENT__LOCAL_GENERATED:
				return isLocalGenerated();
			case CorePackage.EVENT_BELEMENT__INTERNAL_ID:
				return getInternalId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * eSet sets a feature of an object via a feature ID.
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CorePackage.EVENT_BELEMENT__EXTENSIONS:
				getExtensions().clear();
				getExtensions().addAll((Collection<? extends AbstractExtension>)newValue);
				return;
			case CorePackage.EVENT_BELEMENT__ATTRIBUTES:
				((EStructuralFeature.Setting)getAttributes()).set(newValue);
				return;
			case CorePackage.EVENT_BELEMENT__GENERATED:
				setGenerated((Boolean)newValue);
				return;
			case CorePackage.EVENT_BELEMENT__LOCAL_GENERATED:
				setLocalGenerated((Boolean)newValue);
				return;
			case CorePackage.EVENT_BELEMENT__INTERNAL_ID:
				setInternalId((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CorePackage.EVENT_BELEMENT__EXTENSIONS:
				getExtensions().clear();
				return;
			case CorePackage.EVENT_BELEMENT__ATTRIBUTES:
				getAttributes().clear();
				return;
			case CorePackage.EVENT_BELEMENT__GENERATED:
				setGenerated(GENERATED_EDEFAULT);
				return;
			case CorePackage.EVENT_BELEMENT__LOCAL_GENERATED:
				unsetLocalGenerated();
				return;
			case CorePackage.EVENT_BELEMENT__INTERNAL_ID:
				unsetInternalId();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CorePackage.EVENT_BELEMENT__EXTENSIONS:
				return extensions != null && !extensions.isEmpty();
			case CorePackage.EVENT_BELEMENT__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case CorePackage.EVENT_BELEMENT__REFERENCE:
				return REFERENCE_EDEFAULT == null ? getReference() != null : !REFERENCE_EDEFAULT.equals(getReference());
			case CorePackage.EVENT_BELEMENT__GENERATED:
				return isGenerated() != GENERATED_EDEFAULT;
			case CorePackage.EVENT_BELEMENT__LOCAL_GENERATED:
				return isSetLocalGenerated();
			case CorePackage.EVENT_BELEMENT__INTERNAL_ID:
				return isSetInternalId();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (localGenerated: "); //$NON-NLS-1$
		if (localGeneratedESet) result.append(localGenerated); else result.append("<unset>"); //$NON-NLS-1$
		result.append(", internalId: "); //$NON-NLS-1$
		if (internalIdESet) result.append(internalId); else result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

	
	////////////////////////ONLY FOR PERSISTENCE//////////////////////
	/**
	 * returns the internal id of this element if it has one
	 * (internal id is used to construct references for elements that do not have a name)
	 * THIS METHOD IS ONLY PROVIDED FOR PERSISTENCE TO SAVE THE ID
	 * OTHER USERS SHOULD NOT BE USING THIS METHOD
	 * N.B. Named elements DO NOT have ids - they use name instead.
	 * @return UUID string or null
	 * @generated NOT
	 */	
	public String getInternalId(){
		if (this instanceof EventBNamed) return null;
		return internalId;
	}
	
	/**
	 * set the internal id of this element if it is allowed one
	 * (internal id is used to construct references for elements that do not have a name)
	 * THIS METHOD IS ONLY PROVIDED FOR PERSISTENCE TO LOAD THE ID
	 * OTHER USERS SHOULD NOT BE USING THIS METHOD
	 * N.B. Named elements DO NOT have ids - they use name instead.
	 * @param UUID string
	 * @generated NOT
	 */
	public void setInternalId(String newInternalId) {
		if (this instanceof EventBNamed) return;
		String oldInternalId = internalId;
		internalId = newInternalId;
		boolean oldInternalIdESet = internalIdESet;
		internalIdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.EVENT_BELEMENT__INTERNAL_ID, oldInternalId, internalId, !oldInternalIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetInternalId() {
		String oldInternalId = internalId;
		boolean oldInternalIdESet = internalIdESet;
		internalId = INTERNAL_ID_EDEFAULT;
		internalIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CorePackage.EVENT_BELEMENT__INTERNAL_ID, oldInternalId, INTERNAL_ID_EDEFAULT, oldInternalIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetInternalId() {
		return internalIdESet;
	}
	
	
} //EventBElementImpl
