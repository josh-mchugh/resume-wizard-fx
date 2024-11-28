package net.sailware.resumewizard.view.resume.wizard.contact.service.model

import net.sailware.resumewizard.resume.Resume
import net.sailware.resumewizard.resume.model.CreateContactResponse

case class OnContinueResponse(
    val resume: Resume
)

object OnContinueResponse:

  def apply(response: CreateContactResponse): OnContinueResponse =
    new OnContinueResponse(response.resume)
