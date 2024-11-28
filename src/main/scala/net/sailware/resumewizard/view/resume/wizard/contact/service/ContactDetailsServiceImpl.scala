package net.sailware.resumewizard.view.resume.wizard.contact.service

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.resume.wizard.contact.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.contact.service.model.OnContinueResponse
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ContactDetailsServiceImpl(val resumeService: ResumeService) extends ContactDetailsService:

  val logger = LoggerFactory.getLogger(classOf[ContactDetailsServiceImpl])

  override def onContinue(request: OnContinueRequest): Future[OnContinueResponse] =
    logger.info("OnContinueRequest: '{}'", request)
    Future {
      OnContinueResponse(resumeService.handleContactDetailsUpdate(request.toCreateContactRequest))
    }
