package net.sailware.resumewizard.resume.model

case class CreateCertification(
    val title: String,
    val organization: String,
    val duration: String,
    val location: String
)

object CreateCertification:

  def apply(title: String, organization: String, duration: String, location: String): CreateCertification =
    new CreateCertification(title, organization, duration, location)
