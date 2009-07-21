/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eventb.emf.edit.propertySections.abstracts;

import org.eclipse.emf.ecore.EAttribute;

/**
 * An abstract implementation of a section for a field with a String property
 * value.
 *
 */

public abstract class AbstractStringPropertySection extends AbstractTextPropertySection {

	@Override
	protected boolean isCurrent(final String newText) {
		return getFeatureAsText().equals(newText);
	}

	@Override
	protected String getFeatureAsText() {
		String string=null;
		Object feature = null;
		if (getFeature() instanceof EAttribute){
			feature = owner.eGet(getFeature());
			if (feature instanceof String) {
				string = (String)feature;
				if (string == null) return "";//$NON-NLS-1$
				return string;
			}else if (feature==null){
				return "";
			}
		}
		return "ERROR - feature not string";
	}

	@Override
	protected Object getFeatureValue(final String newText) {
		return newText;
	}
}