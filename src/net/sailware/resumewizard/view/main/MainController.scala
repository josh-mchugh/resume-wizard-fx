package net.sailware.resumewizard.view.main

import net.sailware.resumewizard.Controller
import net.sailware.resumewizard.PageFactory
import net.sailware.resumewizard.State
import net.sailware.resumewizard.view.resume.create.service.CreateResumeServiceImpl
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Parent

class MainController(val state: ObjectProperty[State]) extends Controller[Parent]:
  val pageFactory = PageFactory(new CreateResumeServiceImpl())

  val mainPresenter = new MainPresenterImpl()
  val mainView = new MainViewImpl(mainPresenter, state, pageFactory)

  override def view(): Parent =
    mainView.view()
