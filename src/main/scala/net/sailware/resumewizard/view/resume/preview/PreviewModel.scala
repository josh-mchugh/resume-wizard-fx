package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.resume.Resume
import scalafx.beans.property.ObjectProperty

case class PreviewModel(
    val resume: ObjectProperty[Resume] = new ObjectProperty[Resume]()
)
