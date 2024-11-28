package net.sailware.resumewizard.view.resume.wizard.personal.service.model

import net.sailware.resumewizard.resume.Resume
import net.sailware.resumewizard.resume.model.CreatePersonalResponse

case class OnContinueResponse(
    val resume: Resume
)

object OnContinueResponse:

  def apply(response: CreatePersonalResponse): OnContinueResponse =
    new OnContinueResponse(response.resume)
