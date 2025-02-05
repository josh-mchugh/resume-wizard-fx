package net.sailware.resumewizard.view.resume.preview

import scalafx.Includes.*
import scalafx.scene.Node
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import org.slf4j.LoggerFactory

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  val logger = LoggerFactory.getLogger(classOf[PreviewViewImpl])

  override def view(): Node =
    model.resume.onInvalidate { resume => logger.info("resume: {}", resume) }
    val canvas = new Canvas(793.7007874F, 1122.519685F)
    val gc = canvas.getGraphicsContext2D()
    renderPage(gc)
    canvas

  private def renderElement(element: Element, debug: Boolean, gc: GraphicsContext): Unit =
    if element.width > 0F then
      createElementBorderTop(element, gc)
      createElementBorderRight(element, gc)
      createElementBorderBottom(element, gc)
      createElementBorderLeft(element, gc)
    if debug then
      createElementDebugMarginBorderTop(element, gc)
      createElementDebugMarginBorderRight(element, gc)
      createElementDebugMarginBorderBottom(element, gc)
      createElementDebugMarginBorderLeft(element, gc)

      createElementDebugPaddingBorderTop(element, gc)
      createElementDebugPaddingBorderRight(element, gc)
      createElementDebugPaddingBorderBottom(element, gc)
      createElementDebugPaddingBorderLeft(element, gc)

  private def renderPage(gc: GraphicsContext): Unit =
    val debug = false
    val page = Data().createPage()
    gc.setFill(Color.WHITE)
    gc.fillRect(0F, 0F, 793.7007874F, 1122.519685F)
    renderElement(page, debug, gc)
    for row <- page.rows do
      renderElement(row, debug, gc)
      for column <- row.columns do
        renderElement(column, debug, gc)

  private def createElementBorderTop(element: Element, gc: GraphicsContext): Unit =
    val startX = element.x + element.margin.left + (element.border.width / 2F)
    val startY = element.y + element.margin.top + (element.border.width / 2F)
    val endX = element.x + element.width - element.margin.right - (element.border.width / 2F)
    renderLine(startX, startY, endX, startY, element.border.color, element.border.width, gc)

  private def createElementBorderRight(element: Element, gc: GraphicsContext): Unit =
    val startY = element.y + element.margin.top + (element.border.width / 2F)
    val endX = element.x + element.width - element.margin.right - (element.border.width / 2F)
    val endY = element.y + element.height - element.margin.bottom - (element.border.width / 2F)
    renderLine(endX, startY, endX, endY, element.border.color, element.border.width, gc)

  private def createElementBorderBottom(element: Element, gc: GraphicsContext): Unit =
    val startX = element.x + element.margin.left + (element.border.width / 2F)
    val endX = element.x + element.width - element.margin.right - (element.border.width / 2F)
    val endY = element.y + element.height - element.margin.bottom - (element.border.width / 2F)
    renderLine(startX, endY, endX, endY, element.border.color, element.border.width, gc)

  private def createElementBorderLeft(element: Element, gc: GraphicsContext): Unit =
    val startX = element.x + element.margin.left + (element.border.width / 2F)
    val startY = element.y + element.margin.top + (element.border.width / 2F)
    val endY = element.y + element.height - element.margin.bottom - (element.border.width / 2F)
    renderLine(startX, startY, startX, endY, element.border.color, element.border.width, gc)

  private def createElementDebugMarginBorderTop(element: Element, gc: GraphicsContext): Unit =
    val startX = element.x
    val startY = element.y
    val endX = element.x + element.width
    renderLine(startX, startY, endX, startY, Color.AQUA, 1F, List(5d, 5d), gc)

  private def createElementDebugMarginBorderRight(element: Element, gc: GraphicsContext): Unit =
    val startY = element.y
    val endX = element.x + element.width
    val endY = element.y + element.height
    renderLine(endX, startY, endX, endY, Color.AQUA, 1F, List(5d, 5d), gc)

  private def createElementDebugMarginBorderBottom(element: Element, gc: GraphicsContext): Unit =
    val startX = element.x
    val endX = element.x + element.width
    val endY = element.y + element.height
    renderLine(startX, endY, endX, endY, Color.AQUA, 1F, List(5d, 5d), gc)

  private def createElementDebugMarginBorderLeft(element: Element, gc: GraphicsContext): Unit =
    val startX = element.x
    val startY = element.y
    val endY = element.y + element.height
    renderLine(startX, startY, startX, endY, Color.AQUA, 1F, List(5d, 5d), gc)

  private def createElementDebugPaddingBorderTop(element: Element, gc: GraphicsContext): Unit =
    val startX = element.x + element.margin.left + element.border.width + element.padding.left
    val startY = element.y + element.margin.top + element.border.width + element.padding.top
    val endX = element.x + element.width - element.margin.right - element.border.width - element.padding.right
    renderLine(startX, startY, endX, startY, Color.PINK, 1F, List(5d, 5d), gc)

  private def createElementDebugPaddingBorderRight(element: Element, gc: GraphicsContext): Unit =
    val startY = element.y + element.margin.top + element.border.width + element.padding.top
    val endX = element.x + element.width - element.margin.right - element.border.width - element.padding.right
    val endY = element.y + element.height - element.margin.bottom - element.border.width - element.padding.bottom
    renderLine(endX, startY, endX, endY, Color.PINK, 1F, List(5d, 5d), gc)

  private def createElementDebugPaddingBorderBottom(element: Element, gc: GraphicsContext): Unit =
    val startX = element.x + element.margin.left + element.border.width + element.padding.left
    val endX = element.x + element.width - element.margin.right - element.border.width - element.padding.right
    val endY = element.y + element.height - element.margin.bottom - element.border.width - element.padding.bottom
    renderLine(startX, endY, endX, endY, Color.PINK, 1F, List(5d, 5d), gc)

  private def createElementDebugPaddingBorderLeft(element: Element, gc: GraphicsContext): Unit =
    val startX = element.x + element.margin.left + element.border.width + element.padding.left
    val startY = element.y + element.margin.top + element.border.width + element.padding.top
    val endY = element.y + element.height - element.margin.bottom - element.border.width - element.padding.bottom
    renderLine(startX, startY, startX, endY, Color.PINK, 1F, List(5d, 5d), gc)

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color, strokeWidth: Float, gc: GraphicsContext): Unit =
    renderLine(startX, startY, endX, endY, color, strokeWidth,  List.empty, gc)

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color, strokeWidth: Float, strokeDashList: List[Double], gc: GraphicsContext): Unit =
    gc.setStroke(color)
    gc.setLineWidth(strokeWidth)
    gc.setLineDashes(strokeDashList*)
    gc.strokeLine(startX, startY, endX, endY)

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
  def contentStartX(): Float = x + margin.left + border.width + padding.left
  def contentStartY(): Float = y + margin.top + border.width + padding.top
  def contentWidth(): Float = width - margin.left - border.width - padding.left - padding.right - border.width - margin.right
  def contentHeight(): Float = height - margin.top - border.width - padding.top - padding.bottom - border.width - margin.bottom

