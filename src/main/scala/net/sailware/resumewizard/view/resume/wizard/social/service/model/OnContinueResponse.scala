package net.sailware.resumewizard.view.resume.wizard.social.service.model

import net.sailware.resumewizard.resume.Resume
import net.sailware.resumewizard.resume.model.CreateSocialsResponse

case class OnContinueResponse(
    val resume: Resume
)

object OnContinueResponse:

  def apply(response: CreateSocialsResponse): OnContinueResponse =
    new OnContinueResponse(response.resume)
