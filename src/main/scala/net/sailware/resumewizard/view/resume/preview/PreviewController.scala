package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.core.Controller
import net.sailware.resumewizard.view.resume.preview.service.PreviewServiceImpl
import scalafx.scene.Node

class PreviewController(
    val resumeService: ResumeService,
) extends Controller[Node]:

  val service = new PreviewServiceImpl(resumeService)

  val model = new PreviewModel()
  val previewPresenter = new PreviewPresenterImpl(model, service)
  val previewView = new PreviewViewImpl(model)

  override def view(): Node =
    previewPresenter.generateResumePDF()
    previewView.view()
