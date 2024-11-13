package net.sailware.resumewizard.view.resume.wizard.social

import scalafx.collections.ObservableBuffer

case class SocialsModel(
  val socials: ObservableBuffer[SocialModel] = ObservableBuffer(new SocialModel())
)
