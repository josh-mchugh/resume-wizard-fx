package net.sailware.resumewizard.view.resume.wizard.certification.service

import net.sailware.resumewizard.view.resume.wizard.certification.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.certification.service.model.OnContinueResponse
import scala.concurrent.Future

trait CertificationsService:

  def onContinue(request: OnContinueRequest): Future[OnContinueResponse]
