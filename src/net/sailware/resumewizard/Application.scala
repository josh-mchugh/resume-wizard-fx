package net.sailware.resumewizard

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.VPos
import scalafx.scene.Node
import scalafx.scene.Parent
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.Label
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
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty

enum Screen:
  case Dashboard
  case NewResume

case class State(val screen: Screen = Screen.Dashboard)

object Main extends JFXApp3:

  val stateProp = new SimpleObjectProperty(new State())

  EventBus.getDefault().register(this)

  override def start(): Unit =
    stage = new PrimaryStage:
      onCloseRequest = () => EventBus.getDefault().unregister(this)
      scene = new Scene(1280, 768):
        root = new MainController(stateProp).view()

  @Subscribe
  def onEvent(screen: Screen): Unit =
    stateProp.update(stateProp.value.copy(screen = screen))


/*
  Screen Components
 */
trait Controller[T]:
  def view(): T

object ScreenFactory:
  def createScreen(screen: Screen): Node =
    screen match
      case Screen.Dashboard => new DashboardController().view()
      case Screen.NewResume => new NewResumeController().view()

/*
  Main Components
 */
class MainModel { }

class MainController(val state: ObjectProperty[State]) extends Controller[Parent]:
  val presenter = new MainPresenterImpl(state)

  override def view(): Parent =
    presenter.view()

trait MainPresenter {}
trait MainView:
  def view(): Region

class MainPresenterImpl(val state: ObjectProperty[State]) extends MainPresenter:
  val mainView = new MainViewImpl(this, state)

  def view(): Region =
    mainView.view()

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
        children = List(ScreenFactory.createScreen(Screen.Dashboard))
        state.onChange ({
           children = List(ScreenFactory.createScreen(state.value.screen))
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

  override def view(): Node =
    dashboardPresenter.view()

class DashboardPresenterImpl extends DashboardPresenter:
  val dashboardView = new DashboardViewImpl(this)

  def view(): Node =
    dashboardView.view()

  override def onNewResumeButtonAction(): Unit =
    EventBus.getDefault().post(Screen.NewResume)

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
trait NewResumePresenter { }
trait NewResumeView:
  def view(): Region

class NewResumeController extends Controller[Region]:
  val newResumePresenter = new NewResumePresenterImpl()

  override def view(): Region =
    newResumePresenter.view()

class NewResumePresenterImpl extends NewResumePresenter:
  val newResumeView = new NewResumeViewImpl(this)

  def view(): Region =
    newResumeView.view()

class NewResumeViewImpl(
  val presenter: NewResumePresenter
) extends NewResumeView:

  override def view(): Region =
    createNewResumePane

  private def createNewResumePane =
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
      children = List(
        new Text("Create New Resume"),
        new Button("Create Resume"),
        new Label("Resume Name") { },
        new TextField { }
      )
