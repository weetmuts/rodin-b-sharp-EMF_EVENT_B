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

import java.util.List;

import org.eclipse.emf.compare.FactoryException;
import org.eclipse.emf.compare.diff.engine.check.ReferencesCheck;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChangeRightTarget;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;

public class EventBReferencesCheck extends ReferencesCheck {

	public EventBReferencesCheck(CrossReferencer referencer) {
		super(referencer);
	}

	/**
	 * Determines if we should ignore a reference for diff detection.
	 * <p>
	 * Default is to ignore references marked either
	 * <ul>
	 * <li>Containment</li>
	 * <li>Container</li>
	 * <li>Transient</li>
	 * <li>Derived</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Clients should override this if they wish to ignore other references.
	 * </p>
	 * 
	 * @param reference
	 *            Reference to determine whether it should be ignored.
	 * @return <code>True</code> if reference has to be ignored, <code>False</code> otherwise.
	 */
	@Override
	protected boolean shouldBeIgnored(EReference reference) {
		boolean ignore = reference.isContainment();
		ignore = ignore || reference.isDerived();
		ignore = ignore || reference.isTransient();
		ignore = ignore || reference.isContainer();
		ignore = ignore || reference.eContainer() == EcorePackage.eINSTANCE.getEGenericType();
		ignore = ignore || reference.getEReferenceType().equals(EcorePackage.eINSTANCE.getEAnnotation());
		String name = reference.getName();
		ignore = ignore || "refines".equals(name);
		ignore = ignore || "sees".equals(name);
		ignore = ignore || "extends".equals(name);
		return ignore;
	}

	@Override
	protected void checkReferenceOrderChange(DiffGroup root, EReference reference, EObject leftElement, EObject rightElement,
			List<ReferenceChangeLeftTarget> addedReferences, List<ReferenceChangeRightTarget> removedReferences) throws FactoryException {
		//		if (reference.equals(CorePackage.eINSTANCE.getEventBElement_Attributes())) {
		//			return; //order of Camille text attributes changes
		//		}
		super.checkReferenceOrderChange(root, reference, leftElement, rightElement, addedReferences, removedReferences);

	}
}
