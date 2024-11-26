package net.sailware.resumewizard.resume.model

case class CreateResumePersonalRequest(
    val name: String,
    val title: String,
    val summary: String
)

object CreateResumePersonalRequest:

  def apply(name: String, title: String, summary: String): CreateResumePersonalRequest =
    new CreateResumePersonalRequest(name, title, summary)
