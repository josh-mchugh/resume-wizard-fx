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
import scalafx.scene.layout.Priority
import scalafx.scene.layout.Region
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.VBox
import scalafx.scene.text.{Font, FontWeight, Text, TextFlow}
import scalafx.stage.Stage

enum PageType:
  case Dashboard
  case NewResume
  case PersonalDetails
  case ContactDetails
  case Socials

case class State(val currentPageType: PageType = PageType.Dashboard)

object Main extends JFXApp3:

  val logger = LoggerFactory.getLogger(classOf[Main.type])

  val stateProp = ObjectProperty(new State())

  EventBus.getDefault().register(this)

  override def start(): Unit =
    stage = new PrimaryStage:
      onCloseRequest = () => EventBus.getDefault().unregister(this)
      scene = new Scene(1280, 768):
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

object PageFactory:
  def createPage(pageType: PageType): List[Node] =
    List(createView(pageType))

  private def createView(pageType: PageType): Node =
    pageType match
      case PageType.Dashboard => new DashboardController().view()
      case PageType.NewResume => new NewResumeController().view()
      case PageType.PersonalDetails => new PersonalDetailsController().view()
      case PageType.ContactDetails => new ContactDetailsController().view()
      case PageType.Socials => new SocialsController().view()

/*
  Main Components
 */
class MainModel { }

class MainController(val state: ObjectProperty[State]) extends Controller[Parent]:
  val mainPresenter = new MainPresenterImpl()
  val mainView = new MainViewImpl(mainPresenter, state)

  override def view(): Parent =
    mainView.view()

trait MainPresenter {}
trait MainView:
  def view(): Region

class MainPresenterImpl extends MainPresenter { }

class MainViewImpl(
  val presenter: MainPresenter,
  val state: ObjectProperty[State]
) extends MainView:

  override def view(): Region =
    createRootPane

  private def createRootPane =
    new AnchorPane:
      children = createBasePane

  private def createBasePane =
    val result = new StackPane:
      children = createBorderFramePane
    AnchorPane.setTopAnchor(result, 0.0)
    AnchorPane.setLeftAnchor(result, 0.0)
    AnchorPane.setBottomAnchor(result, 0.0)
    AnchorPane.setRightAnchor(result, 0.0)
    result

  private def createBorderFramePane =
    new BorderPane:
      top = createTopNavigation
      center = createMainContent

  private def createTopNavigation =
    new Text("Resume Wizard Top"):
      textOrigin = VPos.Top
      font = Font.font(null, FontWeight.Bold, 18)

  private def createMainContent =
    new ScrollPane:
      vbarPolicy = ScrollBarPolicy.AS_NEEDED
      fitToHeight = true
      hbarPolicy = ScrollBarPolicy.NEVER
      fitToWidth = true
      content = new StackPane:
        style = "-fx-padding: 20;"
        children = PageFactory.createPage(PageType.Dashboard)
        state.onChange ({
           children = PageFactory.createPage(state.value.currentPageType)
        })

/*
  Dashboard Components
*/
trait DashboardPresenter:
  def onNewResumeButtonAction(): Unit
trait DashboardView:
  def view(): Node

class DashboardController extends Controller[Node]:
  val dashboardPresenter = new DashboardPresenterImpl()
  val dashboardView = new DashboardViewImpl(dashboardPresenter)

  override def view(): Node =
    dashboardView.view()

class DashboardPresenterImpl extends DashboardPresenter:
  override def onNewResumeButtonAction(): Unit =
    EventBus.getDefault().post(PageType.NewResume)

class DashboardViewImpl(
  val presenter: DashboardPresenter
) extends DashboardView:

  override def view(): Node =
    createNewResumeButton

  private def createNewResumeButton =
    new Button("Create Resume"):
      onAction = (event: ActionEvent) => presenter.onNewResumeButtonAction()

/*
  New Resume Components
 */
case class NewResumeModel(
  val name: StringProperty = StringProperty("")
):
  val createBtnEnabled = name.isEmpty

trait NewResumePresenter:
  def onCreateForm(): Unit
trait NewResumeView:
  def view(): Region

class NewResumeController extends Controller[Region]:
  val model = new NewResumeModel()
  val newResumePresenter = new NewResumePresenterImpl(model)
  val newResumeView = new NewResumeViewImpl(newResumePresenter, model)

  override def view(): Region =
    newResumeView.view()

class NewResumePresenterImpl(val model: NewResumeModel) extends NewResumePresenter:
  val logger = LoggerFactory.getLogger(classOf[NewResumePresenterImpl])

  override def onCreateForm(): Unit =
    EventBus.getDefault().post(PageType.PersonalDetails)

class NewResumeViewImpl(
  val presenter: NewResumePresenter,
  val model: NewResumeModel
) extends NewResumeView:

  override def view(): Region =
    createNewResumePane

  private def createNewResumePane =
    val content = List(
        new Text("Create New Resume"),
        new Button("Create Resume") {
          disable <== model.createBtnEnabled
          onAction = (event: ActionEvent) => presenter.onCreateForm()
        },
        new Label("Resume Name") { },
        new TextField {
          text <==> model.name
        }
    )

    ComponentUtil.createContentPage(content)

/*
  Personal Details
*/
trait PersonalDetailsPresenter:
  def onContinue(): Unit
trait PersonalDetailsView:
  def view(): Region

