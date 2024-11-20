package net.sailware.resumewizard.view.resume.wizard.personal

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.core.Controller
import net.sailware.resumewizard.view.resume.wizard.personal.service.PersonalDetailsServiceImpl
import scalafx.scene.layout.Region

class PersonalDetailsController(
    val resumeService: ResumeService
) extends Controller[Region]:

  val model = new PersonalDetailsModel()
  val wizardPresenter = new PersonalDetailsPresenterImpl(model, new PersonalDetailsServiceImpl(resumeService))
  val wizardView = new PersonalDetailsViewImpl(wizardPresenter, model)

  def view(): Region =
    wizardView.view()
