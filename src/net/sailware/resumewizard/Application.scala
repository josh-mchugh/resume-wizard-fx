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
  val body = new StackPane:
    children = new MainLayer

  children = body

class MainLayer extends BorderPane:

  val topText = new Text("Resume Wizard Top"):
    textOrigin = VPos.Top
    font = Font.font(null, FontWeight.Bold, 18)

  val centerText = new Text("Resume Wizard Center"):
    textOrigin = VPos.Top
    font = Font.font(null, FontWeight.Bold, 18)

  top = topText
  center = new HBox:
      style = "--fx-border-color: red; -fx-border-width: 1; -fx-border-style: solid;"
      alignment = Pos.CENTER
      children = new VBox
        alignment = Pos.CENTER
        children = centerText
      
