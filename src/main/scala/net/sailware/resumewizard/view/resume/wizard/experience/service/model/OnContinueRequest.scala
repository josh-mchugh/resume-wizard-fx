package net.sailware.resumewizard.view.resume.wizard.experience.service.model

import net.sailware.resumewizard.view.resume.wizard.experience.ExperiencesModel

case class OnContinueRequest(
    val experiences: List[ExperienceForm]
):

  def toTuple: List[(String, String, String, String, String, String)] =
    experiences.map(experience => (experience.title, experience.organization, experience.duration, experience.location, experience.description, experience.skills))

object OnContinueRequest:

  def apply(model: ExperiencesModel): OnContinueRequest =
    new OnContinueRequest(model.experiences.toList.map(ExperienceForm(_)))
