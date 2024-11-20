package net.sailware.resumewizard.view.resume.wizard.social

import net.sailware.resumewizard.view.resume.wizard.social.service.SocialsService
import net.sailware.resumewizard.view.core.PageType
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class SocialsPresenterImpl(
    val model: SocialsModel,
    val service: SocialsService
) extends SocialsPresenter:

  val logger = LoggerFactory.getLogger(classOf[SocialsPresenterImpl])

  override def onContinue(): Unit =
    val socialTuples = model.socials.toList.map(social => (social.name(), social.url()))
    service.handleSocialsUpdate(socialTuples)
    EventBus.getDefault().post(PageType.Experiences)
