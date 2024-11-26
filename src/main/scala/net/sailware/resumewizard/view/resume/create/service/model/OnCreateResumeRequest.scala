package net.sailware.resumewizard.view.resume.create.service.model

import net.sailware.resumewizard.resume.model.CreateResumeRequest
import net.sailware.resumewizard.view.resume.create.CreateResumeModel

case class OnCreateResumeRequest(
    val name: String
):

  def toCreateResumeRequest: CreateResumeRequest =
    CreateResumeRequest(name)

object OnCreateResumeRequest:

  def apply(model: CreateResumeModel) =
    new OnCreateResumeRequest(model.name())
