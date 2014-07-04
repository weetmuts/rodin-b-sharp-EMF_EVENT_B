/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package ac.soton.eventb.roseEditor;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eventb.emf.core.provider.EventbcoreEditPlugin;

/**
 * This is the central singleton for the Eventbcore editor plugin. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public final class RoseEditorPlugin extends EMFPlugin {
	/**
	 * Plug-in ID
	 */
	public static final String PLUGIN_ID = "ac.soton.eventb.roseEditor";

	/**
	 * ID to identify the editor
	 */
	public static final String EDITOR_ID = PLUGIN_ID + ".editorID";

	/**
	 * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public static final RoseEditorPlugin INSTANCE = new RoseEditorPlugin();

	/**
	 * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * The EventbcoreEditPlugin is added as a resource locator to delegate to.
	 * This is probably unused for now but could be useful.
	 * (added this while trying to fix another problem)
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public RoseEditorPlugin() {
		super(new ResourceLocator[] { EventbcoreEditPlugin.INSTANCE });
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static class Implementation extends EclipseUIPlugin {
		/**
		 * Creates an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}
	}

}
