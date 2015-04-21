/**
 * Copyright (c) 2012 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package ac.soton.eventb.emf.core.extension.navigator.refiner;

import org.eclipse.osgi.util.NLS;

public class Identifiers extends NLS {
	private static final String BUNDLE_NAME = "ac.soton.eventb.emf.core.extension.navigator.refiner.identifiers"; //$NON-NLS-1$
	public static String EXTPT_REFINER_ID;
	public static String EXTPT_REFINER_EPACKAGE;
	public static String EXTPT_REFINER_REFINERCLASS;

	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Identifiers.class);
	}

	private Identifiers() {
	}
	

}

