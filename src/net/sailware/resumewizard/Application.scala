package net.sailware.resumewizard

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.slf4j.LoggerFactory
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
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
  case Wizard

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
      case PageType.Wizard => new WizardController().view()

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
    EventBus.getDefault().post(PageType.Wizard)

case class NewResumeModel(
  val name: StringProperty = StringProperty("")
)

class NewResumeViewImpl(
  val presenter: NewResumePresenter,
  val model: NewResumeModel
) extends NewResumeView:

  override def view(): Region =
    createNewResumePane

  private def createNewResumePane =
    val childNodes = List(
        new Text("Create New Resume"),
        new Button("Create Resume") {
          disable <== model.name.isEmpty()
          onAction = (event: ActionEvent) => presenter.onCreateForm()
        },
        new Label("Resume Name") { },
        new TextField {
          text <==> model.name
        }
    )

    ComponentUtil.createContentPage(childNodes)

/*
  Wizard Components
*/
trait WizardPresenter:
  def onContinue(): Unit
trait WizardView:
  def view(): Region

case class WizardModel(
  val name: StringProperty = StringProperty(""),
  val title: StringProperty = StringProperty(""),
  val summary: StringProperty = StringProperty(""),
)

class WizardController() extends Controller[Region]:
  val model = new WizardModel()
  val wizardPresenter = new WizardPresenterImpl(model)
  val wizardView = new WizardViewImpl(wizardPresenter, model)

  def view(): Region =
    wizardView.view()  

class WizardPresenterImpl(val model: WizardModel) extends WizardPresenter:
  val logger = LoggerFactory.getLogger(classOf[WizardPresenterImpl])

  override def onContinue(): Unit =
    logger.info("wizard continue clicked")

class WizardViewImpl(
  val presenter: WizardPresenter,
  val model: WizardModel
) extends WizardView:

  override def view(): Region =
    createWizard

  private def createWizard =
    val childNodes = List(
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

    ComponentUtil.createContentPage(childNodes)

/*
  Component Util
*/
object ComponentUtil:

  def createContentPage(childNodes: List[Node]): VBox =
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
      children = childNodes
