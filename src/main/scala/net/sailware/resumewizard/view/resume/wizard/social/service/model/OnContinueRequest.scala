package net.sailware.resumewizard.view.resume.wizard.social.service.model

import net.sailware.resumewizard.view.resume.wizard.social.SocialForm
import net.sailware.resumewizard.view.resume.wizard.social.SocialsModel

case class OnContinueRequest(
    val socials: List[SocialForm]
):

  def toTuple: List[(String, String)] =
    socials.map(social => (social.name, social.url))

object OnContinueRequest:

  def apply(model: SocialsModel): OnContinueRequest =
    new OnContinueRequest(model.socials.toList.map(SocialForm(_)))
