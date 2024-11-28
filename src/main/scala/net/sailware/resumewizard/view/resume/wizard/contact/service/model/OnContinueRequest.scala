package net.sailware.resumewizard.view.resume.wizard.contact.service.model

import net.sailware.resumewizard.resume.model.CreateContactRequest
import net.sailware.resumewizard.view.resume.wizard.contact.ContactDetailsModel

case class OnContinueRequest(
    val phone: String,
    val email: String,
    val location: String
):

  def toCreateContactRequest: CreateContactRequest =
    CreateContactRequest(phone, email, location)

object OnContinueRequest:

  def apply(model: ContactDetailsModel): OnContinueRequest =
    new OnContinueRequest(model.phone(), model.email(), model.location())
