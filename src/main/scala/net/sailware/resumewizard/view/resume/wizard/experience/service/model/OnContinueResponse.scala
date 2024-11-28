package net.sailware.resumewizard.view.resume.wizard.experience.service.model

import net.sailware.resumewizard.resume.model.CreateExperiencesResponse
import net.sailware.resumewizard.resume.Resume

case class OnContinueResponse(
    val resume: Resume
)

object OnContinueResponse:

  def apply(response: CreateExperiencesResponse): OnContinueResponse =
    new OnContinueResponse(response.resume)