case class Page(
  val x: Float = 0F,
  val y: Float = 0F,
  val width: Float = 793.7007874F,
  val height: Float = 1122.519685F,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val rows: List[Row] = List.empty
) extends Element

case class Row(
  val x: Float,
  val y: Float,
  val width: Float,
  val height: Float,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val columns: List[Column] = List.empty
) extends Element

case class Column(
  val x: Float,
  val y: Float,
  val width: Float,
  val height: Float,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
) extends Element

class Data:
  def createPage(): Page =
    val page = Page(
      margin = Margin(4F, 4F, 4F, 4F),
      padding = Padding(4F, 4F, 4F, 4F),
      border = Border(Color.Blue, 1F)
    )
    var row = Row(
      x = page.contentStartX(),
      y = page.contentStartY(),
      width = page.contentWidth(),
      height = page.contentHeight(),
      margin = Margin(4F, 4F, 4F, 4F),
      padding = Padding(4F, 4F, 4F, 4F),
      border = Border(Color.Purple, 1F),
    )
    val column = Column(
      x = row.contentStartX(),
      y = row.contentStartY(),
      width = row.contentWidth() / 2,
      height = row.contentHeight(),
      margin = Margin(4F, 4F, 4F, 4F),
      padding = Padding(4F, 4F, 4F, 4F),
      border = Border(Color.Orange, 1F),
    )
    row = row.copy(columns = List(column))
    page.copy(rows = List(row))
