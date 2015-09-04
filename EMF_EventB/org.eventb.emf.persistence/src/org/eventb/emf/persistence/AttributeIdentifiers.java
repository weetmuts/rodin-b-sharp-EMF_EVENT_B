/**
 * Copyright (c) 2012 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eventb.emf.persistence;

import org.eclipse.osgi.util.NLS;

public class AttributeIdentifiers extends NLS {
	private static final String BUNDLE_NAME = "org.eventb.emf.persistence.AttributeIdentifiers"; //$NON-NLS-1$
	public static String GENERATOR_ID_KEY;
	public static String PRIORITY_KEY;
	public static String UNKNOWN_ATTRIBUTES_KEY;
	public static String EMF_ID_KEY;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, AttributeIdentifiers.class);
	}

	private AttributeIdentifiers() {
	}

}
