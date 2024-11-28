package net.sailware.resumewizard.resume.model

case class CreateCertificationsRequest(
  val certifications: List[CreateCertification]
)

object CreateCertificationsRequest:

  def apply(certifications: List[CreateCertification]): CreateCertificationsRequest =
    new CreateCertificationsRequest(certifications)
