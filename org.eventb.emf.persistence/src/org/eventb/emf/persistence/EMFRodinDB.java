package org.eventb.emf.persistence;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.persistence.factory.ChangeListenerRegistry;
import org.eventb.emf.persistence.factory.IChangeListener;
import org.eventb.emf.persistence.factory.RodinResource;
import org.rodinp.core.IInternalElement;
import org.rodinp.core.IRodinElement;

/**
 * This class maintains an AdapterFactoryEditingDomain
 * (TransactionalEditingDomain) with a ResourceSet of Rodin components loaded
 * into EMF as Resources. It is intended for applications that work with the
 * Event-B EMF framework and require access to the components but do not have an
 * editing domain set up. If the underlying Rodin file changes the resource will
 * be re-loaded. Various load methods are provided to obtain either the
 * resource, an EMF root element or a specific element within the contents.
 * Client may use the editing domain for workspace synchronisation.
 * 
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

	private final AdapterFactoryEditingDomain editingDomain = (AdapterFactoryEditingDomain) TransactionalEditingDomain.Factory.INSTANCE
			.createEditingDomain(new ListeningRodinResourceSet());

	private final class resourceListener implements IChangeListener {
		@Override
		public void persistentResourceChanged(RodinResource resource) {
			if (resource.isLoaded()) {
				resource.eSetDeliver(false);
				resource.unload();
				try {
					resource.load(Collections.EMPTY_MAP);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			resource.eSetDeliver(true);
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
	 * returns the editing domain as an AdapterFactoryEditingDomain. Can be
	 * downcast to TransactionalEditingDomain (e.g. for use in a
	 * WorkspaceSynchronizer)
	 * 
	 * @return
	 */
	public AdapterFactoryEditingDomain getEditingDomain() {
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
		Resource resource = editingDomain.getResourceSet().getResource(fileURI, false); //n.b. do not load until notifications disabled
		if (resource == null) {
			resource = editingDomain.getResourceSet().createResource(fileURI);
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

}
