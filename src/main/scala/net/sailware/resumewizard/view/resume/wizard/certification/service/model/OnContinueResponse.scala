package net.sailware.resumewizard.view.resume.wizard.certification.service.model

import net.sailware.resumewizard.resume.Resume

case class OnContinueResponse(
    resume: Resume
)

object OnContiueResponse:

  def apply(resume: Resume): OnContinueResponse =
    new OnContinueResponse(resume)
