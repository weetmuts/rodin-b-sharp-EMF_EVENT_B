package org.eventb.emf.persistence.synchroniser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eventb.core.EventBAttributes;
import org.eventb.core.ICommentedElement;
import org.eventb.core.IConfigurationElement;
import org.eventb.core.IIdentifierElement;
import org.eventb.core.ILabeledElement;
import org.eventb.emf.core.Annotation;
import org.eventb.emf.core.Attribute;
import org.eventb.emf.core.AttributeType;
import org.eventb.emf.core.CoreFactory;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBCommented;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBNamed;
import org.eventb.emf.persistence.ISynchroniser;
import org.eventb.emf.persistence.PersistencePlugin;
import org.rodinp.core.IAttributeType;
import org.rodinp.core.IAttributeValue;
import org.rodinp.core.IInternalElement;
import org.rodinp.core.IInternalElementType;
import org.rodinp.core.IRodinDBStatusConstants;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.IRodinFile;
import org.rodinp.core.RodinCore;
import org.rodinp.core.RodinDBException;

public abstract class AbstractSynchroniser implements ISynchroniser {

	private static final String IDENTIFIER = "identifier";
	private static final String LABEL = "label";
	private static final String NAME = "name";
	private static final String CONFIGURATION = "configuration";
	private static final String COMMENT = "comment";

	protected EMap<String, String> rodinInternalDetails;

	private static final Set<IAttributeType> handledAttributes = new HashSet<IAttributeType>();

	static {
		handledAttributes.add(EventBAttributes.CONFIGURATION_ATTRIBUTE);
		handledAttributes.add(EventBAttributes.LABEL_ATTRIBUTE);
		handledAttributes.add(EventBAttributes.IDENTIFIER_ATTRIBUTE);
		handledAttributes.add(EventBAttributes.COMMENT_ATTRIBUTE);
		handledAttributes.add(EventBAttributes.GENERATED_ATTRIBUTE);
	}

	protected abstract EventBElement createEventBElement();

	protected abstract EStructuralFeature getFeature();

	protected abstract IInternalElementType<?> getRodinType();

	protected abstract Set<IAttributeType> getHandledAttributeTypes();

	@SuppressWarnings("unchecked")
	public <T extends EventBElement> EventBElement load(final IRodinElement rodinElement, final EventBElement emfParent, final IProgressMonitor monitor) throws RodinDBException {
		if (!(rodinElement instanceof IInternalElement))
			throw new RodinDBException(new Exception("Not an Internal Element"), IRodinDBStatusConstants.CORE_EXCEPTION);

		// create EMF node
		final EventBElement eventBElement = createEventBElement();

		loadAttributes((IInternalElement) rodinElement, eventBElement);

		if (emfParent != null) {
			// attach new node to parent
			EStructuralFeature feature = getFeature();

			if (feature instanceof EReference && ((EReference) feature).isContainment()) {
				Object container = emfParent.eGet(feature, false);

				if (container instanceof EList) {
					((EList<Object>) container).add(eventBElement);
				} else {
					emfParent.eSet(feature, eventBElement);
				}
			} else if (feature instanceof EAttribute) {
				emfParent.eSet(feature, eventBElement);
			}
		}

		return eventBElement;
	}

	private void loadAttributes(final IInternalElement rodinElement, final EventBElement eventBElement) throws RodinDBException {
		// create Annotation for rodin internal details
		Annotation rodinInternals = CoreFactory.eINSTANCE.createAnnotation();
		rodinInternals.setSource(PersistencePlugin.SOURCE_RODIN_INTERNAL_ANNOTATION);
		eventBElement.getAnnotations().add(rodinInternals);
		rodinInternalDetails = rodinInternals.getDetails();

		if (rodinElement.hasAttribute(EventBAttributes.GENERATED_ATTRIBUTE)) {
			eventBElement.setLocalGenerated(rodinElement.getAttributeValue(EventBAttributes.GENERATED_ATTRIBUTE));
		} else {
			eventBElement.unsetLocalGenerated();
		}

		if (rodinElement instanceof IConfigurationElement) {
			if (((IConfigurationElement) rodinElement).hasConfiguration()) {
				rodinInternalDetails.put(CONFIGURATION, ((IConfigurationElement) rodinElement).getConfiguration());
			}
		}

		// set attributes of new node

		String elementName = rodinElement.getElementName();
		rodinInternalDetails.put(NAME, elementName);
		if (rodinElement instanceof ILabeledElement && ((ILabeledElement) rodinElement).hasLabel()) {
			elementName = ((ILabeledElement) rodinElement).getLabel();
			rodinInternalDetails.put(LABEL, elementName);
		}
		if (rodinElement instanceof IIdentifierElement && ((IIdentifierElement) rodinElement).hasIdentifierString()) {
			elementName = ((IIdentifierElement) rodinElement).getIdentifierString();
			rodinInternalDetails.put(IDENTIFIER, elementName);
		}
		if (eventBElement instanceof EventBNamed) {
			((EventBNamed) eventBElement).setName(elementName);
		}

		if (rodinElement instanceof ICommentedElement && ((ICommentedElement) rodinElement).hasComment()) {
			String comment = ((ICommentedElement) rodinElement).getComment();
			rodinInternalDetails.put(COMMENT, comment);
			if (eventBElement instanceof EventBCommented) {
				((EventBCommented) eventBElement).setComment(comment);
			}
		}

		loadGenericAttributes(rodinElement, eventBElement);
	}

