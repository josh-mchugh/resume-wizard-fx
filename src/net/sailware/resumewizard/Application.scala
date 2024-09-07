package net.sailware.resumewizard

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.geometry.Pos
import scalafx.geometry.VPos
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.AnchorPane
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.HBox
import scalafx.scene.layout.VBox
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
    val centerText = new Text("Resume Wizard Center"):
      textOrigin = VPos.Top
      font = Font.font(null, FontWeight.Bold, 18)
    new HBox:
      style = "--fx-border-color: red; -fx-border-width: 1; -fx-border-style: solid;"
      alignment = Pos.CENTER
      children = new VBox
        alignment = Pos.CENTER
        children = centerText

