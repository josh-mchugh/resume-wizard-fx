package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.core.Controller
import scalafx.scene.web.WebView

class PreviewController(val resumeService: ResumeService) extends Controller[WebView]:
  val model = new PreviewModel()
  val previewPresenter = new PreviewPresenterImpl(model, resumeService)
  val previewView = new PreviewViewImpl(model)

  override def view(): WebView =
    previewView.view()
