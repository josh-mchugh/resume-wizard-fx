package net.sailware.resumewizard.view.resume.wizard.contact

import net.sailware.resumewizard.view.core.PageType
import net.sailware.resumewizard.view.resume.wizard.contact.service.ContactDetailsService
import net.sailware.resumewizard.view.resume.wizard.contact.service.model.OnContinueRequest
import net.sailware.resumewizard.view.resume.wizard.contact.service.model.OnContinueResponse
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scalafx.application.Platform

class ContactDetailsPresenterImpl(
    val model: ContactDetailsModel,
    val service: ContactDetailsService
) extends ContactDetailsPresenter:

  val logger = LoggerFactory.getLogger(classOf[ContactDetailsPresenterImpl])

  override def onContinue(): Unit =
    service
      .onContinue(OnContinueRequest(model))
      .onComplete:
        case Success(response) =>
          logger.info("resume: '{}'", response.resume)
          Platform.runLater(() => EventBus.getDefault().post(PageType.Socials))
        case Failure(t) => logger.error("Failed to continue resume wizard.", t)
