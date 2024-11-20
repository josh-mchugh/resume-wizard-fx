package net.sailware.resumewizard.view.resume.wizard.social

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.core.Controller
import net.sailware.resumewizard.view.resume.wizard.social.service.SocialsServiceImpl
import scalafx.scene.layout.Region

class SocialsController(
    val resumeService: ResumeService
) extends Controller[Region]:

  val model = new SocialsModel()
  val socialsPresenter = new SocialsPresenterImpl(model, new SocialsServiceImpl(resumeService))
  val socialsView = new SocialsViewImpl(socialsPresenter, model)

  override def view(): Region =
    socialsView.view()
