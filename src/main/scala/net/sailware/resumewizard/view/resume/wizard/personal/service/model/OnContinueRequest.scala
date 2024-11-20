package net.sailware.resumewizard.view.resume.wizard.personal.service.model

import net.sailware.resumewizard.view.resume.wizard.personal.PersonalDetailsModel

case class OnContinueRequest(
    val name: String,
    val title: String,
    val summary: String
)

object OnContinueRequest:

  def apply(name: String, title: String, summary: String): OnContinueRequest =
    new OnContinueRequest(name, title, summary)

  def apply(model: PersonalDetailsModel): OnContinueRequest =
    new OnContinueRequest(model.name(), model.title(), model.summary())
