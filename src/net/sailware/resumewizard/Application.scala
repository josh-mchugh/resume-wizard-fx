package net.sailware.resumewizard

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.slf4j.LoggerFactory
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.BooleanProperty
import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.geometry.VPos
import scalafx.scene.Node
import scalafx.scene.Parent
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.scene.control.TextArea
import scalafx.scene.control.TextField
import scalafx.scene.control.ScrollPane
import scalafx.scene.control.ScrollPane.ScrollBarPolicy
import scalafx.scene.layout.AnchorPane
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.HBox
import scalafx.scene.layout.Priority
import scalafx.scene.layout.Region
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.VBox
import scalafx.scene.text.Font
import scalafx.scene.text.FontWeight
import scalafx.scene.text.Text
import scalafx.scene.text.TextFlow
import scalafx.stage.Stage

import net.sailware.resumewizard.view.dashboard.DashboardController
import net.sailware.resumewizard.view.main.MainController
import net.sailware.resumewizard.view.resume.create.CreateResumeController
import net.sailware.resumewizard.view.resume.create.service.CreateResumeService
import net.sailware.resumewizard.view.resume.create.service.CreateResumeServiceImpl
import net.sailware.resumewizard.view.resume.wizard.certification.CertificationsController
import net.sailware.resumewizard.view.resume.wizard.contact.ContactDetailsController
import net.sailware.resumewizard.view.resume.wizard.experience.ExperiencesController
import net.sailware.resumewizard.view.resume.wizard.personal.PersonalDetailsController
import net.sailware.resumewizard.view.resume.wizard.social.SocialsController

enum PageType:
  case Dashboard
  case CreateResume
  case PersonalDetails
  case ContactDetails
  case Socials
  case Experiences
  case Certifications

case class State(val currentPageType: PageType = PageType.Dashboard)

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

/*
  Screen Components
 */
trait Controller[T]:
  def view(): T

class PageFactory(val createResumeService: CreateResumeService):
  def createPage(pageType: PageType): List[Node] =
    List(createView(pageType))

  private def createView(pageType: PageType): Node =
    pageType match
      case PageType.Dashboard => new DashboardController().view()
      case PageType.CreateResume => new CreateResumeController(createResumeService).view()
      case PageType.PersonalDetails => new PersonalDetailsController().view()
      case PageType.ContactDetails => new ContactDetailsController().view()
      case PageType.Socials => new SocialsController().view()
      case PageType.Experiences => new ExperiencesController().view()
      case PageType.Certifications => new CertificationsController().view()
