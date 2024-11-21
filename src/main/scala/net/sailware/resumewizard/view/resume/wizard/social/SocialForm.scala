package net.sailware.resumewizard.view.resume.wizard.social

case class SocialForm(
    val name: String,
    val url: String
)

object SocialForm:

  def apply(model: SocialFormModel): SocialForm =
    new SocialForm(model.name(), model.url())
