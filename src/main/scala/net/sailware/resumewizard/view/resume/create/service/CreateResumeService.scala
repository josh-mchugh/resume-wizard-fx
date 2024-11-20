package net.sailware.resumewizard.view.resume.create.service

import net.sailware.resumewizard.view.resume.create.service.model.OnCreateResumeRequest
import net.sailware.resumewizard.view.resume.create.service.model.OnCreateResumeResponse
import scala.concurrent.Future

trait CreateResumeService:

  def onCreateResume(request: OnCreateResumeRequest): Future[OnCreateResumeResponse]
