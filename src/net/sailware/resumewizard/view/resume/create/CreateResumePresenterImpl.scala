package net.sailware.resumewizard.view.resume.create

import net.sailware.resumewizard.PageType
import net.sailware.resumewizard.view.resume.create.service.CreateResumeService
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class CreateResumePresenterImpl(val model: CreateResumeModel, val createResumeService: CreateResumeService) extends CreateResumePresenter:
  val logger = LoggerFactory.getLogger(classOf[CreateResumePresenterImpl])

  override def onCreateForm(): Unit =
    createResumeService.createResume(model.name.value)
    EventBus.getDefault().post(PageType.PersonalDetails)
