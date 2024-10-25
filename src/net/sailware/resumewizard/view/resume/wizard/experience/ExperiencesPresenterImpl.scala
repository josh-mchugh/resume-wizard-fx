package net.sailware.resumewizard.view.resume.wizard.experience

import net.sailware.resumewizard.PageType
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class ExperiencesPresenterImpl(val model: ExperiencesModel) extends ExperiencesPresenter:
  val logger = LoggerFactory.getLogger(classOf[ExperiencesPresenterImpl])

  override def onContinue(): Unit =
    EventBus.getDefault().post(PageType.Certifications)
