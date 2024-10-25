package net.sailware.resumewizard.view.resume.wizard.personal

import net.sailware.resumewizard.view.core.PageType
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class PersonalDetailsPresenterImpl(val model: PersonalDetailsModel) extends PersonalDetailsPresenter:
  val logger = LoggerFactory.getLogger(classOf[PersonalDetailsPresenterImpl])

  override def onContinue(): Unit =
    EventBus.getDefault().post(PageType.ContactDetails)
