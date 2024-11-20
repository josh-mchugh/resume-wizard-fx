package net.sailware.resumewizard.view.resume.wizard.experience

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.core.Controller
import net.sailware.resumewizard.view.resume.wizard.experience.service.ExperiencesServiceImpl
import scalafx.scene.layout.Region

class ExperiencesController(
    val resumeService: ResumeService
) extends Controller[Region]:

  val model = new ExperiencesModel()
  val experiencesPresenter = new ExperiencesPresenterImpl(model, new ExperiencesServiceImpl(resumeService))
  val experiencesView = new ExperiencesViewImpl(experiencesPresenter, model)

  override def view(): Region =
    experiencesView.view()
