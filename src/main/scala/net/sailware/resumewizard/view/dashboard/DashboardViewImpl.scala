package net.sailware.resumewizard.view.dashboard

import scalafx.Includes.*
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.Button

class DashboardViewImpl(val presenter: DashboardPresenter) extends DashboardView:

  override def view(): Node =
    createNewResumeButton()

  private def createNewResumeButton(): Button =
    new Button("Create Resume"):
      onAction = (event: ActionEvent) => presenter.onNewResumeButtonAction()
