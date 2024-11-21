package net.sailware.resumewizard.view.resume.wizard.social

import scalafx.beans.property.StringProperty

case class SocialFormModel(
    val name: StringProperty = StringProperty(""),
    val url: StringProperty = StringProperty("")
)
