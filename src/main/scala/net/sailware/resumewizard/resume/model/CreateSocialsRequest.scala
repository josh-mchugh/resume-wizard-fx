package net.sailware.resumewizard.resume.model

case class CreateSocialsRequest(
    val socials: List[CreateSocial]
)

object CreateSocialsRequest:

  def apply(socials: List[CreateSocial]): CreateSocialsRequest =
    new CreateSocialsRequest(socials)
