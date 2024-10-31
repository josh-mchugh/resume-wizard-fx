package net.sailware.resumewizard.view.resume.wizard.experience.service

import net.sailware.resumewizard.resume.ResumeService
import org.slf4j.LoggerFactory
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ExperiencesServiceImpl(val resumeService: ResumeService) extends ExperiencesService:

  val logger = LoggerFactory.getLogger(classOf[ExperiencesServiceImpl])

  override def handleExperiencesUpdate(experienceTuples: List[(String, String, String, String, String, String)]): Unit =
    logger.info("experiences: {}", experienceTuples.mkString(", "))
    Future {
      resumeService.handleExperiencesUpdate(experienceTuples)
    } onComplete:
      case Success(resume) => logger.info("new resume: {}", resume)
      case Failure(t) => logger.error("it failed to update resume", t)
