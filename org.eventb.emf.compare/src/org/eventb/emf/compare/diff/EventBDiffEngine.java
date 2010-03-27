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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.compare.diff.engine.GenericDiffEngine;
import org.eclipse.emf.compare.diff.engine.check.AttributesCheck;
import org.eclipse.emf.compare.diff.engine.check.ReferencesCheck;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.match.metamodel.UnmatchElement;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;

public class EventBDiffEngine extends GenericDiffEngine {

	private static final String RodinInternalDetails = "http:///org/eventb/core/RodinInternalAnnotations";

	/**
	 * Returns the Event-B implementation of a {@link org.eclipse.emf.compare.diff.engine.check.AbstractCheck} responsible for the verification of updates on attribute values.
	 * 
	 * @return The Event-B implementation of a {@link org.eclipse.emf.compare.diff.engine.check.AbstractCheck} responsible for the verification of updates on attribute values.
	 * @since 1.0
	 */
	@Override
	protected AttributesCheck getAttributesChecker() {
		return new EventBAttributesCheck(matchCrossReferencer);
	}

	/**
	 * Returns the Event-B implementation of a {@link org.eclipse.emf.compare.diff.engine.check.AbstractCheck} responsible for the verification of updates on reference values.
	 * 
	 * @return The Event-B implementation of a {@link org.eclipse.emf.compare.diff.engine.check.AbstractCheck} responsible for the verification of updates on reference values.
	 * @since 1.0
	 */
	@Override
	protected ReferencesCheck getReferencesChecker() {
		return new EventBReferencesCheck(matchCrossReferencer);
	}

	/**
	 * This will process the {@link #unmatchedElements unmatched elements} list and create the appropriate {@link DiffElement}s.
	 * <p>
	 * This is called for two-way comparison.
	 * </p>
	 * <p>
	 * Filters Rodin internal detail EAnnotation and then defers to the generic diff engine.
	 * </p>
	 * <p>
	 * FIXME: Make this extensible via an extension point so that extenders can decide what should be ignored.
	 * </p>
	 * 
	 * @param diffRoot
	 *            {@link DiffGroup} under which to create the {@link DiffElement}s.
	 * @param unmatched
	 *            The MatchModel's {@link UnmatchElement}s.
	 */
	@Override
	protected void processUnmatchedElements(DiffGroup diffRoot, List<UnmatchElement> unmatched) {
		final List<UnmatchElement> filteredUnmatched = new ArrayList<UnmatchElement>(unmatched.size());
		for (final UnmatchElement element : unmatched) {
			EObject model = element.getElement();
			if (!(model instanceof EAnnotation && RodinInternalDetails.equals(((EAnnotation) model).getSource()))) {
				filteredUnmatched.add(element);
			}
		}
		super.processUnmatchedElements(diffRoot, filteredUnmatched);
	}

	/**
	 * This will process the {@link #unmatchedElements unmatched elements} list and create the appropriate {@link DiffElement}s.
	 * <p>
	 * This is called for three-way comparison. Clients can override this to alter the checks or add their own.
	 * </p>
	 * <p>
	 * Filters Rodin internal detail EAnnotation and then defers to the generic diff engine.
	 * </p>
	 * <p>
	 * FIXME: Make this extensible via an extension point so that extenders can decide what should be ignored.
	 * </p>
	 * 
	 * @param diffRoot
	 *            {@link DiffGroup} under which to create the {@link DiffElement}s.
	 * @param unmatched
	 *            The MatchModel's {@link UnmatchElement}s.
	 */
	@Override
	protected void processUnmatchedElements(DiffGroup diffRoot, Map<UnmatchElement, Boolean> unmatched) {
		final Map<UnmatchElement, Boolean> filteredUnmatched = new HashMap<UnmatchElement, Boolean>(unmatched.size());

		for (final Map.Entry<UnmatchElement, Boolean> element : unmatched.entrySet()) {
			EObject model = element.getKey().getElement();
			if (!(model instanceof EAnnotation && RodinInternalDetails.equals(((EAnnotation) model).getSource()))) {
				filteredUnmatched.put(element.getKey(), element.getValue());
			}
		}
		super.processUnmatchedElements(diffRoot, filteredUnmatched);
	}

}
