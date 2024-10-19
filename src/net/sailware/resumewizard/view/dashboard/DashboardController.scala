package net.sailware.resumewizard.view.dashboard

import net.sailware.resumewizard.Controller

import scalafx.scene.Node

class DashboardController extends Controller[Node]:
  val dashboardPresenter = new DashboardPresenterImpl()
  val dashboardView = new DashboardViewImpl(dashboardPresenter)

  override def view(): Node =
    dashboardView.view()
