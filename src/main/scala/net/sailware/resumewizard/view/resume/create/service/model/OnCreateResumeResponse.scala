package net.sailware.resumewizard.view.resume.create.service.model

import net.sailware.resumewizard.resume.Resume
import net.sailware.resumewizard.resume.model.CreateResumeResponse

case class OnCreateResumeResponse(
    val resume: Resume
)

object OnCreateResumeResponse:

  def apply(response: CreateResumeResponse): OnCreateResumeResponse =
    new OnCreateResumeResponse(response.resume)
