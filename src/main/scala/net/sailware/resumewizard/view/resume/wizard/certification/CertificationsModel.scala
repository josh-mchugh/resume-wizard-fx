package net.sailware.resumewizard.view.resume.wizard.certification

import scalafx.collections.ObservableBuffer

case class CertificationsModel(
    val certifications: ObservableBuffer[CertificationFormModel] = ObservableBuffer(new CertificationFormModel())
)
