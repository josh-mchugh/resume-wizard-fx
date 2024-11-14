package net.sailware.resumewizard.view.resume.wizard.contact

import net.sailware.resumewizard.view.core.PageType
import net.sailware.resumewizard.view.resume.wizard.contact.service.ContactDetailsService
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class ContactDetailsPresenterImpl(
    val model: ContactDetailsModel,
    val service: ContactDetailsService
) extends ContactDetailsPresenter:
  val logger = LoggerFactory.getLogger(classOf[ContactDetailsPresenterImpl])

  override def onContinue(): Unit =
    service.handleContactDetailsUpdate(model.phone.get(), model.email.get(), model.location.get())
    EventBus.getDefault().post(PageType.Socials)
