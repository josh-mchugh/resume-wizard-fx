package net.sailware.resumewizard.view.resume.wizard.contact.service

import net.sailware.resumewizard.view.resume.wizard.contact.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.contact.service.model.OnContinueResponse
import scala.concurrent.Future

trait ContactDetailsService:

  def onContinue(request: OnContinueRequest): Future[OnContinueResponse]
