package net.sailware.resumewizard.resume.model

case class CreateResumeRequest(
    val name: String
)

object CreateResumeRequest:

  def apply(name: String): CreateResumeRequest =
    new CreateResumeRequest(name)
