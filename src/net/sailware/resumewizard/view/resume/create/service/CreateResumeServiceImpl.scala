package net.sailware.resumewizard.view.resume.create.service

import org.slf4j.LoggerFactory

class CreateResumeServiceImpl() extends CreateResumeService:
  val logger = LoggerFactory.getLogger(classOf[CreateResumeServiceImpl])

  override def createResume(name: String): Unit =
    logger.info("resume created: {}", name)
