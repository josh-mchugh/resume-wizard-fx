package net.sailware.resumewizard.view.dashboard

import net.sailware.resumewizard.Controller

import scalafx.scene.Node

/**
  * DashboardController is the controller for the Dashboard page component.
  * The controller handles the creation of the presenter and view.
  *
  * Dashboard component allows for the user to manage their resumes.
  *
  * author Josh McHugh
  */
class DashboardController extends Controller[Node]:
  val dashboardPresenter = new DashboardPresenterImpl()
  val dashboardView = new DashboardViewImpl(dashboardPresenter)

  /**
    * Returns the Dashboard component view
    *
    * return dashboard view node
    */
  override def view(): Node =
    dashboardView.view()
