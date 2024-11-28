package net.sailware.resumewizard.resume.model

case class CreatePersonalRequest(
    val name: String,
    val title: String,
    val summary: String
)

object CreatePersonalRequest:

  def apply(name: String, title: String, summary: String): CreatePersonalRequest =
    new CreatePersonalRequest(name, title, summary)
