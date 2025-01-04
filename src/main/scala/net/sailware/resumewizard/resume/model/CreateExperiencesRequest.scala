package net.sailware.resumewizard.resume.model

case class CreateExperiencesRequest(
    experiences: List[CreateExperience]
)

object CreateExperiencesRequest:

  def apply(experiences: List[CreateExperience]): CreateExperiencesRequest =
    new CreateExperiencesRequest(experiences)
