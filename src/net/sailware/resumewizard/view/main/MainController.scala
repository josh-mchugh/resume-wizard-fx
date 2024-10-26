package net.sailware.resumewizard.view.main

import net.sailware.resumewizard.resume.ResumeServiceImpl
import net.sailware.resumewizard.view.core.Controller
import net.sailware.resumewizard.view.core.PageFactory
import net.sailware.resumewizard.view.core.State
import net.sailware.resumewizard.view.resume.create.service.CreateResumeServiceImpl
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Parent

class MainController(val state: ObjectProperty[State]) extends Controller[Parent]:
  val resumeService = new ResumeServiceImpl()
  val pageFactory = PageFactory(new CreateResumeServiceImpl(resumeService))

  val mainPresenter = new MainPresenterImpl()
  val mainView = new MainViewImpl(mainPresenter, state, pageFactory)

  override def view(): Parent =
    mainView.view()
