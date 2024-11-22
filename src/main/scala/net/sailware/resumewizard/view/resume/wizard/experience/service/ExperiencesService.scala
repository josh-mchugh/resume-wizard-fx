package net.sailware.resumewizard.view.resume.wizard.experience.service

import net.sailware.resumewizard.view.resume.wizard.experience.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.experience.service.model.OnContinueResponse
import scala.concurrent.Future

trait ExperiencesService:

  def onContinue(request: OnContinueRequest): Future[OnContinueResponse]
