package net.sailware.resumewizard.view.resume.wizard.certification

import net.sailware.resumewizard.view.core.Controller
import scalafx.scene.layout.Region

class CertificationsController() extends Controller[Region]:
  val model = new CertificationsModel()
  val certificationsPresenter = new CertificationsPresenterImpl(model)
  val certificationsView = new CertificationsViewImpl(certificationsPresenter, model)

  override def view(): Region =
    certificationsView.view()
