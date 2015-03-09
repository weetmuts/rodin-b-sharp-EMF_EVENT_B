package org.eventb.emf.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eventb.core.IContextRoot;
import org.eventb.core.IMachineRoot;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBNamedCommentedComponentElement;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.Project;
import org.eventb.emf.persistence.factory.ChangeListenerRegistry;
import org.eventb.emf.persistence.factory.IChangeListener;
import org.eventb.emf.persistence.factory.ModelUpdateTool;
import org.eventb.emf.persistence.factory.RodinResource;
import org.rodinp.core.IInternalElement;
import org.rodinp.core.IInternalElementType;
import org.rodinp.core.IRodinDB;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.IRodinProject;
import org.rodinp.core.RodinCore;

/**
 * This class maintains a ResourceSet of Rodin components loaded into EMF as
 * Resources. It is intended for applications that work with the Event-B EMF
 * framework and require access to the components but do not have an editing
 * domain set up. If the underlying Rodin file changes the resource will be
 * re-loaded or, if it has been deleted, removed from the resource set. Various
 * load methods are provided to obtain either the resource, an EMF root element
 * or a specific element within the contents. A save method is also provided to
 * save an EventBElement into resource specified by URI.
 *
 * @author cfs
 *
 */
public final class EMFRodinDB {

	/**
	 * the singleton instance of EMFRodinDB
	 */
	public final static EMFRodinDB INSTANCE = new EMFRodinDB();

	private EMFRodinDB() {
	} //do not allow instantiation

	private final ResourceSet resourceSet = new ListeningRodinResourceSet();
	private final TransactionalEditingDomain editingDomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(resourceSet);

