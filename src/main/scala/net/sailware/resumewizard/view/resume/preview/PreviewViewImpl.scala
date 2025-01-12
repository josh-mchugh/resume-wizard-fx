package net.sailware.resumewizard.view.resume.preview

import scalafx.Includes.*
import scalafx.scene.Node
import scalafx.scene.layout.Pane
import scalafx.scene.text.Text

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  override def view(): Node =
    new Pane:
      style = "-fx-background-color: white"
      maxWidth = 793.7F
      maxHeight = 1122.52F
      minWidth = 793.7F
      minHeight = 1122.52F
      children = new Text("Hello World"):
        x = 50
        y = 50
