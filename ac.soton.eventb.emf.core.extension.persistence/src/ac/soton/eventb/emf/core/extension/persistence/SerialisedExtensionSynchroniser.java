package ac.soton.eventb.emf.core.extension.persistence;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter.ReadableInputStream;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eventb.emf.core.AbstractExtension;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.persistence.synchroniser.AbstractSynchroniser;
import org.rodinp.core.IAttributeType;
import org.rodinp.core.IInternalElementType;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.RodinCore;
import org.rodinp.core.RodinDBException;

public abstract class SerialisedExtensionSynchroniser extends AbstractSynchroniser {
	
	private static final Set<IAttributeType> handledAttributes = new HashSet<IAttributeType>();

	static {
		handledAttributes.add(ISerialised.SERIALISED_ATTRIBUTE);
	}

	@Override
	protected EStructuralFeature getFeature() {
		return CorePackage.eINSTANCE.getEventBElement_Extensions();
	}

	@Override
	protected IInternalElementType<?> getRodinType() {
		return ISerialisedExtension.ELEMENT_TYPE;
	}

	@Override
	protected Set<IAttributeType> getHandledAttributeTypes() {
		return handledAttributes;
	}

	@Override
	public <T extends EventBElement> EventBElement load(
			IRodinElement rodinElement, EventBElement emfParent,
			IProgressMonitor monitor) throws RodinDBException {

		assert rodinElement instanceof ISerialisedExtension;
		ISerialisedExtension serialisedExtension = (ISerialisedExtension) rodinElement;
		
		// create EMF node
		AbstractExtension eventBElement = (AbstractExtension) super.load(rodinElement, emfParent, monitor);
		
		if (serialisedExtension.hasSerialised() && !serialisedExtension.getSerialised().isEmpty()) {
			String loadString = serialisedExtension.getSerialised();
			
			// use a resource to deserialise an attribute string
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			try {
				
				// dummy resource that loads from input stream to desired EMF object
				Resource resource = resourceSet.createResource(URI.createURI("http:///My.inp"));
				ReadableInputStream in = new ReadableInputStream(new StringReader(loadString));
				resource.load(in, null);
				if (!resource.getContents().isEmpty()) {
					AbstractExtension ext = (AbstractExtension) resource.getContents().get(0);
					EClass eClass = eventBElement.eClass();
					EList<EStructuralFeature> eFeatures = eClass.getEAllStructuralFeatures();
					for (EStructuralFeature feature : eFeatures)
						eventBElement.eSet(feature, ext.eGet(feature));
					return eventBElement;
				}
			}
			catch (IOException e) {
				RodinCore.getPlugin().getLog().log(
						new Status(IStatus.ERROR, ExtensionPersistencePlugin.PLUGIN_ID, "Error when trying to deserialise an extension.", e));
				return null;
			}
		}
		
		return null;
	}

	@Override
	public IRodinElement save(EventBElement emfElement,
			IRodinElement rodinParent, IProgressMonitor monitor)
			throws RodinDBException {
		
		// create Rodin element
		IRodinElement rodinElement = super.save(emfElement, rodinParent, monitor);
		if (rodinElement instanceof ISerialisedExtension && emfElement instanceof AbstractExtension) {
			String saveString;
			try {
				saveString = XMIHelperImpl.saveString(Collections.emptyMap(), Collections.singletonList(emfElement), "UTF-8", null);
				((ISerialisedExtension) rodinElement).setSerialised(saveString, monitor);
			} catch (Exception e) {
				RodinCore.getPlugin().getLog().log(
						new Status(IStatus.ERROR, ExtensionPersistencePlugin.PLUGIN_ID, "Error when trying to serialise an extension.", e));
				return null;
			}
		}
		return rodinElement;
	}

}
