package net.sailware.resumewizard.view.resume.create.service.model

import net.sailware.resumewizard.resume.Resume

case class OnCreateResumeResponse(
    val resume: Resume
)

object OnCreateResumeResponse:

  def apply(resume: Resume): OnCreateResumeResponse =
    new OnCreateResumeResponse(resume)
