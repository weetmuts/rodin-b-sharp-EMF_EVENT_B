/*
 *University of Southampton
 */

package ac.soton.eventb.roseEditor.propertySections.enumerations;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.viewers.IFilter;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.machine.Event;
import org.eventb.emf.core.machine.MachinePackage;

import ac.soton.eventb.roseEditor.propertySections.abstracts.AbstractEnumerationPropertySection;

/**
 * A section for the EventKind property
 *
 * @author Colin Snook
 */

public class EventConvergencePropertySection extends AbstractEnumerationPropertySection  implements IFilter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try{
			EventBObject element = (EventBObject)selected;
			if (element instanceof Event) return true;
			return false;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	protected EAttribute getFeature() {
		return MachinePackage.eINSTANCE.getEvent_Convergence();
	}

}