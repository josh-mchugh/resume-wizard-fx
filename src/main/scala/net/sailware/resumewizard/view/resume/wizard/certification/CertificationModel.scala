package net.sailware.resumewizard.view.resume.wizard.certification

import scalafx.beans.property.StringProperty

case class CertificationModel(
    val title: StringProperty = StringProperty(""),
    val organization: StringProperty = StringProperty(""),
    val duration: StringProperty = StringProperty(""),
    val location: StringProperty = StringProperty("")
)
