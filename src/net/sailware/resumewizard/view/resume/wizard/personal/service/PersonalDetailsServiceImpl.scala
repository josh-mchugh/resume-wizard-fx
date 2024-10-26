package net.sailware.resumewizard.view.resume.wizard.personal.service

import net.sailware.resumewizard.resume.ResumeService
import org.slf4j.LoggerFactory
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class PersonalDetailsServiceImpl(val resumeService: ResumeService) extends PersonalDetailsService:

  val logger = LoggerFactory.getLogger(classOf[PersonalDetailsServiceImpl])

  override def onPersonalDetailsSave(name: String, title: String, summary: String): Unit =
    logger.info("name: '{}', title: '{}', summary: '{}'", name, title, summary)
    Future {
      resumeService.handlePersonalDetailsUpdate(name, title, summary)
    } onComplete:
      case Success(resume) => logger.info("new resume: {}", resume)
      case Failure(t) => logger.error("it failed to create resume", t)
