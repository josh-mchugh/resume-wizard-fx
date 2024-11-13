package net.sailware.resumewizard.view.resume.wizard.certification

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.core.Controller
import net.sailware.resumewizard.view.resume.wizard.certification.service.CertificationsServiceImpl
import scalafx.scene.layout.Region

class CertificationsController(val resumeService: ResumeService) extends Controller[Region]:
  val model = new CertificationsModel()
  val certificationsPresenter = new CertificationsPresenterImpl(model, new CertificationsServiceImpl(resumeService))
  val certificationsView = new CertificationsViewImpl(certificationsPresenter, model)

  override def view(): Region =
    certificationsView.view()
