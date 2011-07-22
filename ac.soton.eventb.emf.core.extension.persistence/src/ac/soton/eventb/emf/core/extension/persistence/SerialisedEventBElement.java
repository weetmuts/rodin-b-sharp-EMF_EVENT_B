/**
 * Copyright (c) 2011 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package ac.soton.eventb.emf.core.extension.persistence;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eventb.core.basis.EventBElement;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.RodinDBException;

/**
 * Serialised EventB element type.
 * 
 * @author vitaly
 *
 */
public abstract class SerialisedEventBElement extends EventBElement implements ISerialised {

	/**
	 * @param name
	 * @param parent
	 */
	public SerialisedEventBElement(String name, IRodinElement parent) {
		super(name, parent);
	}

	@Override
	public boolean hasSerialised() throws RodinDBException {
		return hasAttribute(SERIALISED_ATTRIBUTE);
	}

	@Override
	public String getSerialised() throws RodinDBException {
		return getAttributeValue(SERIALISED_ATTRIBUTE);
	}

	@Override
	public void setSerialised(String string, IProgressMonitor monitor)
			throws RodinDBException {
		setAttributeValue(SERIALISED_ATTRIBUTE, string, monitor);
	}

}