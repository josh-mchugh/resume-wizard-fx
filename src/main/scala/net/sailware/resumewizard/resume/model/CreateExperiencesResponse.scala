package net.sailware.resumewizard.resume.model

import net.sailware.resumewizard.resume.Resume

case class CreateExperiencesResponse(
    val resume: Resume
)

object CreateExperiencesResponse:

  def apply(resume: Resume): CreateExperiencesResponse =
    new CreateExperiencesResponse(resume)
