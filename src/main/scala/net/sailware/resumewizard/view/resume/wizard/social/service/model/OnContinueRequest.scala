package net.sailware.resumewizard.view.resume.wizard.social.service.model

case class OnContinueRequest(
    val socials: List[(String, String)]
)

object OnContinueRequest:

  def apply(socials: List[(String, String)]): OnContinueRequest =
    new OnContinueRequest(socials)
