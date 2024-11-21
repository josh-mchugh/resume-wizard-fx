package net.sailware.resumewizard.view.resume.wizard.social

import net.sailware.resumewizard.view.resume.wizard.social.service.SocialsService
import net.sailware.resumewizard.view.resume.wizard.social.service.model.OnContinueRequest
import net.sailware.resumewizard.view.core.PageType
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scalafx.application.Platform

class SocialsPresenterImpl(
    val model: SocialsModel,
    val service: SocialsService
) extends SocialsPresenter:

  val logger = LoggerFactory.getLogger(classOf[SocialsPresenterImpl])

  override def onContinue(): Unit =
    val socials = model.socials.toList.map(social => (social.name(), social.url()))
    service
      .onContinue(OnContinueRequest(socials))
      .onComplete:
        case Success(response) =>
          logger.info("resume: {}", response.resume)
          Platform.runLater(() => EventBus.getDefault().post(PageType.Experiences))
        case Failure(t) => logger.error("Failed to continue resume wizard.", t)
