package net.sailware.resumewizard.view.resume.wizard.social

import scalafx.beans.property.StringProperty

case class SocialModel(
  val name: StringProperty = StringProperty(""),
  val url: StringProperty = StringProperty("")
)
