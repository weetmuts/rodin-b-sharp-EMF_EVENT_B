package org.eventb.emf.persistence.synchroniser;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eventb.core.IContextRoot;
import org.eventb.core.IExtendsContext;
import org.eventb.core.IMachineRoot;
import org.eventb.core.IRefinesEvent;
import org.eventb.core.IRefinesMachine;
import org.eventb.core.ISeesContext;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.Project;
import org.eventb.emf.persistence.ISynchroniser;
import org.rodinp.core.IParent;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.IRodinFile;
import org.rodinp.core.RodinDBException;

public class SyncManager {
	private static final Map<String, ISynchroniser> idMapping = new HashMap<String, ISynchroniser>();
	private static final Map<EClass, ISynchroniser> emfMapping = new HashMap<EClass, ISynchroniser>();
	private static final ISynchroniser genericSynchroniser;

	private static final String synchronisersID = "org.eventb.emf.persistence.synchroniser";
	static {
		// populate synchroniser mappings from registered extensions
		for (final IExtension extension : Platform.getExtensionRegistry().getExtensionPoint(synchronisersID).getExtensions()) {
			for (final IConfigurationElement packageElement : extension.getConfigurationElements()) {
				if ("emfPackage".equals(packageElement.getName())) {
					String nsURI = packageElement.getAttribute("nsURI");
					EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsURI);
					for (final IConfigurationElement element : packageElement.getChildren("synchroniser")) {
						if ("synchroniser".equals(element.getName())) {
							try {
								final ISynchroniser synchroniser = (ISynchroniser) element.createExecutableExtension("synchroniser_class");
								String rodinId = element.getAttribute("rodin_id");
								if (rodinId != null && !"".equals(rodinId)) {
									idMapping.put(rodinId, synchroniser);
								}
								EClassifier eClassifier = ePackage.getEClassifier(element.getAttribute("emf_class"));
								if (eClassifier != null) {
									emfMapping.put((EClass) eClassifier, synchroniser);
								}
							} catch (final CoreException e) {
								// to log an error we would need to give this
								// plugin an
								// Activator Class
								// IStatus status= new Status(
								// IStatus.ERROR,
								// PLUGIN_ID,
								// IStatus.ERROR,
								// message,
								// exception);
								// plugin.getLog().log(status);
							}
						}
					}
				}
			}
		}

		genericSynchroniser = new ExtensionSynchroniser();// DynamicSynchroniser(); //
	}

	public EventBElement loadRodinElement(final IRodinElement rodinElement, final EventBElement emfParent, Map<IRodinElement, EventBObject> map, final IProgressMonitor monitor)
			throws RodinDBException, Exception {
		final EventBElement emfElement;
		final IRodinElement rElement = rodinElement instanceof IRodinFile ? ((IRodinFile) rodinElement).getRoot() : rodinElement;
		// get mapping by id
		final String id = rElement.getElementType().getId();

		// for a project look at machine or context only
		if (emfParent instanceof Project && !id.equals(IMachineRoot.ELEMENT_TYPE.getId()) && !id.equals(IContextRoot.ELEMENT_TYPE.getId()))
			return null;

		if (id.equals(IRefinesMachine.ELEMENT_TYPE.getId()) || id.equals(ISeesContext.ELEMENT_TYPE.getId()) || id.equals(IRefinesEvent.ELEMENT_TYPE.getId())
				|| id.equals(IExtendsContext.ELEMENT_TYPE.getId()))
			return null;

		final ISynchroniser synchroniser = idMapping.get(id);

		if (synchroniser != null) {
			// call synchroniser
			emfElement = synchroniser.load(rElement, emfParent, monitor);
		} else {
			// use default generic synchroniser
			emfElement = genericSynchroniser.load(rElement, emfParent, monitor);
		}
		map.put(rElement, emfElement);
		for (final IRodinElement child : ((IParent) rElement).getChildren()) {
			loadRodinElement(child, emfElement, map, monitor);
		}

		return emfElement;
	}

	public void saveModelElement(final EventBElement emfElement, final IRodinElement rodinParent, Map<IRodinElement, EventBObject> map, final IProgressMonitor monitor)
			throws CoreException {
		final IRodinElement rodinElement;

		// get mapping and call synchroniser
		final ISynchroniser synchroniser = emfMapping.get(emfElement.eClass());

		if (synchroniser != null) {
			rodinElement = synchroniser.save(emfElement, rodinParent, monitor);
		} else {
			// use default generic synchroniser
			rodinElement = genericSynchroniser.save(emfElement, rodinParent, monitor);
		}
		if (rodinElement == null)
			return;
		map.put(rodinElement, emfElement);
		for (final EObject child : emfElement.eContents()) {
			if (child instanceof EventBElement)
				saveModelElement((EventBElement) child, rodinElement, map, monitor);
		}
	}
}
