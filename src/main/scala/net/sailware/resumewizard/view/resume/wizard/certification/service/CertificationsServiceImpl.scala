package net.sailware.resumewizard.view.resume.wizard.certification.service

import net.sailware.resumewizard.resume.ResumeService
import org.slf4j.LoggerFactory
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CertificationsServiceImpl(val resumeService: ResumeService) extends CertificationsService:

  val logger = LoggerFactory.getLogger(classOf[CertificationsServiceImpl])

  override def handleCertificationsUpdate(certifications: List[(String, String, String, String)]): Unit =
    logger.info("certifications: ", certifications.mkString(", "))
    Future {
      resumeService.handleCertificationsUpdate(certifications)
    } onComplete:
      case Success(resume) => logger.info("new resume: {}", resume)
      case Failure(t)      => logger.error("it failed to update resume")
