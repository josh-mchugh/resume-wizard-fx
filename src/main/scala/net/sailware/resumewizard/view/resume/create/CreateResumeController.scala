package net.sailware.resumewizard.view.resume.create

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.core.Controller
import net.sailware.resumewizard.view.resume.create.service.CreateResumeServiceImpl
import scalafx.scene.layout.Region

class CreateResumeController(
    val resumeService: ResumeService
) extends Controller[Region]:

  val model = new CreateResumeModel()
  val createResumePresenter = new CreateResumePresenterImpl(model, new CreateResumeServiceImpl(resumeService))
  val createResumeView = new CreateResumeViewImpl(createResumePresenter, model)

  override def view(): Region =
    createResumeView.view()
