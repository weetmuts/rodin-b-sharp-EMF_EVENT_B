
package ac.soton.eventb.roseEditor.propertySections.enumerations;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.viewers.IFilter;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.EventBPredicate;

import ac.soton.eventb.roseEditor.propertySections.abstracts.AbstractEnumerationPropertySection;


/**
 * A section for the derived predicate, theorem boolean value
 *
 * @author cfs
 */
public class EventBDerivedPredicateTheoremPropertySection extends AbstractEnumerationPropertySection  implements IFilter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try{
			EventBObject element = (EventBObject)selected;
			if (element instanceof EventBPredicate) return true;
			return false;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	protected EAttribute getFeature() {
		return CorePackage.eINSTANCE.getEventBDerived_Theorem();
	}

}