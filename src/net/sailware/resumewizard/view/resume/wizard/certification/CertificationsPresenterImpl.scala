package net.sailware.resumewizard.view.resume.wizard.certification

import net.sailware.resumewizard.view.core.PageType
import net.sailware.resumewizard.view.resume.wizard.certification.service.CertificationsService
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class CertificationsPresenterImpl(
  val model: CertificationsModel,
  val service: CertificationsService
) extends CertificationsPresenter:
  val logger = LoggerFactory.getLogger(classOf[CertificationsPresenterImpl])

  override def onContinue(): Unit =
    val certificationTuples = model.certifications.toList.map(certification => (certification.title(), certification.organization(), certification.duration(), certification.location()))
    service.handleCertificationsUpdate(certificationTuples)
    EventBus.getDefault().post(PageType.Preview)
