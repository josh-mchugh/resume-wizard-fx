package net.sailware.resumewizard.view.resume.wizard.social.service

import net.sailware.resumewizard.resume.ResumeService
import org.slf4j.LoggerFactory
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SocialsServiceImpl(val resumeService: ResumeService) extends SocialsService:

  val logger = LoggerFactory.getLogger(classOf[SocialsServiceImpl])

  override def handleSocialsUpdate(socialTuples: List[(String, String)]): Unit =
    logger.info("socials: {}", socialTuples.mkString(", "))
    Future {
      resumeService.handleSocialsUpdate(socialTuples)
    } onComplete:
      case Success(resume) => logger.info("new resume: {}", resume)
      case Failure(t) => logger.error("it failed to create resume", t)
