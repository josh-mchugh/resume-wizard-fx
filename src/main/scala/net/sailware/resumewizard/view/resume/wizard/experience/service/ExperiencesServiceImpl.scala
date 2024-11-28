package net.sailware.resumewizard.view.resume.wizard.experience.service

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.resume.wizard.experience.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.experience.service.model.OnContinueResponse
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ExperiencesServiceImpl(val resumeService: ResumeService) extends ExperiencesService:

  val logger = LoggerFactory.getLogger(classOf[ExperiencesServiceImpl])

  override def onContinue(request: OnContinueRequest): Future[OnContinueResponse] =
    logger.info("OnContinueRequest: '{}'", request)
    Future {
      OnContinueResponse(resumeService.handleExperiencesUpdate(request.toCreateExperiencesRequest))
    }