case class PersonalDetailsModel(
  val name: StringProperty = StringProperty(""),
  val title: StringProperty = StringProperty(""),
  val summary: StringProperty = StringProperty(""),
)

class PersonalDetailsController() extends Controller[Region]:
  val model = new PersonalDetailsModel()
  val wizardPresenter = new PersonalDetailsPresenterImpl(model)
  val wizardView = new PersonalDetailsViewImpl(wizardPresenter, model)

  def view(): Region =
    wizardView.view()  

class PersonalDetailsPresenterImpl(val model: PersonalDetailsModel) extends PersonalDetailsPresenter:
  val logger = LoggerFactory.getLogger(classOf[PersonalDetailsPresenterImpl])

  override def onContinue(): Unit =
    EventBus.getDefault().post(PageType.ContactDetails)

class PersonalDetailsViewImpl(
  val presenter: PersonalDetailsPresenter,
  val model: PersonalDetailsModel
) extends PersonalDetailsView:

  override def view(): Region =
    createPersonalDetails

  private def createPersonalDetails =
    val content = List(
        new Text("Personal Details"),
        new Button("Continue") {
          onAction = (event: ActionEvent) => presenter.onContinue()
        },
        new Label("Your name"),
        new TextField {
          text <==> model.name
        },
        new Label("Your Current Title"),
        new TextField {
          text <==> model.title
        },
        new Label("Summary of your current career position"),
        new TextArea {
          prefRowCount = 3
          text <==> model.summary
        }
    )

    ComponentUtil.createContentPage(content)

/*
  Contact Details
*/
case class ContactDetailsModel(
  val phone: StringProperty = StringProperty(""),
  val email: StringProperty = StringProperty(""),
  val location: StringProperty = StringProperty("")
)

trait ContactDetailsPresenter:
  def onContinue(): Unit
trait ContactDetailsView:
  def view(): Region

class ContactDetailsController() extends Controller[Region]:
  val model = new ContactDetailsModel()
  val contactDetailsPresenter = new ContactDetailsPresenterImpl(model)
  val contactDetailsView = new ContactDetailsViewImpl(contactDetailsPresenter, model)

  override def view(): Region =
    contactDetailsView.view()

class ContactDetailsPresenterImpl(
  val model: ContactDetailsModel
) extends ContactDetailsPresenter:
  val logger = LoggerFactory.getLogger(classOf[ContactDetailsPresenterImpl])

  override def onContinue(): Unit =
    EventBus.getDefault().post(PageType.Socials)

class ContactDetailsViewImpl(
  val presenter: ContactDetailsPresenter,
  val model: ContactDetailsModel
) extends ContactDetailsView:
  override def view(): Region =
    val content = List(
        new Text("Contact Details"),
        new Button("Continue") {
          onAction = (event: ActionEvent) => presenter.onContinue()
        },
        new Label("Your phone number"),
        new TextField {
          text <==> model.phone
        },
        new Label("Your email address"),
        new TextField {
          text <==> model.email
        },
        new Label("Your location"),
        new TextField {
          text <==> model.location
        }
    )

    ComponentUtil.createContentPage(content)

/*
  Socials
*/
case class SocialModel(
  val name: StringProperty = StringProperty(""),
  val url: StringProperty = StringProperty("")
)

case class SocialsModel(
  val socials: ObservableBuffer[SocialModel] = ObservableBuffer(new SocialModel())
)

trait SocialsPresenter:
  def onContinue(): Unit
trait SocialsView:
  def view(): Region

class SocialsController() extends Controller[Region]:
  val model = new SocialsModel()
  val socialsPresenter = new SocialsPresenterImpl(model)
  val socialsView = new SocialsViewImpl(socialsPresenter, model)

  override def view(): Region =
    socialsView.view()

class SocialsPresenterImpl(val model: SocialsModel) extends SocialsPresenter:
  val logger = LoggerFactory.getLogger(classOf[SocialsPresenterImpl])

  override def onContinue(): Unit =
    logger.info("on continue clicked...")

class SocialsViewImpl(val presenter: SocialsPresenter, val model: SocialsModel) extends SocialsView:
  override def view(): Region =
    val content = List(
        new Text("Socials"),
        new Button("Continue") {
          onAction = (event: ActionEvent) => presenter.onContinue()
        },
        new VBox {
          children = model.socials.map(social => createSocialSection(social)).flatten
          model.socials.onInvalidate { (newValue) =>
            children = model.socials.map(social => createSocialSection(social)).flatten
          }
        },
        new Button("Add Social") {
          onAction = (event: ActionEvent) => model.socials += new SocialModel()
        },
    )

    ComponentUtil.createContentPage(content)

  private def createSocialSection(social: SocialModel): List[Node] =
    List(
      new Label("Social Name"),
      new TextField {
        text <==> social.name
      },
      new Label("Social URL"),
      new TextField {
        text <==> social.url
      }
    )
/*
  Component Util
*/
object ComponentUtil:

  def createContentPage(content: List[Node]): VBox =
    new VBox(5):
      style = """
              -fx-padding: 20;
              -fx-background-color: #ffffff;
              -fx-background-radius: 5;
              -fx-border-color: rgba(0, 0, 0, 0.18); 
              -fx-border-width: 1; 
              -fx-border-style: solid;
              -fx-border-radius: 5;
              -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.18), 10, 0, 0, 0);
              """
      maxWidth = 1020
      children = content
