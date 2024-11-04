package net.sailware.resumewizard.view.resume.preview

import scalafx.embed.swing.SwingNode
import scalafx.scene.Node
import scalafx.scene.layout.Region
import javax.swing.*
import java.awt.*
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.ViewerPropertiesManager;

class PreviewViewImpl(val model : PreviewModel) extends PreviewView:

  override def view(): Node =

    val swingController = new SwingController()
    swingController.setIsEmbeddedComponent(true)

    FontPropertiesManager.getInstance().loadOrReadSystemFonts()
    val properties = ViewerPropertiesManager.getInstance()
    properties.getPreferences().putFloat(ViewerPropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 1.25f);
    properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_UTILITY_OPEN, false);
    properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_UTILITY_SAVE, false);
    properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_UTILITY_PRINT, false);
    // hide the status bar
    properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_STATUSBAR, false);
    // hide a few toolbars, just to show how the prefered size of the viewer changes.
    properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, false);
    properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, false);
    properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, false);
    properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_FORMS, false);

    swingController.getDocumentViewController().setAnnotationCallback(
      new org.icepdf.ri.common.MyAnnotationCallback(swingController.getDocumentViewController())
    );

    val factory = new SwingViewBuilder(swingController, properties)
    val viewerPanel = factory.buildViewerPanel()
    val swingNode = new SwingNode()
    swingNode.setContent(viewerPanel)

    SwingUtilities.invokeLater(() => {
      swingController.openDocument(getClass.getResource("/helloworld.pdf").getPath)
      viewerPanel.revalidate()
    })
    swingNode
