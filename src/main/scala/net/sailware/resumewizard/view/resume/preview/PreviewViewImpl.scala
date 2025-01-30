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
    val element = Page(
      margin = Margin(100F, 50F, 100F, 50F),
      padding = Padding(100F, 50F, 100F, 50F),
      border = Border(Color.RED, 25F)
    )
    val results = collection.mutable.ListBuffer[Node]()
    if element.width > 0F then
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
    val startX = element.x + element.margin.left + (element.border.width / 2F)
    val startY = element.y + element.margin.top + (element.border.width / 2F)
    val endX = element.x + element.width - element.margin.right - (element.border.width / 2F)
    renderLine(startX, startY, endX, startY, element.border.color, element.border.width)

  private def createElementBorderRight(element: Element): Line =
    val startY = element.y + element.margin.top + (element.border.width / 2F)
    val endX = element.x + element.width - element.margin.right - (element.border.width / 2F)
    val endY = element.y + element.height - element.margin.bottom - (element.border.width / 2F)
    renderLine(endX, startY, endX, endY, element.border.color, element.border.width)

  private def createElementBorderBottom(element: Element): Line =
    val startX = element.x + element.margin.left + (element.border.width / 2F)
    val endX = element.x + element.width - element.margin.right - (element.border.width / 2F)
    val endY = element.y + element.height - element.margin.bottom - (element.border.width / 2F)
    renderLine(startX, endY, endX, endY, element.border.color, element.border.width)

  private def createElementBorderLeft(element: Element): Line =
    val startX = element.x + element.margin.left + (element.border.width / 2F)
    val startY = element.y + element.margin.top + (element.border.width / 2F)
    val endY = element.y + element.height - element.margin.bottom - (element.border.width / 2F)
    renderLine(startX, startY, startX, endY, element.border.color, element.border.width)

  private def createElementDebugMarginBorderTop(element: Element): Line =
    val startX = element.x
    val startY = element.y
    val endX = element.x + element.width
    renderLine(startX, startY, endX, startY, Color.AQUA, 1F, List(5d, 5d))

  private def createElementDebugMarginBorderRight(element: Element): Line =
    val startY = element.y
    val endX = element.x + element.width
    val endY = element.y + element.height
    renderLine(endX, startY, endX, endY, Color.AQUA, 1F, List(5d, 5d))

  private def createElementDebugMarginBorderBottom(element: Element): Line =
    val startX = element.x
    val endX = element.x + element.width
    val endY = element.y + element.height
    renderLine(startX, endY, endX, endY, Color.AQUA, 1F, List(5d, 5d))

  private def createElementDebugMarginBorderLeft(element: Element): Line =
    val startX = element.x
    val startY = element.y
    val endY = element.y + element.height
    renderLine(startX, startY, startX, endY, Color.AQUA, 1F, List(5d, 5d))

  private def createElementDebugPaddingBorderTop(element: Element): Line =
    val startX = element.x + element.margin.left + element.border.width + element.padding.left
    val startY = element.y + element.margin.top + element.border.width + element.padding.top
    val endX = element.x + element.width - element.margin.right - element.border.width - element.padding.right
    renderLine(startX, startY, endX, startY, Color.PINK, 1F, List(5d, 5d))

  private def createElementDebugPaddingBorderRight(element: Element): Line =
    val startY = element.y + element.margin.top + element.border.width + element.padding.top
    val endX = element.x + element.width - element.margin.right - element.border.width - element.padding.right
    val endY = element.y + element.height - element.margin.bottom - element.border.width - element.padding.bottom
    renderLine(endX, startY, endX, endY, Color.PINK, 1F, List(5d, 5d))

  private def createElementDebugPaddingBorderBottom(element: Element): Line =
    val startX = element.x + element.margin.left + element.border.width + element.padding.left
    val endX = element.x + element.width - element.margin.right - element.border.width - element.padding.right
    val endY = element.y + element.height - element.margin.bottom - element.border.width - element.padding.bottom
    renderLine(startX, endY, endX, endY, Color.PINK, 1F, List(5d, 5d))

  private def createElementDebugPaddingBorderLeft(element: Element): Line =
    val startX = element.x + element.margin.left + element.border.width + element.padding.left
    val startY = element.y + element.margin.top + element.border.width + element.padding.top
    val endY = element.y + element.height - element.margin.bottom - element.border.width - element.padding.bottom
    renderLine(startX, startY, startX, endY, Color.PINK, 1F, List(5d, 5d))

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color, strokeWidth: Float): Line =
    renderLine(startX, startY, endX, endY, color, strokeWidth,  List.empty)

  private def renderLine(startXValue: Float, startYValue: Float, endXValue: Float, endYValue: Float, colorValue: Color, strokeWidthValue: Float, strokeDashList: List[java.lang.Double]): Line =
    new Line:
      startX =startXValue
      startY = startYValue
      endX = endXValue
      endY = endYValue
      stroke = colorValue
      strokeWidth = strokeWidthValue
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
  val color: Color = Color.BLACK,
  val width: Float = 0F
)

abstract class Element:
  def x: Float
  def y: Float
  def width: Float
  def height: Float
  def margin: Margin
  def padding: Padding
  def border: Border

class Page(
  val x: Float = 0F,
  val y: Float = 0F,
  val width: Float = 793.7007874F,
  val height: Float = 1122.519685F,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border()
) extends Element
