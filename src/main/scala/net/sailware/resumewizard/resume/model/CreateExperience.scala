package net.sailware.resumewizard.resume.model

case class CreateExperience(
    val title: String,
    val organization: String,
    val duration: String,
    val location: String,
    val description: String,
    val skills: String
)

object CreateExperience:

  def apply(title: String, organization: String, duration: String, location: String, description: String, skills: String): CreateExperience =
    new CreateExperience(title, organization, duration, location, description, skills)
