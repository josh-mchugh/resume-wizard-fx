package net.sailware.resumewizard.view.resume.wizard.certification.service.model

import net.sailware.resumewizard.view.resume.wizard.certification.CertificationFormModel

case class CertificationForm(
    val title: String,
    val organization: String,
    val duration: String,
    val location: String
)

object CertificationForm:

  def apply(model: CertificationFormModel): CertificationForm =
    new CertificationForm(model.title(), model.organization(), model.duration(), model.location())
