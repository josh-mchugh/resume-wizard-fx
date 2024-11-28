package net.sailware.resumewizard.view.resume.wizard.certification.service.model

import net.sailware.resumewizard.resume.Resume
import net.sailware.resumewizard.resume.model.CreateCertificationsResponse

case class OnContinueResponse(
    resume: Resume
)

object OnContinueResponse:

  def apply(response: CreateCertificationsResponse): OnContinueResponse =
    new OnContinueResponse(response.resume)
