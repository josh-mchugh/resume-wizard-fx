package net.sailware.resumewizard.view.resume.wizard.contact

import scalafx.beans.property.StringProperty

case class ContactDetailsModel(
  val phone: StringProperty = StringProperty(""),
  val email: StringProperty = StringProperty(""),
  val location: StringProperty = StringProperty("")
)
