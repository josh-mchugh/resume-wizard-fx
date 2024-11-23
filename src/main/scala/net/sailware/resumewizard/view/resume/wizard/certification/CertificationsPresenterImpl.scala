package net.sailware.resumewizard.view.resume.wizard.certification

import net.sailware.resumewizard.view.core.PageType
import net.sailware.resumewizard.view.resume.wizard.certification.service.CertificationsService
import net.sailware.resumewizard.view.resume.wizard.certification.service.model.OnContinueRequest
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scalafx.application.Platform

class CertificationsPresenterImpl(
    val model: CertificationsModel,
    val service: CertificationsService
) extends CertificationsPresenter:

  val logger = LoggerFactory.getLogger(classOf[CertificationsPresenterImpl])

  override def onContinue(): Unit =
    service
      .onContinue(OnContinueRequest(model))
      .onComplete:
        case Success(response) =>
          logger.info("resume: '{}'", response.resume)
          Platform.runLater(() => EventBus.getDefault().post(PageType.Preview))
        case Failure(t) => logger.error("Failed to continue resume wizard.", t)
