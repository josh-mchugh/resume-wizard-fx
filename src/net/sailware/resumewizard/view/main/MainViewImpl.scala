package net.sailware.resumewizard.view.main

import net.sailware.resumewizard.view.core.PageFactory
import net.sailware.resumewizard.view.core.PageType
import net.sailware.resumewizard.view.core.State
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.VPos
import scalafx.scene.control.ScrollPane
import scalafx.scene.control.ScrollPane.ScrollBarPolicy
import scalafx.scene.layout.AnchorPane
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.Region
import scalafx.scene.layout.StackPane
import scalafx.scene.text.Font
import scalafx.scene.text.FontWeight
import scalafx.scene.text.Text

class MainViewImpl(
  val presenter: MainPresenter,
  val state: ObjectProperty[State],
  val pageFactory: PageFactory
) extends MainView:

  override def view(): Region =
    createRootPane()

  private def createRootPane(): Region =
    new AnchorPane:
      children = createBasePane()

  private def createBasePane(): StackPane =
    val result = new StackPane:
      children = createBorderFramePane()
    AnchorPane.setTopAnchor(result, 0.0)
    AnchorPane.setLeftAnchor(result, 0.0)
    AnchorPane.setBottomAnchor(result, 0.0)
    AnchorPane.setRightAnchor(result, 0.0)
    result

  private def createBorderFramePane(): BorderPane =
    new BorderPane:
      top = createTopNavigation()
      center = createMainContent()

  private def createTopNavigation(): Text =
    new Text("Resume Wizard Top"):
      textOrigin = VPos.Top
      font = Font.font(null, FontWeight.Bold, 18)

  private def createMainContent(): ScrollPane =
    new ScrollPane:
      vbarPolicy = ScrollBarPolicy.AS_NEEDED
      fitToHeight = true
      hbarPolicy = ScrollBarPolicy.NEVER
      fitToWidth = true
      content = new StackPane:
        style = "-fx-padding: 20;"
        children = pageFactory.createPage(PageType.Dashboard)
        state.onChange ({
           children = pageFactory.createPage(state.value.currentPageType)
        })
