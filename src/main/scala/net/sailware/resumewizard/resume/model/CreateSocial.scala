package net.sailware.resumewizard.resume.model

case class CreateSocial(
  val name: String,
  val url: String
)

object CreateSocial:

  def apply(name: String, url: String): CreateSocial =
    new CreateSocial(name, url)
