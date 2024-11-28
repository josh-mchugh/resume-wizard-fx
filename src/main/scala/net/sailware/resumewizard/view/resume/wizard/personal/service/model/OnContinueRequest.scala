package net.sailware.resumewizard.view.resume.wizard.personal.service.model

import net.sailware.resumewizard.resume.model.CreatePersonalRequest
import net.sailware.resumewizard.view.resume.wizard.personal.PersonalDetailsModel

case class OnContinueRequest(
    val name: String,
    val title: String,
    val summary: String
):

  def toCreateResumePersonalRequest: CreatePersonalRequest =
    CreatePersonalRequest(name, title, summary)

object OnContinueRequest:

  def apply(model: PersonalDetailsModel): OnContinueRequest =
    new OnContinueRequest(model.name(), model.title(), model.summary())
