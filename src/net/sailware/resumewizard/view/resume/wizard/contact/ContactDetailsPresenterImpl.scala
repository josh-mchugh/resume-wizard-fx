package net.sailware.resumewizard.view.resume.wizard.contact

import net.sailware.resumewizard.view.core.PageType
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class ContactDetailsPresenterImpl(val model: ContactDetailsModel) extends ContactDetailsPresenter:
  val logger = LoggerFactory.getLogger(classOf[ContactDetailsPresenterImpl])

  override def onContinue(): Unit =
    EventBus.getDefault().post(PageType.Socials)
