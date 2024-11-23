package net.sailware.resumewizard.view.resume.wizard.certification.service.model

import net.sailware.resumewizard.view.resume.wizard.certification.CertificationsModel

case class OnContinueRequest(
    certifications: List[CertificationForm]
):

  def toTuple: List[(String, String, String, String)] =
    certifications.map(certification => (certification.title, certification.organization, certification.duration, certification.location))

object OnContinueRequest:

  def apply(model: CertificationsModel): OnContinueRequest =
    new OnContinueRequest(model.certifications.toList.map(CertificationForm(_)))
