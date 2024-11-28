package net.sailware.resumewizard.resume.model

import net.sailware.resumewizard.resume.Resume

case class CreateCertificationsResponse(
  resume: Resume
)

object CreateCertificationsResponse:

  def apply(resume: Resume): CreateCertificationsResponse =
    new CreateCertificationsResponse(resume)
