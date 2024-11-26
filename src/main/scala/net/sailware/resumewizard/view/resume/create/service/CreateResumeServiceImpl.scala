package net.sailware.resumewizard.view.resume.create.service

import net.sailware.resumewizard.resume.Resume
import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.resume.model.CreateResumeRequest
import net.sailware.resumewizard.view.resume.create.service.model.OnCreateResumeRequest
import net.sailware.resumewizard.view.resume.create.service.model.OnCreateResumeResponse
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CreateResumeServiceImpl(
    val service: ResumeService
) extends CreateResumeService:

  val logger = LoggerFactory.getLogger(classOf[CreateResumeServiceImpl])

  override def onCreateResume(request: OnCreateResumeRequest): Future[OnCreateResumeResponse] =
    logger.info("OnCreateResumeRequest: '{}'", request)
    Future {
      val response = service.handleCreateResume(CreateResumeRequest(request.name))
      OnCreateResumeResponse(response.resume)
    }