	/**
	 * Loads all attributes which have not been handled by this class or the
	 * concrete implementing synchroniser into an annotation with the source
	 * 
	 * @param rodinElement
	 * @param eventBElement
	 * @throws RodinDBException
	 */
	private void loadGenericAttributes(IInternalElement rodinElement, EventBElement eventBElement) throws RodinDBException {
		// check if we have any conflicting handlers for attributes
		Set<IAttributeType> otherwiseTypes = getHandledAttributeTypes();
		checkConflicts(otherwiseTypes);

		/*
		 * Filter all elements that are already handled by this class and a
		 * concrete synchroniser. We only store those which are unhandled.
		 */
		HashSet<IAttributeType> availableTypes = new HashSet<IAttributeType>(Arrays.asList(rodinElement.getAttributeTypes()));
		availableTypes.removeAll(handledAttributes);
		availableTypes.removeAll(otherwiseTypes);

		// add all remaining attributes
		for (IAttributeType type : availableTypes) {
			IAttributeValue rodinAttribute = rodinElement.getAttributeValue(type);
			Attribute eventBAttribute = CoreFactory.eINSTANCE.createAttribute();
			eventBAttribute.setType(getType(rodinAttribute));
			eventBAttribute.setValue(rodinAttribute.getValue());
			eventBElement.getAttributes().put(type.getId(), eventBAttribute);
		}
	}

	private AttributeType getType(IAttributeValue rodinAttribute) {
		if (rodinAttribute instanceof IAttributeValue.Boolean)
			return AttributeType.BOOLEAN;
		else if (rodinAttribute instanceof IAttributeValue.Handle)
			return AttributeType.HANDLE;
		else if (rodinAttribute instanceof IAttributeValue.Integer)
			return AttributeType.INTEGER;
		else if (rodinAttribute instanceof IAttributeValue.Long)
			return AttributeType.LONG;
		else if (rodinAttribute instanceof IAttributeValue.String)
			return AttributeType.STRING;
		return null;
	}

