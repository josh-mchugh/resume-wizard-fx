package net.sailware.resumewizard.view.resume.wizard.personal

import net.sailware.resumewizard.view.core.Controller
import scalafx.scene.layout.Region

class PersonalDetailsController() extends Controller[Region]:
  val model = new PersonalDetailsModel()
  val wizardPresenter = new PersonalDetailsPresenterImpl(model)
  val wizardView = new PersonalDetailsViewImpl(wizardPresenter, model)

  def view(): Region =
    wizardView.view()
