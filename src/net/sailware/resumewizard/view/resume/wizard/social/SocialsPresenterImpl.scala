package net.sailware.resumewizard.view.resume.wizard.social

import net.sailware.resumewizard.view.core.PageType
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class SocialsPresenterImpl(val model: SocialsModel) extends SocialsPresenter:
  val logger = LoggerFactory.getLogger(classOf[SocialsPresenterImpl])

  override def onContinue(): Unit =
    EventBus.getDefault().post(PageType.Experiences)
