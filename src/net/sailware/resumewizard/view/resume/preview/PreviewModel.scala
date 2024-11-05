package net.sailware.resumewizard.view.resume.preview

import java.io.File
import scalafx.beans.property.ObjectProperty

case class PreviewModel(
  val pdf: ObjectProperty[File] = new ObjectProperty[File]()
)
