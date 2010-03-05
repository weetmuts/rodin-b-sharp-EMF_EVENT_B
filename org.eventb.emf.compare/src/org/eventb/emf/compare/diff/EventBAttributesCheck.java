/**
 * Copyright (c) 2010
 * University of Southampton and others.
 * All rights reserved. This program and the accompanying materials  are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this 
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 *
 * $Id$
 */

package org.eventb.emf.compare.diff;

import org.eclipse.emf.compare.diff.engine.check.AttributesCheck;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;
import org.eventb.emf.core.CorePackage;

public class EventBAttributesCheck extends AttributesCheck {

	public EventBAttributesCheck(CrossReferencer referencer) {
		super(referencer);
	}

	/**
	 * Determines if we should ignore an attribute for diff detection.
	 * <p>
	 * Default is to ignore attributes marked either
	 * <ul>
	 * <li>Transient</li>
	 * <li>Derived</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Clients should override this if they wish to ignore other attributes.
	 * </p>
	 * 
	 * @param attribute
	 *            Attribute to determine whether it should be ignored.
	 * @return <code>True</code> if attribute has to be ignored, <code>False</code> otherwise.
	 */
	@Override
	protected boolean shouldBeIgnored(EAttribute attribute) {
		boolean ignore = false;
		//		ignore = ignore || attribute.isTransient();
		//		ignore = ignore || attribute.isDerived();
		EObject container = attribute.eContainer();
		//		EObject containerContainer = container == null ? null : container.eContainer();
		//		EObject containingFeature = attribute.eContainingFeature();
		//		EObject containementFeature = attribute.eContainmentFeature();
		ignore = ignore || (container instanceof ENamedElement && "EStringToStringMapEntry".equals(((ENamedElement) container).getName()));
		ignore = ignore || attribute.eContainer().equals(EcorePackage.eINSTANCE.getEAnnotation());
		ignore = ignore || attribute.equals(CorePackage.eINSTANCE.getEventBElement_Reference());
		return ignore;
	}
}
