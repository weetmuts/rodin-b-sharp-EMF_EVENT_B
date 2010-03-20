/*******************************************************************************
 * Copyright (c) 2010 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eventb.emf.teamwork;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.progress.UIJob;
import org.eventb.core.IContextRoot;
import org.eventb.core.IMachineRoot;
import org.rodinp.core.IRodinProject;
import org.rodinp.core.RodinCore;

/**
 * Teamwork resource updater class.
 * 
 * Contains static methods to create, synchronise or remove a teamwork (xmb) resource.
 */
public class TeamworkUpdater {
	private final static ResourceSet rSet = new ResourceSetImpl();
	public static final String TEAMWORK_EXT = "xmb";
	public static final String TEAMWORK_FOLDER = "team";

	/**
	 * Get full path of xmb resource that corresponds to an eventB resource.
	 * 
	 * @param resource eventB file resource
	 * @return full path of the xmb file resource
	 */
	public static String getTeamworkResourcePath(IResource resource) {
		return resource.getFullPath().removeLastSegments(1)
			.append(TEAMWORK_FOLDER)
			.append(resource.getName())
			.addFileExtension(TEAMWORK_EXT).toOSString();
	}

	/**
	 * Get full path of an eventB resource that corresponds to a teamwork resource (xmb).
	 * 
	 * @param resource xmb file resource
	 * @return full path of the eventB file resource
	 */
	public static String getTeamworkReverseResourcePath(IResource resource) {
		return resource.getFullPath().removeLastSegments(2)
			.append(resource.getName())
			.removeFileExtension().toOSString();
	}

	/**
	 * Create a temawork xmb resource for the provided eventB resource or eventB project folder.
	 * 
	 * @param resource eventB file resource or eventB project folder containing such resources
	 */
	public static void createTeamworkResource(final IResource resource) {
		new TeamworkUIJob(Display.getDefault(), "Create teamwork resource", new CreateOperation(resource)).schedule();
	}

	/**
	 * Synchronise two resources.
	 * 
	 * @param resource eventB resource or xmb resource
	 */
	public static void synchroniseTeamworkResource(final IResource resource) {
		new TeamworkUIJob(Display.getDefault(), "Synchronise teamwork resources", new SynchroniseOperation(resource)).schedule();
	}

	/**
	 * Remove a teamwork xmb resource for the provided eventB resource.
	 * 
	 * @param resource eventB file resource
	 */
	public static void removeTeamworkResource(final IResource resource) {
		new TeamworkUIJob(Display.getDefault(), "Remove teamwork resource", new RemoveOperation(resource)).schedule();
	}
	
	public static void renameTeamworkResource(final IResource oldFile, final IResource  newFile) {
		new TeamworkUIJob(Display.getDefault(), "Rename teamwork resource", new RenameOperation(oldFile, newFile)).schedule();
	}
	
	/**
	 * Custom UI job to perform a workspace operation for the teamwork synchronisation inside a UI thread.
	 */
	static class TeamworkUIJob extends UIJob {
		
		private IRunnableWithProgress operation;

		public TeamworkUIJob(Display jobDisplay, String name, WorkspaceModifyOperation operation) {
			super(jobDisplay, name);
			this.operation = operation;
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(true, false, operation);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return Status.OK_STATUS;
		}
		
	}
	
	/**
	 * Teamwork resource creation operation.
	 * 
	 * Depending on parameter's actual type (project or file) creates either a set of
	 * xmb elements for all the project's machines/contexts or a single xmb for the 
	 * provided eventB resource.
	 */
	static class CreateOperation extends WorkspaceModifyOperation {
		
		private IResource resource;

		/**
		 * Constructor.
		 * 
		 * @param resource eventB resource or a folder containing a set of eventB resources
		 */
		public CreateOperation(IResource resource) {
			super();
			this.resource = resource;
		}
		
