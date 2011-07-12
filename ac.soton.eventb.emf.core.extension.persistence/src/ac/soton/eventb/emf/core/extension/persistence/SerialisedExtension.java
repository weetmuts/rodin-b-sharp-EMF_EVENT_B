package ac.soton.eventb.emf.core.extension.persistence;

import org.rodinp.core.IInternalElement;
import org.rodinp.core.IInternalElementType;
import org.rodinp.core.IRodinElement;

public class SerialisedExtension extends SerialisedEventBElement implements ISerialisedExtension {

	public SerialisedExtension(String name, IRodinElement parent) {
		super(name, parent);
	}

	@Override
	public IInternalElementType<? extends IInternalElement> getElementType() {
		return ELEMENT_TYPE;
	}

}
