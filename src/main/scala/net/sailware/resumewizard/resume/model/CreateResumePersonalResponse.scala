package net.sailware.resumewizard.resume.model

import net.sailware.resumewizard.resume.Resume

case class CreateResumePersonalResponse(
    val resume: Resume
)

object CreateResumePersonalResponse:

  def appyl(resume: Resume): CreateResumePersonalResponse =
    new CreateResumePersonalResponse(resume)
