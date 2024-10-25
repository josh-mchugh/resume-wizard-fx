package net.sailware.resumewizard.view.resume.wizard.social

import net.sailware.resumewizard.view.core.Controller
import scalafx.scene.layout.Region

class SocialsController() extends Controller[Region]:
  val model = new SocialsModel()
  val socialsPresenter = new SocialsPresenterImpl(model)
  val socialsView = new SocialsViewImpl(socialsPresenter, model)

  override def view(): Region =
    socialsView.view()
