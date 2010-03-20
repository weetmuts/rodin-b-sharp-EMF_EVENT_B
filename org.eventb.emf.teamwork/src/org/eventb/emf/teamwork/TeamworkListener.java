/*******************************************************************************
 * Copyright (c) 2010 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eventb.emf.teamwork;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.team.core.RepositoryProvider;
import org.rodinp.core.RodinCore;

/**
 * Teamwork resource listener.
 */
public class TeamworkListener implements IResourceChangeListener {

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() != IResourceChangeEvent.POST_CHANGE)
            return;
		
		try {
			for (IResourceDelta parent : event.getDelta().getAffectedChildren(IResourceDelta.CHANGED))
				if (((IProject) parent.getResource()).hasNature(RodinCore.NATURE_ID) && RepositoryProvider.isShared((IProject) parent.getResource())) {
					// project is shared
					if ((parent.getFlags() & IResourceDelta.DESCRIPTION) != 0)
						TeamworkUpdater.createTeamworkResource((IProject) parent.getResource());
					// project files are modified
					else {
						// eventB resource
						for (IResourceDelta child : parent.getAffectedChildren())
							if (child.getResource() instanceof IFile && child.getResource().getFileExtension().matches("bum|buc"))
								switch (child.getKind()) {
								case IResourceDelta.ADDED:TeamworkUpdater.createTeamworkResource(child.getResource());break;
								case IResourceDelta.REMOVED://if ((child.getFlags() & IResourceDelta.MOVED_TO) == 0) 
									TeamworkUpdater.removeTeamworkResource(child.getResource()); break; //else
										//TeamworkUpdater.renameTeamworkResource(child.getResource(),((IProject) parent.getResource()).findMember(child.getMovedToPath()));break;
								case IResourceDelta.CHANGED:TeamworkUpdater.synchroniseTeamworkResource(child.getResource());break;
								}
						
						IResourceDelta team = parent.findMember(new Path("team"));
						if (team == null)
							return;
						
						// teamwork resource
						for (IResourceDelta child : team.getAffectedChildren())
							if (child.getResource() instanceof IFile && child.getResource().getFileExtension().matches(TeamworkUpdater.TEAMWORK_EXT))
								switch (child.getKind()) {
								case IResourceDelta.ADDED:break;//TeamworkUpdater.createTeamworkResource(child.getResource());break;
								case IResourceDelta.CHANGED:TeamworkUpdater.synchroniseTeamworkResource(child.getResource());break;
								case IResourceDelta.REMOVED:break;
								}
					}
				}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
