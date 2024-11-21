package net.sailware.resumewizard.view.resume.wizard.contact.service.model

import net.sailware.resumewizard.view.resume.wizard.contact.ContactDetailsModel

case class OnContinueRequest(
    val phone: String,
    val email: String,
    val location: String
)

object OnContinueRequest:

  def apply(phone: String, email: String, location: String): OnContinueRequest =
    new OnContinueRequest(phone, email, location)

  def apply(model: ContactDetailsModel): OnContinueRequest =
    new OnContinueRequest(model.phone(), model.email(), model.location())
