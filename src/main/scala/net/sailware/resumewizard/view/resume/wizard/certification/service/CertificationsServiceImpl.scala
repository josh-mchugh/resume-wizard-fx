package net.sailware.resumewizard.view.resume.wizard.certification.service

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.resume.wizard.certification.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.certification.service.model.OnContinueResponse
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CertificationsServiceImpl(
    val resumeService: ResumeService
) extends CertificationsService:

  val logger = LoggerFactory.getLogger(classOf[CertificationsServiceImpl])

  override def onContinue(request: OnContinueRequest): Future[OnContinueResponse] =
    logger.info("OnContinueRequest: '{}'", request)
    Future {
      OnContinueResponse(resumeService.handleCertificationsUpdate(request.toTuple))
    }
