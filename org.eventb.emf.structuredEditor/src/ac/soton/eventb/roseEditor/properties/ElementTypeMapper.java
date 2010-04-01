/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;


/**
 * Maps a selected object to the  type (class) of the element it represents
 *
 * @author Colin Snook
 */

public class ElementTypeMapper implements ITypeMapper {

	/**
	 * @inheritDoc
	 */
	public Class<?> mapType(final Object object) {
		if (object instanceof EObject) {
			return ((EObject)object).getClass();
		}
		return null;
	}

}
