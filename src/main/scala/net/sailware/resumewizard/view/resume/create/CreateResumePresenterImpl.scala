package net.sailware.resumewizard.view.resume.create

import net.sailware.resumewizard.view.core.PageType
import net.sailware.resumewizard.view.resume.create.service.CreateResumeService
import net.sailware.resumewizard.view.resume.create.service.model.OnCreateResumeRequest
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scalafx.application.Platform

class CreateResumePresenterImpl(
    val model: CreateResumeModel,
    val service: CreateResumeService
) extends CreateResumePresenter:

  val logger = LoggerFactory.getLogger(classOf[CreateResumePresenterImpl])

  override def onCreateForm(): Unit =
    service
      .onCreateResume(OnCreateResumeRequest(model))
      .onComplete:
        case Success(response) =>
          logger.info("new resume: {}", response.resume)
          Platform.runLater(() => EventBus.getDefault().post(PageType.PersonalDetails))
        case Failure(t) => logger.error("Failed to create resume.", t)
