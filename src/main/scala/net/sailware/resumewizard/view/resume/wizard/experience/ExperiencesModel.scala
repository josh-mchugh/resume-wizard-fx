package net.sailware.resumewizard.view.resume.wizard.experience

import scalafx.collections.ObservableBuffer

case class ExperiencesModel(
    val experiences: ObservableBuffer[ExperienceModel] = ObservableBuffer(new ExperienceModel())
)
