package net.sailware.resumewizard.view.resume.preview

import scalafx.Includes.*
import scalafx.scene.Node
import scalafx.scene.text.Text

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  override def view(): Node =
    Text("Hello")
