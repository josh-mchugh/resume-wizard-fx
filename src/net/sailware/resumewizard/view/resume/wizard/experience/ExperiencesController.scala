package net.sailware.resumewizard.view.resume.wizard.experience

import net.sailware.resumewizard.Controller
import scalafx.scene.layout.Region

class ExperiencesController() extends Controller[Region]:
  val model = new ExperiencesModel()
  val experiencesPresenter = new ExperiencesPresenterImpl(model)
  val experiencesView = new ExperiencesViewImpl(experiencesPresenter, model)

  override def view(): Region =
    experiencesView.view()
