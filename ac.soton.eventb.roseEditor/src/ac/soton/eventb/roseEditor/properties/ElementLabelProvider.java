/*******************************************************************************
 * Copyright (c) 2006,2007,2008 University of Southampton and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package ac.soton.eventb.roseEditor.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.context.ContextPackage;
import org.eventb.emf.core.machine.MachinePackage;


/**
 * Label provider for the title bar of the tabbed property sheet page.
 *
 * @author Colin Snook
 */


public class ElementLabelProvider extends LabelProvider {


	private final ITypeMapper typeMapper;		//used in multiple selections below only

	/**
	 * constructor.
	 */
	public ElementLabelProvider() {
		super();
		typeMapper = new ElementTypeMapper();
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	// This method defines the image to appear in the label. Currently no icon is provided
	@Override
	public Image getImage(final Object element) {
		return null ;
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object objects) {
		if (objects == null || objects.equals(StructuredSelection.EMPTY)) {
			return "No items selected";//$NON-NLS-1$
		}
		final boolean multiple[] = {false};
		final Object object = getObject(objects, multiple);
		if (object == null || ((IStructuredSelection) objects).size() > 1) {
			return ((IStructuredSelection) objects).size() + " items selected";//$NON-NLS-1$
		} else {
			return getLabelForItem(object);
		}
	}

	/**
	 * Method to set the label name of the properties window
	 *
	 * @param object the selected part
	 * @return the label for the properties window.
	 */
	private String getLabelForItem(final Object object){
		if (object instanceof EventBElement){
			return getLabelForElement((EventBElement)object);
		}else {
			return "";	//default to no title
		}
	}


	/**
	 * Method to set the label name of the properties window when selection represents a EventBElement
	 *
	 * @param umlbelement the selected diagram part
	 * @return the label for the properties window.
	 * @generated NOT
	 */
	protected String getLabelForElement(final EventBElement element){
		EPackage ePackage = element.eClass().getEPackage();
		
		String elementName="<un-named>";
//		if (element instanceof EventBNamed){
//			elementName = ((EventBNamed)element).getName();
//		}else{
			//try to find a feature that looks appropriate
			Object label=null;
			EStructuralFeature labelFeature = element.eClass().getEStructuralFeature("name");
			if (labelFeature == null) labelFeature = element.eClass().getEStructuralFeature("label");
			if (labelFeature == null) labelFeature = element.eClass().getEStructuralFeature("identifier");
			if (labelFeature != null)
				label = element.eGet(labelFeature);
			if (label instanceof String){
				elementName = (String)label;
			}
//		}
		String kindName;
		int umlbElementKind = element.eClass().getClassifierID();

		if (ePackage == CorePackage.eINSTANCE){
			switch (umlbElementKind){
			case CorePackage.PROJECT: 					{kindName =  "Project"; break;}
			default:{kindName="UNRECOGNISED ELEMENT";}}
		}else if (ePackage == MachinePackage.eINSTANCE){
			switch (umlbElementKind){
			case MachinePackage.MACHINE: 				{kindName =  "Machine"; break;}
			case MachinePackage.VARIABLE: 				{kindName =  "Variable"; break;}
			case MachinePackage.INVARIANT: 				{kindName =  "Invariant"; break;}
			case MachinePackage.VARIANT: 				{kindName =  "Variant"; break;}
			case MachinePackage.ACTION: 				{kindName =  "Action"; break;}
			case MachinePackage.EVENT: 					{kindName =  "Event"; break;}
			case MachinePackage.CONVERGENCE: 			{kindName =  "Convergence"; break;}
			case MachinePackage.GUARD: 					{kindName =  "Guard"; break;}
			case MachinePackage.PARAMETER: 				{kindName =  "Parameter"; break;}
			case MachinePackage.WITNESS: 				{kindName =  "Witness"; break;}
			default:{kindName="UNRECOGNISED ELEMENT";}}
			kindName = ePackage.getName()+" : "+kindName;
			
		}else if (ePackage == ContextPackage.eINSTANCE){
			switch (umlbElementKind){
			case ContextPackage.CONTEXT: 				{kindName =  "Context"; break;}
			case ContextPackage.CARRIER_SET: 			{kindName =  "Carrier Set"; break;}
			case ContextPackage.CONSTANT: 				{kindName =  "Constant"; break;}
			case ContextPackage.AXIOM: 					{kindName =  "Axiom"; break;}
			default:{kindName="UNRECOGNISED ELEMENT";}}
			kindName = ePackage.getName()+" : "+kindName;

		}else{
			kindName = ePackage.getName()+" : "+element.eClass().getName();
			//kindName="UNRECOGNISED ELEMENT";
		}
		String generated = "";
		if (element.isGenerated()) generated = " (GENERATED)";
		return kindName+" - "+elementName + generated;
	}

	/**
	 * Determine if a multiple object selection has been passed to the label
	 * provider. If the objects is a IStructuredSelection, see if all the
	 * objects in the selection are the same and if so, we want to provide
	 * labels for the common selected element.
	 *
	 * @param objects
	 *            a single object or a IStructuredSelection.
	 * @param multiple
	 *            first element in the array is true if there is multiple
	 *            unequal selected elements in a IStructuredSelection.
	 * @return the object to get labels for.
	 */
	private Object getObject(final Object objects, final boolean multiple[]) {
		Assert.isNotNull(objects);
		Object object = null;
		if (objects instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) objects;
			object = selection.getFirstElement();
			if (selection.size() == 1) {
				// one element selected
				multiple[0] = false;
				return object;
			}
			// multiple elements selected
			multiple[0] = true;
			Class<?> firstClass = typeMapper.mapType(object);
			// determine if all the objects in the selection are the same type
			for (Object next: selection.toArray()) {
				 //= i.next();
				Class<?> nextClass = typeMapper.mapType(next);
				if (!nextClass.equals(firstClass)) {
					// two elements not equal == multiple selected unequal
					multiple[0] = false;
					object = null;
					break;
				}
			}
		} else {
			multiple[0] = false;
			object = objects;
		}
		return object;
	}

}