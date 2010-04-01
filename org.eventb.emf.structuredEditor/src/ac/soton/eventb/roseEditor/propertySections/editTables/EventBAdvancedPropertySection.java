package ac.soton.eventb.roseEditor.propertySections.editTables;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class EventBAdvancedPropertySection extends AbstractPropertySection implements IFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try {
			@SuppressWarnings("unused")
			EObject element = (EObject) selected;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected PropertySheetPage page;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		page = new PropertySheetPage();
		//adapterFactory = RoseEditor
		page.setPropertySourceProvider(new AdapterFactoryContentProvider(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)));
		//new XXXItemProviderAdapterFactory()));

		page.createControl(composite);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		page.getControl().setLayoutData(data);
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		page.selectionChanged(part, selection);
	}

	@Override
	public void dispose() {
		super.dispose();

		if (page != null) {
			page.dispose();
			page = null;
		}

	}

	@Override
	public void refresh() {
		page.refresh();
	}

	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}
}