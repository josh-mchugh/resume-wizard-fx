package net.sailware.resumewizard.resume.model

import net.sailware.resumewizard.resume.Resume

case class CreateContactResponse(
  resume: Resume
)

object CreateContactResponse:

  def apply(resume: Resume): CreateContactResponse =
    new CreateContactResponse(resume)
