package net.sailware.resumewizard.view.resume.create.service

import net.sailware.resumewizard.resume.Resume
import net.sailware.resumewizard.resume.ResumeService
import org.slf4j.LoggerFactory
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CreateResumeServiceImpl(val resumeService: ResumeService) extends CreateResumeService:
  val logger = LoggerFactory.getLogger(classOf[CreateResumeServiceImpl])

  override def createResume(name: String): Unit =
    logger.info("resume created: {}", name)
    Future {
      resumeService.handleCreateResume(name)
    } onComplete:
      case Success(resume) => logger.info("new resume: {}", resume)
      case Failure(t)      => logger.error("it failed to create resume", t)
