package net.sailware.resumewizard.view.resume.wizard.personal

import net.sailware.resumewizard.view.resume.wizard.personal.service.PersonalDetailsService
import net.sailware.resumewizard.view.resume.wizard.personal.service.model.OnContinueRequest
import net.sailware.resumewizard.view.core.PageType
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scalafx.application.Platform

class PersonalDetailsPresenterImpl(
    val model: PersonalDetailsModel,
    val service: PersonalDetailsService
) extends PersonalDetailsPresenter:

  val logger = LoggerFactory.getLogger(classOf[PersonalDetailsPresenterImpl])

  override def onContinue(): Unit =
    service
      .onPersonalDetailsSave(OnContinueRequest(model))
      .onComplete:
        case Success(response) =>
          logger.info("resume: '{}'", response.resume)
          Platform.runLater(() => EventBus.getDefault().post(PageType.ContactDetails))
        case Failure(t) => logger.error("Failed to continue resume wizard.", t)
