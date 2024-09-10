package net.sailware.resumewizard

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.VPos
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.ScrollPane
import scalafx.scene.control.ScrollPane.ScrollBarPolicy
import scalafx.scene.layout.AnchorPane
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.StackPane
import scalafx.scene.text.{Font, FontWeight, Text}
import scalafx.stage.Stage

object Application extends JFXApp3:

  override def start(): Unit =
    stage = new PrimaryStage:
      scene = new Scene(1280, 768):
        root = new ApplicationWindow

class ApplicationWindow extends AnchorPane:
  // Create body Stack Pane with Main Layer
  val body = new StackPane:
    children = new MainLayer

  // Set body to anchor to ApplicationWindows edges
  AnchorPane.setTopAnchor(body, 0.0)
  AnchorPane.setLeftAnchor(body, 0.0)
  AnchorPane.setBottomAnchor(body, 0.0)
  AnchorPane.setRightAnchor(body, 0.0)

  // Add body to children of the ApplicationWindow
  children = body

class MainLayer extends BorderPane:

  top = topContent
  center = centerContent

  // Create Top Content
  def topContent =
    new Text("Resume Wizard Top"):
      textOrigin = VPos.Top
      font = Font.font(null, FontWeight.Bold, 18)

  // Create Center Content
  def centerContent =
    val button = new Button("Create Resume"):
      onAction = (event: ActionEvent) => println("Hello from button action")
    new ScrollPane:
      vbarPolicy = ScrollBarPolicy.AS_NEEDED
      fitToHeight = true
      hbarPolicy = ScrollBarPolicy.NEVER
      fitToWidth = true
      content = new StackPane:
        style = "--fx-border-color: red; -fx-border-width: 1; -fx-border-style: solid;"
        children = button
