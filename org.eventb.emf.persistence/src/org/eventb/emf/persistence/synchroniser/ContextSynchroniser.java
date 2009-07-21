package org.eventb.emf.persistence.synchroniser;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eventb.core.EventBAttributes;
import org.eventb.core.IContextRoot;
import org.eventb.core.IExtendsContext;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.context.Context;
import org.eventb.emf.core.context.ContextFactory;
import org.rodinp.core.IAttributeType;
import org.rodinp.core.IInternalElement;
import org.rodinp.core.IInternalElementType;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.RodinDBException;

public class ContextSynchroniser extends AbstractSynchroniser {

	private static final Set<IAttributeType> handledAttributes = new HashSet<IAttributeType>();

	static {
		handledAttributes.add(EventBAttributes.TARGET_ATTRIBUTE);
	}

	@Override
	protected Set<IAttributeType> getHandledAttributeTypes() {
		return handledAttributes;
	}

	@Override
	protected Context createEventBElement() {
		return ContextFactory.eINSTANCE.createContext();
	}

	@Override
	protected EStructuralFeature getFeature() {
		return CorePackage.eINSTANCE.getProject_Components();
	}

	@Override
	protected IInternalElementType<?> getRodinType() {
		return IContextRoot.ELEMENT_TYPE;
	}

	@Override
	public EventBElement load(final IInternalElement rodinElement, final EventBElement emfParent, final IProgressMonitor monitor) throws RodinDBException {

		// create EMF node for context
		final Context eventBElement = (Context) super.load(rodinElement, emfParent, monitor);
		if (rodinElement instanceof IContextRoot) {
			EList<String> extendsNames = eventBElement.getExtendsNames();
			for (IExtendsContext extendsContext : ((IContextRoot) rodinElement).getExtendsClauses()) {
				extendsNames.add(extendsContext.getAbstractContextName());
			}
		}
		return eventBElement;
	}

	@Override
	public IInternalElement save(final EventBElement emfElement, final IRodinElement rodinParent, final IProgressMonitor monitor) throws RodinDBException {

		// create Rodin element
		IInternalElement rodinElement = super.save(emfElement, rodinParent, monitor);
		if (rodinElement instanceof IContextRoot && emfElement instanceof Context) {
			EList<String> extendsNames = ((Context) emfElement).getExtendsNames();
			for (String extendsName : extendsNames) {
				IExtendsContext extendsContext = ((IContextRoot) rodinElement).getExtendsClause(getNewName());
				extendsContext.create(null, monitor);
				extendsContext.setAbstractContextName(extendsName, monitor);
			}
		}
		return rodinElement;
	}

}
