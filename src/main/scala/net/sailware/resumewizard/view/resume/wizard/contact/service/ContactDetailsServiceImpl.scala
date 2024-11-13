package net.sailware.resumewizard.view.resume.wizard.contact.service

import net.sailware.resumewizard.resume.ResumeService
import org.slf4j.LoggerFactory
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ContactDetailsServiceImpl(val resumeService: ResumeService) extends ContactDetailsService:

  val logger = LoggerFactory.getLogger(classOf[ContactDetailsServiceImpl])

  override def handleContactDetailsUpdate(phone: String, email: String, location: String): Unit =
    logger.info("phone: '{}', email: '{}', location: '{}'", phone, email, location)
    Future {
      resumeService.handleContactDetailsUpdate(phone, email, location)
    } onComplete:
      case Success(resume) => logger.info("new resume: {}", resume)
      case Failure(t) => logger.error("it failed to create resume", t)
