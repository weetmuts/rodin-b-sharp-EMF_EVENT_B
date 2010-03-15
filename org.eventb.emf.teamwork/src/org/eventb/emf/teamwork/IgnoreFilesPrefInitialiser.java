/*******************************************************************************
 * Copyright (c) 2010 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eventb.emf.teamwork;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.IStartup;
import org.osgi.service.prefs.Preferences;

public class IgnoreFilesPrefInitialiser implements IStartup {
	private final String[] ignoredFiles = new String[] { "*.bum", "*.bcm", "*.buc", "*.bcc", "*.bpo", "*.bpr", "*.bps" };

	public void earlyStartup() {
		IPreferencesService service = Platform.getPreferencesService();
		IEclipsePreferences root = service.getRootNode();
		Preferences node = root.node(InstanceScope.SCOPE).node("org.eclipse.team.core");
		String ignore = "";
		for (String ext : ignoredFiles) {
			ignore += ext + "\ntrue\n";
		}
		node.put("ignore_files", ignore);
	}

}