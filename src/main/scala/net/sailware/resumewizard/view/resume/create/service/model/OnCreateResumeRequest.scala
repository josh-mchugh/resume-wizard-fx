package net.sailware.resumewizard.view.resume.create.service.model

import net.sailware.resumewizard.view.resume.create.CreateResumeModel

case class OnCreateResumeRequest(
    val name: String
)

object OnCreateResumeRequest:

  def apply(name: String): OnCreateResumeRequest =
    new OnCreateResumeRequest(name)

  def apply(model: CreateResumeModel) =
    new OnCreateResumeRequest(model.name())
