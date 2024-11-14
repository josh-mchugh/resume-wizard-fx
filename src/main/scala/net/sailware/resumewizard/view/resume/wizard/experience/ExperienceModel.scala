package net.sailware.resumewizard.view.resume.wizard.experience

import scalafx.beans.property.StringProperty

case class ExperienceModel(
    val title: StringProperty = StringProperty(""),
    val organization: StringProperty = StringProperty(""),
    val duration: StringProperty = StringProperty(""),
    val location: StringProperty = StringProperty(""),
    val description: StringProperty = StringProperty(""),
    val skills: StringProperty = StringProperty("")
)
