package net.sailware.resumewizard.view.resume.wizard.social.service.model

import net.sailware.resumewizard.resume.Resume

case class OnContinueResponse(
    val resume: Resume
)

object OnContiunueResponse:

  def apply(resume: Resume): OnContinueResponse =
    new OnContinueResponse(resume)
