package org.eventb.emf.persistence;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eventb.emf.core.EventBElement;
import org.rodinp.core.IInternalElement;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.RodinDBException;

public interface ISynchroniser {
	public <T extends EventBElement> EventBElement load(IInternalElement rodinElement, EventBElement emfParent, final IProgressMonitor monitor) throws RodinDBException;

	public IInternalElement save(EventBElement emfElement, IRodinElement rodinParent, final IProgressMonitor monitor) throws RodinDBException;
}
