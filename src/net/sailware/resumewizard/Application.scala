package net.sailware.resumewizard

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.VPos
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.scene.control.TextField
import scalafx.scene.control.ScrollPane
import scalafx.scene.control.ScrollPane.ScrollBarPolicy
import scalafx.scene.layout.AnchorPane
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.VBox
import scalafx.scene.layout.Priority
import scalafx.scene.layout.StackPane
import scalafx.scene.text.{Font, FontWeight, Text, TextFlow}
import scalafx.stage.Stage

object Main extends JFXApp3:

  override def start(): Unit =
    stage = new PrimaryStage:
      scene = new Scene(1280, 768):
        root = createRootPane

  def createRootPane =
    new AnchorPane:
      children = createRootStackPane

  def createRootStackPane =
    val result = new StackPane:
      children = createMainLayer
    AnchorPane.setTopAnchor(result, 0.0)
    AnchorPane.setLeftAnchor(result, 0.0)
    AnchorPane.setBottomAnchor(result, 0.0)
    AnchorPane.setRightAnchor(result, 0.0)
    result

  def createMainLayer =
    new BorderPane:
      top = topContent
      center = centerContent

  // Create Top Content
  def topContent =
    new Text("Resume Wizard Top"):
      textOrigin = VPos.Top
      font = Font.font(null, FontWeight.Bold, 18)

  // Create Center Content
  def centerContent =
    new ScrollPane:
      vbarPolicy = ScrollBarPolicy.AS_NEEDED
      fitToHeight = true
      hbarPolicy = ScrollBarPolicy.NEVER
      fitToWidth = true
      content = createDashboardPane

  def createDashboardPane =
    new StackPane:
      style = "-fx-padding: 20;"
      children = createResumeButton(this)

  def createResumeButton(stackPane: StackPane) =
    new Button("Create Resume"):
      onAction = (event: ActionEvent) => stackPane.getChildren().add(createResumePane)

  def createResumePane =
    new VBox(5):
      style = """
              -fx-padding: 20;
              -fx-background-color: #ffffff;
              -fx-background-radius: 5;
              -fx-border-color: rgba(0, 0, 0, 0.18); 
              -fx-border-width: 1; 
              -fx-border-style: solid;
              -fx-border-radius: 5;
              -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.18), 10, 0, 0, 0);
              """
      maxWidth = 1020
      children = List(
        new Text("Create New Resume"),
        new Button("Create Resume"),
        new Label("Resume Name") { },
        new TextField { }
      )

