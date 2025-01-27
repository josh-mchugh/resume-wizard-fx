package net.sailware.resumewizard.view.resume.preview

import scalafx.Includes.*
import scalafx.scene.Node
import scalafx.scene.paint.Color
import scalafx.scene.shape.Line
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.Pane
import scalafx.scene.layout.Region
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import org.slf4j.LoggerFactory

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  val logger = LoggerFactory.getLogger(classOf[PreviewViewImpl])

  override def view(): Region =
    model.resume.onInvalidate { resume => logger.info("resume: {}", resume) }
    new Pane:
      style = "-fx-background-color: white"
      maxWidth = 793.7007874F
      maxHeight = 1122.519685F
      minWidth = 793.7007874F
      minHeight = 1122.519685F
      children = renderRow()

  private def renderRow(): List[Node] =
    val element = Element(
      x = 0F,
      y = 0F,
      width = 793.7007874F,
      height = 1122.519685F,
      margin = Margin(100F, 50F, 100F, 50F),
      border = Some(Border(Color.RED))
    )
    val results = collection.mutable.ListBuffer[Node]()
    if element.border.isDefined then
      val border = element.border.get
      results += createTopBorder(element)
      results += createRightBorder(element)
      results += createBottomBorder(element)
      results += createLeftBorder(element)
    results.toList

  private def createTopBorder(element: Element): Line =
    val border = element.border.get
    val startX = element.x + element.margin.left
    val startY = element.y + element.margin.top
    val endX = element.x + element.width - element.margin.right
    renderLine(startX, startY, endX, startY, border.color)

  private def createRightBorder(element: Element): Line =
    val border = element.border.get
    val startY = element.y + element.margin.top
    val endX = element.x + element.width - element.margin.right
    val endY = element.y + element.height - element.margin.bottom
    renderLine(endX, startY, endX, endY, border.color)

  private def createBottomBorder(element: Element): Line =
    val border = element.border.get
    val startX = element.x + element.margin.left
    val endX = element.x + element.width - element.margin.right
    val endY = element.y + element.height - element.margin.bottom
    renderLine(startX, endY, endX, endY, border.color)

  private def createLeftBorder(element: Element): Line =
    val border = element.border.get
    val startX = element.x + element.margin.left
    val startY = element.y + element.margin.top
    val endY = element.y + element.height - element.margin.bottom
    renderLine(startX, startY, startX, endY, border.color)

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color): Line =
    val result = new Line()
    result.setStartX(startX)
    result.setStartY(startY)
    result.setEndX(endX)
    result.setEndY(endY)
    result.setStrokeWidth(1F)
    result.setStroke(color)
    result

case class Margin(
  val top: Float = 0F,
  val right: Float = 0F,
  val bottom: Float = 0F,
  val left: Float = 0F
)

case class Padding(
  val top: Float = 0F,
  val right: Float = 0F,
  val bottom: Float = 0F,
  val left: Float = 0F
)

case class Border(
  val color: Color
)

case class Element(
  val x: Float,
  val y: Float,
  val width: Float,
  val height: Float,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Option[Border] = None
)
