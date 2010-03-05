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

import org.eclipse.emf.compare.diff.engine.GenericDiffEngine;
import org.eclipse.emf.compare.diff.engine.check.AttributesCheck;
import org.eclipse.emf.compare.diff.engine.check.ReferencesCheck;

public class EventBDiffEngine extends GenericDiffEngine {

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

}
