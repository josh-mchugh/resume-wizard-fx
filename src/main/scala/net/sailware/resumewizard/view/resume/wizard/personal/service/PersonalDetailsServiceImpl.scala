package net.sailware.resumewizard.view.resume.wizard.personal.service

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.resume.wizard.personal.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.personal.service.model.OnContinueResponse
import org.slf4j.LoggerFactory
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class PersonalDetailsServiceImpl(val resumeService: ResumeService) extends PersonalDetailsService:

  val logger = LoggerFactory.getLogger(classOf[PersonalDetailsServiceImpl])

  override def onPersonalDetailsSave(request: OnContinueRequest): Future[OnContinueResponse] =
    logger.info("OnContinueRequest '{}'", request)
    Future {
      OnContinueResponse(resumeService.handlePersonalDetailsUpdate(request.name, request.title, request.summary))
    }
