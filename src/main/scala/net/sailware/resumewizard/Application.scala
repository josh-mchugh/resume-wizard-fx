package net.sailware.resumewizard

import net.sailware.resumewizard.view.core.PageType
import net.sailware.resumewizard.view.core.State
import net.sailware.resumewizard.view.main.MainController
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.slf4j.LoggerFactory
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.stage.Stage

object Main extends JFXApp3:

  val logger = LoggerFactory.getLogger(classOf[Main.type])

  val stateProp = ObjectProperty(new State())

  EventBus.getDefault().register(this)

  override def start(): Unit =
    stage = new PrimaryStage:
      onCloseRequest = () => EventBus.getDefault().unregister(this)
      scene = new Scene(1280, 768):
        stylesheets = List("styles.css")
        root = new MainController(stateProp).view()

  @Subscribe
  def onPageTypeEvent(pageType: PageType): Unit =
    val currentState = stateProp.value
    logger.info("Updating current page type from: {} to: {}", currentState.currentPageType, pageType)
    stateProp.update(currentState.copy(currentPageType = pageType))
