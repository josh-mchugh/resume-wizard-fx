package net.sailware.resumewizard.resume.model

import net.sailware.resumewizard.resume.Resume

case class CreatePersonalResponse(
    val resume: Resume
)

object CreatePersonalResponse:

  def appyl(resume: Resume): CreatePersonalResponse =
    new CreatePersonalResponse(resume)
