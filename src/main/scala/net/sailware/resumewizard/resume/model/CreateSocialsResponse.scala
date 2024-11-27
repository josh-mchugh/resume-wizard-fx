package net.sailware.resumewizard.resume.model

import net.sailware.resumewizard.resume.Resume

case class CreateSocialsResponse(
  val resume: Resume
)

object CreateSocialsResponse:

  def apply(resume: Resume): CreateSocialsResponse =
    new CreateSocialsResponse(resume)
