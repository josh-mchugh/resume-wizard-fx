package net.sailware.resumewizard.view.resume.wizard.personal.service

import net.sailware.resumewizard.resume.Resume
import net.sailware.resumewizard.view.resume.wizard.personal.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.personal.service.model.OnContinueResponse
import scala.concurrent.Future

trait PersonalDetailsService:

  def onPersonalDetailsSave(request: OnContinueRequest): Future[OnContinueResponse]
