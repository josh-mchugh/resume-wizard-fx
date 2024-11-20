package net.sailware.resumewizard.view.resume.create

import scalafx.beans.property.StringProperty

case class CreateResumeModel(
    val name: StringProperty = StringProperty("")
):
  val createBtnEnabled = name.isEmpty
