package net.sailware.resumewizard.view.resume.wizard.experience

case class ExperienceForm(
  val title: String,
  val organization: String,
  val duration: String,
  val location: String,
  val description: String,
  val skills: String
)

object ExperienceForm:

  def apply(model: ExperienceFormModel): ExperienceForm =
    new ExperienceForm(model.title(), model.organization(), model.duration(), model.location(), model.description(), model.skills())
