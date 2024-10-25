package net.sailware.resumewizard.view.resume.wizard.certification

import org.slf4j.LoggerFactory

class CertificationsPresenterImpl(val model: CertificationsModel) extends CertificationsPresenter:
  val logger = LoggerFactory.getLogger(classOf[CertificationsPresenterImpl])

  override def onContinue(): Unit =
    logger.info("on continue clicked...")
