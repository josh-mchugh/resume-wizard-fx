package net.sailware.resumewizard.view.resume.wizard.experience

import net.sailware.resumewizard.view.core.PageType
import net.sailware.resumewizard.view.resume.wizard.experience.service.ExperiencesServiceImpl
import org.greenrobot.eventbus.EventBus
import org.slf4j.LoggerFactory

class ExperiencesPresenterImpl(
    val model: ExperiencesModel,
    val service: ExperiencesServiceImpl
) extends ExperiencesPresenter:

  val logger = LoggerFactory.getLogger(classOf[ExperiencesPresenterImpl])

  override def onContinue(): Unit =
    val experienceTuples =
      model.experiences.toList.map(experience => (experience.title(), experience.organization(), experience.duration(), experience.location(), experience.description(), experience.skills()))
    service.handleExperiencesUpdate(experienceTuples)
    EventBus.getDefault().post(PageType.Certifications)
