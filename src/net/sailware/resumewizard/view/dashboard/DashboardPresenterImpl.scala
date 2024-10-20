package net.sailware.resumewizard.view.dashboard

import net.sailware.resumewizard.PageType

import org.greenrobot.eventbus.EventBus

class DashboardPresenterImpl extends DashboardPresenter:

  override def onNewResumeButtonAction(): Unit =
    EventBus.getDefault().post(PageType.CreateResume)
