package net.sailware.resumewizard.resume.model

import net.sailware.resumewizard.resume.Resume

case class CreateResumeResponse(
    resume: Resume
)

object CreateResumeResponse:

  def apply(resume: Resume): CreateResumeResponse =
    new CreateResumeResponse(resume)
