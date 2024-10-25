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

/*
  Certifications
 */
case class CertificationModel(
  val title: StringProperty = StringProperty(""),
  val organization: StringProperty = StringProperty(""),
  val location: StringProperty = StringProperty(""),
  val year: StringProperty = StringProperty(""),
)

case class CertificationsModel(
  val certifications: ObservableBuffer[CertificationModel] = ObservableBuffer(new CertificationModel())
)

trait CertificationsPresenter:
  def onContinue(): Unit
trait CertificationsView:
  def view(): Region

class CertificationsController() extends Controller[Region]:
  val model = new CertificationsModel()
  val certificationsPresenter = new CertificationsPresenterImpl(model)
  val certificationsView = new CertificationsViewImpl(certificationsPresenter, model)

  override def view(): Region =
    certificationsView.view()

class CertificationsPresenterImpl(val model: CertificationsModel) extends CertificationsPresenter:
  val logger = LoggerFactory.getLogger(classOf[CertificationsPresenterImpl])

  override def onContinue(): Unit =
    logger.info("on continue clicked...")

class CertificationsViewImpl(val presenter: CertificationsPresenter, val model: CertificationsModel) extends CertificationsView:
  override def view(): Region =
    val content = List(
      ComponentUtil.createPageHeader(
        "Certifications",
        createContinueButton()
      ),
      new VBox {
        children = model.certifications.map(certification => createCertificationSection(certification)).flatten
        model.certifications.onInvalidate { (newValue) =>
          children = model.certifications.map(certification => createCertificationSection(certification)).flatten
        }
      },
      new Button("Add Certification") {
        onAction = (event: ActionEvent) => model.certifications += new CertificationModel()
      },
    )

    ComponentUtil.createContentPage(content)

  private def createContinueButton(): List[Region] =
    val button = new Button("Continue"):
      onAction = (event: ActionEvent) => presenter.onContinue()

    List(
      new HBox:
        alignment = Pos.TopRight
        children = button
    )

  private def createCertificationSection(certification: CertificationModel): List[Node] =
    List(
      new Label("Title"),
      new TextField {
        text <==> certification.title
      },
      new Label("Organization Name"),
      new TextField {
        text <==> certification.organization
      },
      new Label("Location"),
      new TextField {
        text <==> certification.location
      },
      new Label("Year"),
      new TextField {
        text <==> certification.year
      },
    )
/*
  Component Util
*/
object ComponentUtil:

  def createContentPage(content: List[Node]): VBox =
    new VBox(5):
      styleClass = List("page")
      maxWidth = 1020
      children = content

  def createPageHeader(title: String, content: List[Node]): VBox =
    val titleBox = new HBox:
      styleClass = List("page-header__content")
      alignment = Pos.Center
      children = new Text(title):
        styleClass = List("header")

    new VBox:
      styleClass = List("page-header")
      children = titleBox :: content
