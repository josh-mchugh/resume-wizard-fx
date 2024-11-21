package net.sailware.resumewizard.view.resume.wizard.social.service

import net.sailware.resumewizard.view.resume.wizard.social.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.social.service.model.OnContinueResponse
import scala.concurrent.Future

trait SocialsService:

  def onContinue(request: OnContinueRequest): Future[OnContinueResponse]