	/**
	 * Checks whether the given set of {@link IAttributeType}s intersect with
	 * the {@link IAttributeType}s handled by this class. In this case an
	 * exception is thrown.
	 * 
	 * @param otherwiseTypes
	 * @throws CoreException
	 */
	private void checkConflicts(Set<IAttributeType> otherwiseTypes) {
		Set<IAttributeType> intersection = new HashSet<IAttributeType>(otherwiseTypes);
		intersection.retainAll(handledAttributes);

		if (intersection.size() > 0) {
			PersistencePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, PersistencePlugin.PLUGIN_ID, "Conflicting attribute handlers for: " + intersection.toString()));
		}
	}

	public IRodinElement save(final EventBElement eventBElement, final IRodinElement rodinParent, final IProgressMonitor monitor) throws RodinDBException {
		// get rodin details annotation or create if doesn't exist
		Annotation rodinInternals = eventBElement.getAnnotation(PersistencePlugin.SOURCE_RODIN_INTERNAL_ANNOTATION);
		if (rodinInternals == null) {
			rodinInternals = CoreFactory.eINSTANCE.createAnnotation();
			rodinInternals.setSource(PersistencePlugin.SOURCE_RODIN_INTERNAL_ANNOTATION);
			eventBElement.getAnnotations().add(rodinInternals);
		}
		rodinInternalDetails = rodinInternals.getDetails();

		// create Rodin element
		IInternalElement rodinElement = null;
		if (rodinParent instanceof IRodinFile) {
			IRodinFile file = (IRodinFile) rodinParent;
			if (!file.exists())
				file.create(false, monitor);
			rodinElement = file.getRoot();
			rodinElement.clear(true, monitor);
		} else if (rodinParent instanceof IInternalElement) {
			try {
				rodinElement = getNewRodinElement((IInternalElement) rodinParent, getRodinType());
				rodinElement.create(null, monitor);
			} catch (RodinDBException e) {
				PersistencePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, PersistencePlugin.PLUGIN_ID, "Rodin Exception while saving: " + e.getMessage()));
			}
		} else {
			return null;
		}
		saveAttributes(eventBElement, monitor, rodinElement);
		return rodinElement;
	}

	private IInternalElement getNewRodinElement(IInternalElement rodinParent, IInternalElementType<?> rodinType) throws RodinDBException {
		IInternalElement rodinElement = rodinParent.getInternalElement(getRodinType(), getInternalName());
		if (rodinElement.exists()) {
			//if name clash, overwrite Rodin name with UUID - this should be extremely rare.
			String oldName = rodinElement.getElementName();
			rodinInternalDetails.removeKey("name");
			rodinElement = rodinParent.getInternalElement(getRodinType(), getInternalName());
			PersistencePlugin
					.getDefault()
					.getLog()
					.log(new Status(IStatus.WARNING, PersistencePlugin.PLUGIN_ID, "Element name clash detected, renamed element " + oldName + " to "
							+ rodinElement.getElementName()));
		}
		return rodinElement;
	}

	private void saveAttributes(final EventBElement eventBElement, final IProgressMonitor monitor, final IInternalElement rodinElement) throws RodinDBException {

		if (eventBElement.isSetLocalGenerated()) {
			rodinElement.setAttributeValue(EventBAttributes.GENERATED_ATTRIBUTE, eventBElement.isLocalGenerated(), monitor);
		}

		if (rodinElement instanceof IConfigurationElement) {
			// make sure the element has a configuration
			if (rodinInternalDetails.get(CONFIGURATION) == null) {
				rodinInternalDetails.put(CONFIGURATION, IConfigurationElement.DEFAULT_CONFIGURATION);
			}

			((IConfigurationElement) rodinElement).setConfiguration(rodinInternalDetails.get(CONFIGURATION), monitor);
		}

		if (rodinElement instanceof ICommentedElement && eventBElement instanceof EventBCommented
				&& ((EventBCommented) eventBElement).eIsSet(CorePackage.eINSTANCE.getEventBCommented_Comment())) {
			((ICommentedElement) rodinElement).setComment(((EventBCommented) eventBElement).getComment(), monitor);
		}

		String elementName = null;
		if (eventBElement instanceof EventBNamed && ((EventBNamed) eventBElement).eIsSet(CorePackage.eINSTANCE.getEventBNamed_Name())) {
			elementName = ((EventBNamed) eventBElement).getName();
		}

		if (rodinElement instanceof ILabeledElement) {
			if (elementName != null)
				((ILabeledElement) rodinElement).setLabel(elementName, monitor);
		}

		if (rodinElement instanceof IIdentifierElement) {
			if (elementName != null)
				((IIdentifierElement) rodinElement).setIdentifierString(elementName, monitor);
		}

		saveGenericAttributes(rodinElement, eventBElement, monitor);
	}

	private void saveGenericAttributes(IInternalElement rodinElement, EventBElement eventBElement, IProgressMonitor monitor) throws RodinDBException {
		for (Entry<String, Attribute> attribute : eventBElement.getAttributes()) {
			String id = attribute.getKey();
			Object value = attribute.getValue().getValue();
			IAttributeValue rodinAttributeValue = null;
			switch (attribute.getValue().getType().getValue()) {
			case AttributeType.BOOLEAN_VALUE:
				IAttributeType.Boolean booleanType = (IAttributeType.Boolean) RodinCore.getAttributeType(id);
				rodinAttributeValue = booleanType.makeValue((Boolean) value);
				break;
			case AttributeType.HANDLE_VALUE:
				IAttributeType.Handle handleType = (IAttributeType.Handle) RodinCore.getAttributeType(id);
				rodinAttributeValue = handleType.makeValue((IRodinElement) value);
				break;
			case AttributeType.INTEGER_VALUE:
				IAttributeType.Integer integerType = (IAttributeType.Integer) RodinCore.getAttributeType(id);
				rodinAttributeValue = integerType.makeValue((Integer) value);
				break;
			case AttributeType.LONG_VALUE:
				IAttributeType.Long longType = (IAttributeType.Long) RodinCore.getAttributeType(id);
				rodinAttributeValue = longType.makeValue((Long) value);
				break;
			case AttributeType.STRING_VALUE:
				IAttributeType.String stringType = (IAttributeType.String) RodinCore.getAttributeType(id);
				rodinAttributeValue = stringType.makeValue((String) value);
				break;
			default:
			}
			if (rodinAttributeValue != null)
				rodinElement.setAttributeValue(rodinAttributeValue, monitor);
		}
	}

	protected String getInternalName() {
		String name = rodinInternalDetails.get(NAME);
		if (name == null) {
			// no name retrieved from internal details so create one
			return getNewName();
		}
		return name;
	}

	protected String getNewName() {
		return EcoreUtil.generateUUID();
	}

}
