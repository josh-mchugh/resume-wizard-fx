package net.sailware.resumewizard.view.resume.create

import net.sailware.resumewizard.view.core.Controller
import net.sailware.resumewizard.view.resume.create.service.CreateResumeService
import scalafx.scene.layout.Region

class CreateResumeController(val createResumeService: CreateResumeService) extends Controller[Region]:
  val model = new CreateResumeModel()
  val createResumePresenter = new CreateResumePresenterImpl(model, createResumeService)
  val createResumeView = new CreateResumeViewImpl(createResumePresenter, model)

  override def view(): Region =
    createResumeView.view()
