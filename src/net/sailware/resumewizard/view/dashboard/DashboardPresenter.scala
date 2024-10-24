package net.sailware.resumewizard.view.dashboard

/**
  * Dashboard presenter trait for user interaction with the Dashboard view.
  *
  * author Josh McHugh
  */
trait DashboardPresenter:

  /**
    * Handles the user action when triggering the New Resume Button.
    */
  def onNewResumeButtonAction(): Unit
