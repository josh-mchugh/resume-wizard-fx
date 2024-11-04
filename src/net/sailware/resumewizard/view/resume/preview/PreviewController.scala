package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.core.Controller
import scalafx.scene.Node

class PreviewController(val resumeService: ResumeService) extends Controller[Node]:
  val model = new PreviewModel()
  val previewPresenter = new PreviewPresenterImpl(model, resumeService)
  val previewView = new PreviewViewImpl(model)

  override def view(): Node =
    previewView.view()
