package net.sailware.resumewizard

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.VPos
import scalafx.scene.Node
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
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections;
import javafx.util.Builder

object Main extends JFXApp3:

  override def start(): Unit =
    stage = createStage()

  private def createStage(): PrimaryStage =
    val controller = new MainController()

    EventBus.getDefault().register(controller)

    new PrimaryStage:
      onCloseRequest = () => EventBus.getDefault().unregister(controller)
      scene = new Scene(1280, 768):
        root = controller.view()

/*
  Main Components
 */
case class State(val screen: String = "Dashboard")

class MainModel:
  val state = new SimpleObjectProperty(new State())

class MainController:
  val model = new MainModel()
  def viewBuilder = new MainViewBuilder(
    model,
    List(
      new DashboardController(model.state).view(),
      new NewResumeController(model.state).view()
    )
  )

  def view(): Region =
    viewBuilder.build()

  @Subscribe
  def onEvent(event: String): Unit =
    model.state.update(new State("NewResume"))

class MainViewBuilder(
  val model: MainModel,
  val screens: List[Node]
) extends Builder[Region]:

  override def build(): Region =
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
        children = screens

/*
  Dashboard Components
*/
class DashboardModel { }

class DashboardController(val state: ObjectProperty[State]):
  val func = () => EventBus.getDefault().post("Hello everyone!");
  val model = new DashboardModel
  val viewBuilder = new DashboardViewBuilder(model, func) 

  def view(): Node =
    viewBuilder.build()

class DashboardViewBuilder(val model: DashboardModel, val func: () => Unit) extends Builder[Node]:

  override def build(): Node =
    createNewResumeButton

  private def createNewResumeButton =
    new Button("Create Resume"):
      onAction = (event: ActionEvent) => func()

/*
  New Resume Components
*/
class NewResumeModel { }

class NewResumeController(val state: ObjectProperty[State]):

  val model = new NewResumeModel()
  val viewBuilder = new NewResumeViewBuilder(model)

  def view(): Region =
    val result = viewBuilder.build()
    result.visibleProperty <== state.isEqualTo(new State("NewResume"))
    result

class NewResumeViewBuilder(val model: NewResumeModel) extends Builder[Region]:

  override def build(): Region =
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
