package org.eventb.emf.persistence.synchroniser;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eventb.emf.core.CoreFactory;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.Extension;
import org.rodinp.core.IAttributeType;
import org.rodinp.core.IInternalElement;
import org.rodinp.core.IInternalElementType;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.RodinCore;
import org.rodinp.core.RodinDBException;

public class ExtensionSynchroniser extends AbstractSynchroniser {

	private static final Set<IAttributeType> handledAttributes = new HashSet<IAttributeType>();
	private static IInternalElementType<?> rodinElementType;

	@Override
	protected Set<IAttributeType> getHandledAttributeTypes() {
		return handledAttributes;
	}

	@Override
	protected EventBElement createEventBElement() {
		return CoreFactory.eINSTANCE.createExtension();
	}

	@Override
	protected EStructuralFeature getFeature() {
		return CorePackage.eINSTANCE.getEventBElement_Extensions();
	}

	@Override
	protected IInternalElementType<?> getRodinType() {
		return rodinElementType;
	}

	@Override
	public EventBElement load(final IInternalElement rodinElement, final EventBElement emfParent, final IProgressMonitor monitor) throws RodinDBException {
		// create EMF node
		Extension eventBElement = (Extension) super.load(rodinElement, emfParent, monitor);
		return eventBElement;
	}

	@Override
	public IInternalElement save(final EventBElement emfElement, final IRodinElement rodinParent, final IProgressMonitor monitor) throws RodinDBException {
		String id = ((Extension) emfElement).getExtensionId();
		if (id == null || "".equals(id))
			return null; //some extensions are not intended to be saved
		rodinElementType = RodinCore.getInternalElementType(id);
		// create Rodin element
		IInternalElement rodinElement = super.save(emfElement, rodinParent, monitor);
		return rodinElement;
	}
}
