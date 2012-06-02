package ac.soton.eventb.emf.core.extension.navigator.refiner;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eventb.core.basis.EventBElement;
import org.rodinp.core.IInternalElement;
import org.rodinp.core.IInternalElementType;
import org.rodinp.core.IRefinementParticipant;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.RodinDBException;

/**
 * This refinement participant sets the generated attribute of a target element
 * whenever the corresponding source element has the generated attribute set to true.
 * This is because the normal machine refinement does not preserve the generated attribute.
 * 
 * @author cfs
 *
 */
public class GeneratedCopier implements IRefinementParticipant {


	@Override
	public void process(IInternalElement targetRoot,
			IInternalElement sourceRoot, IProgressMonitor monitor)
			throws RodinDBException {
		
		IRodinElement[] sourceElements = sourceRoot.getChildren();

		for (IRodinElement sourceElement : sourceElements){
			if (sourceElement instanceof EventBElement){
				EventBElement sourceEventBElement = (EventBElement)sourceElement;
				if (sourceEventBElement.isGenerated()){
					EventBElement targetEventBElement = findCorrespondingTarget(targetRoot, sourceEventBElement);
					if (targetEventBElement != null){
						targetEventBElement.setGenerated(true, monitor);
					}
					
				}
			}
		}
	}

	private EventBElement findCorrespondingTarget(IInternalElement targetRoot, EventBElement sourceEventBElement) throws RodinDBException {
		String sourceLabel = sourceEventBElement.hasLabel() ? sourceEventBElement.getLabel() : "";
		String sourceIdentifier = sourceEventBElement.hasIdentifierString() ? sourceEventBElement.getIdentifierString() : "";
		IInternalElementType<? extends IInternalElement> sourceType = sourceEventBElement.getElementType();
		IRodinElement[] targetElements = targetRoot.getChildren();
		for (IRodinElement targetElement : targetElements){
			if (targetElement instanceof EventBElement){
				EventBElement targetEventBElement = (EventBElement)targetElement;
				if (targetEventBElement.getElementType().equals(sourceType) && (
						(targetEventBElement.hasLabel() && targetEventBElement.getLabel().equals(sourceLabel)) ||
						(targetEventBElement.hasIdentifierString() && targetEventBElement.getIdentifierString().equals(sourceIdentifier))
						))
				{
					return targetEventBElement;
				}	
			}
		}
		return null;
	}
			
}
