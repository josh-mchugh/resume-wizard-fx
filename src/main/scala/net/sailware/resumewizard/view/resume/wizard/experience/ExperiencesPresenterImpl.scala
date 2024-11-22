package net.sailware.resumewizard.view.resume.wizard.experience

import net.sailware.resumewizard.view.core.PageType
import net.sailware.resumewizard.view.resume.wizard.experience.service.ExperiencesServiceImpl
import net.sailware.resumewizard.view.resume.wizard.experience.service.model.OnContinueRequest
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure
import scalafx.application.Platform

class ExperiencesPresenterImpl(
    val model: ExperiencesModel,
    val service: ExperiencesServiceImpl
) extends ExperiencesPresenter:

  val logger = LoggerFactory.getLogger(classOf[ExperiencesPresenterImpl])

  override def onContinue(): Unit =
    service
      .onContinue(OnContinueRequest(model))
      .onComplete:
        case Success(response) =>
          logger.info("resume: '{}'", response.resume)
          Platform.runLater(() => EventBus.getDefault().post(PageType.Certifications))
        case Failure(t)      => logger.error("Failed to continue resume wizard.", t)