	private final class resourceListener implements IChangeListener {
		@Override
		public void persistentResourceChanged(RodinResource resource) {
			if (resource.isLoaded()) {
				resource.eSetDeliver(false);
				resource.unload();
				try {
					if (resource.getUnderlyingResource().exists()) {
						resource.load(Collections.EMPTY_MAP);
						ModelUpdateTool.updateDiagnostics(resource);
						resource.eSetDeliver(true);
					} else {
						//underlying Rodin file has been deleted so remove this resource
						resourceSet.getResources().remove(resource);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private final class ListeningRodinResourceSet extends ResourceSetImpl {
		@Override
		public Resource createResource(URI uri) {
			Resource resource = super.createResource(uri);
			if (resource instanceof RodinResource) {
				ChangeListenerRegistry.addChangeListener((RodinResource) resource, new resourceListener());
			}
			return resource;
		}
	}

	/**
	 * get the resource set.
	 *
	 * @return
	 */
	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	/**
	 * get the editing domain
	 *
	 * @return
	 */
	public TransactionalEditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * loads an Event-B component (root) into EMF
	 *
	 * @param root
	 * @return
	 */
	public EventBElement loadEventBComponent(IInternalElement root) {
		if (root == null || !root.exists())
			return null;
		URI fileURI = URI.createPlatformResourceURI(root.getResource().getFullPath().toString(), true);
		return loadEventBComponent(fileURI);
	}

	/**
	 * loads an Event-B component (URI) into EMF
	 *
	 * @param root
	 * @return
	 */
	public EventBElement loadEventBComponent(URI fileURI) {
		Resource resource = loadResource(fileURI);
		if (resource == null) {
			return null;
		}
		if (resource.isLoaded() && !resource.getContents().isEmpty() && resource.getContents().get(0) instanceof EventBElement) {
			return (EventBElement) resource.getContents().get(0);
		} else {
			return null;
		}
	}

	/**
	 * loads an Event-B component (root) as an EMF Resource
	 *
	 * @param root
	 * @return
	 */
	public Resource loadResource(IInternalElement root) {
		if (root == null || !root.exists())
			return null;
		URI fileURI = URI.createPlatformResourceURI(root.getResource().getFullPath().toString(), true);
		return loadResource(fileURI);
	}

	/**
	 * loads an Event-B component (URI) as an EMF Resource
	 *
	 * @param root
	 * @return
	 */
	public Resource loadResource(URI fileURI) {
		Resource resource = resourceSet.getResource(fileURI, false); //n.b. do not load until notifications disabled
		if (resource == null) {
			resource = resourceSet.createResource(fileURI);
		}
		if (!resource.isLoaded()) {
			resource.eSetDeliver(false); // turn off notifications while loading
			try {
				resource.load(Collections.emptyMap());
			} catch (IOException e) {
				return null;
			}
			resource.eSetDeliver(true);
		}
		if (resource.isLoaded()) {
			return resource;
		} else {
			return null;
		}
	}

	/**
	 *
	 * loads an Event-B internal element as the corresponding EMF child element
	 *
	 * @param rodinElement
	 * @return
	 */
	public EventBObject loadElement(IInternalElement rodinElement) {
		Resource emfResource = loadResource(rodinElement);
		Map<IRodinElement, EventBObject> map = ((RodinResource) emfResource).getMap();
		EventBObject eventBObject = map.get(rodinElement);
		return eventBObject;
	}

	/**
	 * loads the EMF resource using the EMF uri and uses the uri fragment to
	 * obtain an EMF child element to return (The uri could be from an
	 * unresolved proxy for exaample)
	 *
	 * @param uri
	 * @return
	 */
	public EventBObject loadElement(URI uri) {
		return (EventBObject) loadResource(uri).getEObject(uri.fragment());
	}

	/**
	 * saves an Event-B component (URI) as an EMF Resource
	 *
	 * @param uri
	 * @return the saved Resource
	 */
	public Resource saveResource(URI fileURI, EventBElement element) {
		Resource resource = resourceSet.getResource(fileURI, false); //n.b. do not load until notifications disabled
		if (resource == null) {
			resource = resourceSet.createResource(fileURI);
		}
		boolean deliver = resource.eDeliver();
		resource.eSetDeliver(false); // turn off notifications
		resource.getContents().clear();
		resource.getContents().add(element);
		try {
			resource.save(Collections.emptyMap());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			resource.eSetDeliver(deliver); // turn notifications back on
		}
		return resource;
	}

	/**
	 * this returns the project name by checking the uri of the given element
	 * The element must be loaded or an npe will occur
	 *
	 * @param element
	 * @return
	 */
	public String getProjectName(EventBElement element) {
		URI uri = element.getURI();
		return uri.segment(1);
	}

	/**
	 * This loads the complete project into EMF. This is an ALTERNATIVE to
	 * working with individual component resources.
	 *
	 * (remember, if you already have individual components loaded in
	 * EMFRodinDB, loading the project creates different objects for these same
	 * components and proxies will not resolve between the two. It is
	 * recommended to use getReference() to test whether two elements loaded in
	 * different resources are actually representing the same model element).
	 *
	 * @param project
	 *            name
	 * @return
	 */
	public Project loadEMFProject(String projectName) {
		final URI projectURI = URI.createPlatformResourceURI(projectName + "/" + projectName + ".prj", true);
		Resource projRes = loadResource(projectURI);
		return (Project) projRes.getContents().get(0);
	}

	public List<EventBNamedCommentedComponentElement> loadAllComponents(String projectName) {
		List<EventBNamedCommentedComponentElement> components = new ArrayList<EventBNamedCommentedComponentElement>();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		try {
			final IRodinDB rodinDB = RodinCore.valueOf(workspace.getRoot());

			IRodinProject rp = rodinDB.getRodinProject(projectName);
			IInternalElement[] roots = {};
			for (IInternalElementType<?> type : supportedTypes) {
				roots = rp.getRootElementsOfType(type);
				for (IInternalElement root : roots) {
					EventBElement comp = loadEventBComponent(root);
					if (comp instanceof EventBNamedCommentedComponentElement) {
						components.add((EventBNamedCommentedComponentElement) comp);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return components;
	}

	private final static IInternalElementType<?>[] supportedTypes = { IMachineRoot.ELEMENT_TYPE, IContextRoot.ELEMENT_TYPE };
}
