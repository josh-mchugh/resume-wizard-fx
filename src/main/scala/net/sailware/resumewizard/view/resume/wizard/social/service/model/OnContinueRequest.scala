package net.sailware.resumewizard.view.resume.wizard.social.service.model

import net.sailware.resumewizard.resume.model.CreateSocial
import net.sailware.resumewizard.resume.model.CreateSocialsRequest
import net.sailware.resumewizard.view.resume.wizard.social.SocialsModel

case class OnContinueRequest(
    val socials: List[SocialForm]
):

  def toCreateSocialsRequest: CreateSocialsRequest =
    CreateSocialsRequest(socials.map(social => CreateSocial(social.name, social.url)))

object OnContinueRequest:

  def apply(model: SocialsModel): OnContinueRequest =
    new OnContinueRequest(model.socials.toList.map(SocialForm(_)))