		@Override
		protected void execute(IProgressMonitor monitor)
				throws CoreException, InvocationTargetException,
				InterruptedException {
			try {
				if (resource instanceof IProject) {
					IRodinProject rodinProject = RodinCore.getRodinDB().getRodinProject(resource.getName());

					// get all machines and contexts not registered yet
					List<IResource> toAdd = new ArrayList<IResource>();
					for (IMachineRoot machine : rodinProject.getRootElementsOfType(IMachineRoot.ELEMENT_TYPE))
						toAdd.add(machine.getResource());
					for (IContextRoot context : rodinProject.getRootElementsOfType(IContextRoot.ELEMENT_TYPE))
						toAdd.add(context.getResource());

					// create an xmb for every found component
					for (IResource file : toAdd)
						createXMBResource(file);
				} else if (!resource.getFileExtension().equals(TEAMWORK_EXT))
					createXMBResource(resource);
				else
					createResource(resource);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * Create a resource of the same content as provided resource.
		 * 
		 * @param res1 original resource
		 * @param res2 resource to be created
		 * @throws IOException
		 */
		private void createResourceCopy(Resource res1, Resource res2) throws IOException {
			res1.load(null);
			EObject element = res1.getContents().get(0);
			res2.save(Collections.EMPTY_MAP);
			res2.getContents().add(element);
			res2.save(Collections.EMPTY_MAP);
		}
		
		/**
		 * Create an XMB resource for the eventB resource provided.
		 * 
		 * @param file eventB resource
		 * @throws IOException
		 */
		private void createXMBResource(IResource file) throws IOException {
			Resource res = rSet.createResource(URI.createPlatformResourceURI(file.getFullPath().toOSString(), true));
			Resource xmbRes = rSet.createResource(URI.createPlatformResourceURI(getTeamworkResourcePath(file), true));
			createResourceCopy(res, xmbRes);
		}

		/**
		 * Create an eventB resource for the XMB resource provided.
		 * 
		 * @param file XMB teamwork resource
		 * @throws IOException
		 */
		private void createResource(IResource file) throws IOException {
			Resource xmbRes = rSet.createResource(URI.createPlatformResourceURI(file.getFullPath().toOSString(), true));
			Resource res = rSet.createResource(URI.createPlatformResourceURI(getTeamworkReverseResourcePath(file), true));
			createResourceCopy(xmbRes, res);
		}
		
	}
	
	/**
	 * Teamwork resource synchronisation operation.
	 * 
	 * Synchronises the contents of a shared resource with its pair:
	 * if eventB resource provided, updates the contents of its corresponding xmb file;
	 * if xmb resource provided, updates its eventB file.
	 */
	static class SynchroniseOperation extends WorkspaceModifyOperation {
		
		private IResource resource;

		public SynchroniseOperation(IResource resource) {
			super();
			this.resource = resource;
		}
		@Override
		protected void execute(IProgressMonitor monitor) throws CoreException,
				InvocationTargetException, InterruptedException {
			String path2 = resource.getFileExtension().equals(TEAMWORK_EXT) ? 
					getTeamworkReverseResourcePath(resource) : 
						getTeamworkResourcePath(resource);
			Resource res1 = rSet.createResource(URI.createPlatformResourceURI(resource.getFullPath().toOSString(), true));
			Resource res2 = rSet.createResource(URI.createPlatformResourceURI(path2, true));
			
			IResource resource2 = WorkspaceSynchronizer.getFile(res2);
			if (resource2 == null || !resource2.exists())
				throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, "Teamwork synchronisation error: "+resource2.getFullPath()+" does not exist."));
			
			// if files already synchronised don't continue
			if (resource.getModificationStamp() == resource2.getModificationStamp())
				return;
			
			try {
				res1.load(null);
				res2.load(null);
				res2.getContents().clear();
				res2.getContents().add(res1.getContents().get(0));
				res2.save(Collections.EMPTY_MAP);
				// synchronise modification stamp (used to prevent synchronisation loop)
				resource2.revertModificationStamp(resource.getModificationStamp());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Teamwork resource remove operation.
	 * 
	 * Removes an xmb resource that corresponds to a provided eventB resource.
	 */
	static class RemoveOperation extends WorkspaceModifyOperation {
		
		private IResource resource;

		public RemoveOperation(IResource resource) {
			super();
			this.resource = resource;
		}

		@Override
		protected void execute(IProgressMonitor monitor) throws CoreException,
				InvocationTargetException, InterruptedException {
			Resource xmbRes = rSet.createResource(URI.createPlatformResourceURI(getTeamworkResourcePath(resource), true));
			try {
				xmbRes.delete(Collections.EMPTY_MAP);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Teamwork resource rename operation.
	 * 
	 * Rename an xmb resource that corresponds to a renamed eventB resource.
	 */
	static class RenameOperation extends WorkspaceModifyOperation {
		
		private IResource oldFile;
		private IResource newFile;

		public RenameOperation(IResource oldFile, IResource newFile) {
			super();
			this.oldFile = oldFile;
			this.newFile = newFile;
		}

		@Override
		protected void execute(IProgressMonitor monitor) throws CoreException,
				InvocationTargetException, InterruptedException {
			Resource res = rSet.createResource(URI.createPlatformResourceURI(newFile.getFullPath().toOSString(), true));
			Resource xmbRes = rSet.createResource(URI.createPlatformResourceURI(getTeamworkResourcePath(oldFile), true));
			try {
				res.load(null);
				xmbRes.load(null);
				xmbRes.getContents().clear();
				xmbRes.getContents().add(res.getContents().get(0));
				xmbRes.save(Collections.EMPTY_MAP);
				WorkspaceSynchronizer.getFile(xmbRes).move(new Path(getTeamworkResourcePath(newFile)), false, new NullProgressMonitor());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
