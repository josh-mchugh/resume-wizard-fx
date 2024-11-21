package net.sailware.resumewizard.view.resume.wizard.social.service

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.resume.wizard.social.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.social.service.model.OnContinueResponse
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

class SocialsServiceImpl(
    val resumeService: ResumeService
) extends SocialsService:

  val logger = LoggerFactory.getLogger(classOf[SocialsServiceImpl])

  override def onContinue(request: OnContinueRequest): Future[OnContinueResponse] =
    logger.info("OnContinueRequest: '{}'", request)
    Future {
      OnContinueResponse(resumeService.handleSocialsUpdate(request.socials))
    }
