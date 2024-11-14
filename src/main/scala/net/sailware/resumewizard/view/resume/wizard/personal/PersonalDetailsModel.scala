package net.sailware.resumewizard.view.resume.wizard.personal

import scalafx.beans.property.StringProperty

case class PersonalDetailsModel(
    val name: StringProperty = StringProperty(""),
    val title: StringProperty = StringProperty(""),
    val summary: StringProperty = StringProperty("")
)
