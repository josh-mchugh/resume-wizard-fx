package net.sailware.resumewizard.view.resume.wizard.social

import scalafx.collections.ObservableBuffer

case class SocialsModel(
    val socials: ObservableBuffer[SocialFormModel] = ObservableBuffer(new SocialFormModel())
)
