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

import scala.jdk.CollectionConverters.*

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
    val debug = true
    val element = Element(
      x = 0F,
      y = 0F,
      width = 793.7007874F,
      height = 1122.519685F,
      margin = Margin(100F, 50F, 100F, 50F),
      padding = Padding(100F, 50F, 100F, 50F),
      border = Some(Border(Color.RED))
    )
    val results = collection.mutable.ListBuffer[Node]()
    if element.border.isDefined then
      results += createElementBorderTop(element)
      results += createElementBorderRight(element)
      results += createElementBorderBottom(element)
      results += createElementBorderLeft(element)
    if debug then
      results += createElementDebugMarginBorderTop(element)
      results += createElementDebugMarginBorderRight(element)
      results += createElementDebugMarginBorderBottom(element)
      results += createElementDebugMarginBorderLeft(element)

      results += createElementDebugPaddingBorderTop(element)
      results += createElementDebugPaddingBorderRight(element)
      results += createElementDebugPaddingBorderBottom(element)
      results += createElementDebugPaddingBorderLeft(element)

    results.toList

  private def createElementBorderTop(element: Element): Line =
    val border = element.border.get
    val startX = element.x + element.margin.left
    val startY = element.y + element.margin.top
    val endX = element.x + element.width - element.margin.right
    renderLine(startX, startY, endX, startY, border.color)

  private def createElementBorderRight(element: Element): Line =
    val border = element.border.get
    val startY = element.y + element.margin.top
    val endX = element.x + element.width - element.margin.right
    val endY = element.y + element.height - element.margin.bottom
    renderLine(endX, startY, endX, endY, border.color)

  private def createElementBorderBottom(element: Element): Line =
    val border = element.border.get
    val startX = element.x + element.margin.left
    val endX = element.x + element.width - element.margin.right
    val endY = element.y + element.height - element.margin.bottom
    renderLine(startX, endY, endX, endY, border.color)

  private def createElementBorderLeft(element: Element): Line =
    val border = element.border.get
    val startX = element.x + element.margin.left
    val startY = element.y + element.margin.top
    val endY = element.y + element.height - element.margin.bottom
    renderLine(startX, startY, startX, endY, border.color)

  private def createElementDebugMarginBorderTop(element: Element): Line =
    val startX = element.x
    val startY = element.y
    val endX = element.x + element.width
    renderLine(startX, startY, endX, startY, Color.AQUA, List(5d, 5d))

  private def createElementDebugMarginBorderRight(element: Element): Line =
    val startY = element.y
    val endX = element.x + element.width
    val endY = element.y + element.height
    renderLine(endX, startY, endX, endY, Color.AQUA, List(5d, 5d))

  private def createElementDebugMarginBorderBottom(element: Element): Line =
    val startX = element.x
    val endX = element.x + element.width
    val endY = element.y + element.height
    renderLine(startX, endY, endX, endY, Color.AQUA, List(5d, 5d))

  private def createElementDebugMarginBorderLeft(element: Element): Line =
    val startX = element.x
    val startY = element.y
    val endY = element.y + element.height
    renderLine(startX, startY, startX, endY, Color.AQUA, List(5d, 5d))

  private def createElementDebugPaddingBorderTop(element: Element): Line =
    val startX = element.x + element.margin.left + element.padding.left
    val startY = element.y + element.margin.top + element.padding.top
    val endX = element.x + element.width - element.margin.right - element.padding.right
    renderLine(startX, startY, endX, startY, Color.PINK, List(5d, 5d))

  private def createElementDebugPaddingBorderRight(element: Element): Line =
    val startY = element.y + element.margin.top + element.padding.top
    val endX = element.x + element.width - element.margin.right - element.padding.right
    val endY = element.y + element.height - element.margin.bottom - element.padding.bottom
    renderLine(endX, startY, endX, endY, Color.PINK, List(5d, 5d))

  private def createElementDebugPaddingBorderBottom(element: Element): Line =
    val startX = element.x + element.margin.left + element.padding.left
    val endX = element.x + element.width - element.margin.right - element.padding.right
    val endY = element.y + element.height - element.margin.bottom - element.padding.bottom
    renderLine(startX, endY, endX, endY, Color.PINK, List(5d, 5d))

  private def createElementDebugPaddingBorderLeft(element: Element): Line =
    val startX = element.x + element.margin.left + element.padding.left
    val startY = element.y + element.margin.top + element.padding.top
    val endY = element.y + element.height - element.margin.bottom - element.padding.bottom
    renderLine(startX, startY, startX, endY, Color.PINK, List(5d, 5d))

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color): Line =
    renderLine(startX, startY, endX, endY, color, List.empty)

  private def renderLine(startXValue: Float, startYValue: Float, endXValue: Float, endYValue: Float, colorValue: Color, strokeDashList: List[java.lang.Double]): Line =
    new Line:
      startX =startXValue
      startY = startYValue
      endX = endXValue
      endY = endYValue
      stroke = colorValue
      strokeWidth = 1F
      strokeDashArray = strokeDashList

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
