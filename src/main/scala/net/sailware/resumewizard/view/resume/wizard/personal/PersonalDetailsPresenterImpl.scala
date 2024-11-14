package net.sailware.resumewizard.view.resume.wizard.personal

import net.sailware.resumewizard.view.resume.wizard.personal.service.PersonalDetailsService
import net.sailware.resumewizard.view.core.PageType
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class PersonalDetailsPresenterImpl(
    val model: PersonalDetailsModel,
    val service: PersonalDetailsService
) extends PersonalDetailsPresenter:

  val logger = LoggerFactory.getLogger(classOf[PersonalDetailsPresenterImpl])

  override def onContinue(): Unit =
    service.onPersonalDetailsSave(model.name.get(), model.title.get(), model.summary.get())
    EventBus.getDefault().post(PageType.ContactDetails)
