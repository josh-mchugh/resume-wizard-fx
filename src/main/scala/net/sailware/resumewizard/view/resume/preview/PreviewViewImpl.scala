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
    if element.border.width > 0F then
      element.renderBorderTop(gc)
      element.renderBorderRight(gc)
      element.renderBorderBottom(gc)
      element.renderBorderLeft(gc)
    if debug then
      element.renderDebugMarginBorderTop(gc)
      element.renderDebugMarginBorderRight(gc)
      element.renderDebugMarginBorderBottom(gc)
      element.renderDebugMarginBorderLeft(gc)

      element.renderDebugPaddingBorderTop(gc)
      element.renderDebugPaddingBorderRight(gc)
      element.renderDebugPaddingBorderBottom(gc)
      element.renderDebugPaddingBorderLeft(gc)

    gc.setFill(element.background.color)
    gc.fillRect(element.contentStartX(), element.contentStartY(), element.contentWidth(), element.contentHeight())

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
        for content <- column.content do
          renderElement(content, debug, gc)

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

case class Background(
  val color: Color = Color.Transparent
)

abstract class Element:
  def x: Float
  def y: Float
  def width: Float
  def height: Float
  def margin: Margin
  def padding: Padding
  def border: Border
  def background: Background
  def contentStartX(): Float = x + margin.left + border.width + padding.left
  def contentStartY(): Float = y + margin.top + border.width + padding.top
  def contentWidth(): Float = width - margin.left - border.width - padding.left - padding.right - border.width - margin.right
  def contentHeight(): Float = height - margin.top - border.width - padding.top - padding.bottom - border.width - margin.bottom

  def renderBorderTop(gc: GraphicsContext): Unit =
    val startX = x + margin.left + (border.width / 2F)
    val startY = y + margin.top + (border.width / 2F)
    val endX = x + width - margin.right - (border.width / 2F)
    renderLine(startX, startY, endX, startY, border.color, border.width, gc)

  def renderBorderRight(gc: GraphicsContext): Unit =
    val startY = y + margin.top + (border.width / 2F)
    val endX = x + width - margin.right - (border.width / 2F)
    val endY = y + height - margin.bottom - (border.width / 2F)
    renderLine(endX, startY, endX, endY, border.color, border.width, gc)

  def renderBorderBottom(gc: GraphicsContext): Unit =
    val startX = x + margin.left + (border.width / 2F)
    val endX = x + width - margin.right - (border.width / 2F)
    val endY = y + height - margin.bottom - (border.width / 2F)
    renderLine(startX, endY, endX, endY, border.color, border.width, gc)

  def renderBorderLeft(gc: GraphicsContext): Unit =
    val startX = x + margin.left + (border.width / 2F)
    val startY = y + margin.top + (border.width / 2F)
    val endY = y + height - margin.bottom - (border.width / 2F)
    renderLine(startX, startY, startX, endY, border.color, border.width, gc)

  def renderDebugMarginBorderTop(gc: GraphicsContext): Unit =
    val startX = x
    val startY = y
    val endX = x + width
    renderLine(startX, startY, endX, startY, Color.AQUA, 1F, List(5d, 5d), gc)

  def renderDebugMarginBorderRight(gc: GraphicsContext): Unit =
    val startY = y
    val endX = x + width
    val endY = y + height
    renderLine(endX, startY, endX, endY, Color.AQUA, 1F, List(5d, 5d), gc)

  def renderDebugMarginBorderBottom(gc: GraphicsContext): Unit =
    val startX = x
    val endX = x + width
    val endY = y + height
    renderLine(startX, endY, endX, endY, Color.AQUA, 1F, List(5d, 5d), gc)

  def renderDebugMarginBorderLeft(gc: GraphicsContext): Unit =
    val startX = x
    val startY = y
    val endY = y + height
    renderLine(startX, startY, startX, endY, Color.AQUA, 1F, List(5d, 5d), gc)

  def renderDebugPaddingBorderTop(gc: GraphicsContext): Unit =
    val startX = x + margin.left + border.width + padding.left
    val startY = y + margin.top + border.width + padding.top
    val endX = x + width - margin.right - border.width - padding.right
    renderLine(startX, startY, endX, startY, Color.PINK, 1F, List(5d, 5d), gc)

  def renderDebugPaddingBorderRight(gc: GraphicsContext): Unit =
    val startY = y + margin.top + border.width + padding.top
    val endX = x + width - margin.right - border.width - padding.right
    val endY = y + height - margin.bottom - border.width - padding.bottom
    renderLine(endX, startY, endX, endY, Color.PINK, 1F, List(5d, 5d), gc)

  def renderDebugPaddingBorderBottom(gc: GraphicsContext): Unit =
    val startX = x + margin.left + border.width + padding.left
    val endX = x + width - margin.right - border.width - padding.right
    val endY = y + height - margin.bottom - border.width - padding.bottom
    renderLine(startX, endY, endX, endY, Color.PINK, 1F, List(5d, 5d), gc)

  def renderDebugPaddingBorderLeft(gc: GraphicsContext): Unit =
    val startX = x + margin.left + border.width + padding.left
    val startY = y + margin.top + border.width + padding.top
    val endY = y + height - margin.bottom - border.width - padding.bottom
    renderLine(startX, startY, startX, endY, Color.PINK, 1F, List(5d, 5d), gc)

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color, strokeWidth: Float, gc: GraphicsContext): Unit =
    renderLine(startX, startY, endX, endY, color, strokeWidth,  List.empty, gc)

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color, strokeWidth: Float, strokeDashList: List[Double], gc: GraphicsContext): Unit =
    gc.setStroke(color)
    gc.setLineWidth(strokeWidth)
    gc.setLineDashes(strokeDashList*)
    gc.strokeLine(startX, startY, endX, endY)

case class Page(
  val x: Float = 0F,
  val y: Float = 0F,
  val width: Float = 793.7007874F,
  val height: Float = 1122.519685F,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
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
  val background: Background = Background(),
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
  val background: Background = Background(),
  val content: List[Content] = List.empty
) extends Element

case class Content(
  val x: Float,
  val y: Float,
  val width: Float,
  val height: Float,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
) extends Element

class Data:
  def createPage(): Page =
    val page = Page(
      padding = Padding(50F, 50F, 100F, 50F),
    )
    var row1 = Row(
      x = page.contentStartX(),
      y = page.contentStartY(),
      width = page.contentWidth(),
      height = 50F,
    )
    val r1Column1 = Column(
      x = row1.contentStartX(),
      y = row1.contentStartY(),
      width = row1.contentWidth() / 3,
      height = row1.contentHeight(),
      margin = Margin(0F, 15F, 0F, 0F),
      background = Background(Color.Gray)
    )
    var r1Column2 = Column(
      x = row1.contentStartX() + row1.contentWidth() / 3,
      y = row1.contentStartY(),
      width = row1.contentWidth() / 3,
      height = row1.contentHeight(),
      margin = Margin(0F, 15F, 0F, 15F),
      background = Background(Color.Gray)
    )
    var r1C2Content = Content(
      x = r1Column2.contentStartX(),
      y = r1Column2.contentStartY(),
      width = r1Column2.contentWidth() / 2,
      height = 35F,
      margin = Margin(5F, 0F, 0F, 5F),
      background = Background(Color.WhiteSmoke)
    )
    r1Column2 = r1Column2.copy(content = List(r1C2Content))
    val r1Column3 = Column(
      x = row1.contentStartX() + row1.contentWidth() / 3 * 2,
      y = row1.contentStartY(),
      width = row1.contentWidth() / 3,
      height = row1.contentHeight(),
      margin = Margin(0F, 0F, 0F, 15F),
      background = Background(Color.Gray)
    )
    row1 = row1.copy(columns = List(r1Column1, r1Column2, r1Column3))
    var row2 = Row(
      x = page.contentStartX(),
      y = page.contentStartY() + row1.height,
      width = page.contentWidth(),
      height = page.contentHeight() - row1.height,
      margin = Margin(10F, 0F, 0F, 0F)
    )
    var r2Column1 = Column(
      x = row2.contentStartX(),
      y = row2.contentStartY(),
      width = row2.contentWidth() / 3,
      height = row2.contentHeight(),
      margin = Margin(0F, 15F, 0F, 0F),
    )
    val r2C1Content1 = Content(
      x = r2Column1.contentStartX(),
      y = r2Column1.contentStartY(),
      width = r2Column1.contentWidth(),
      height = r2Column1.contentHeight() / 3,
      margin = Margin(0F, 0F, 10F, 0F),
      background = Background(Color.Gray)
    )
    val r2C1Content2 = Content(
      x = r2Column1.contentStartX(),
      y = r2Column1.contentStartY() + r2C1Content1.height,
      width = r2Column1.contentWidth(),
      height = 50F,
      background = Background(Color.Gray)
    )
    r2Column1 = r2Column1.copy(content = List(r2C1Content1, r2C1Content2))
    val r2Column2 = Column(
      x = row2.contentStartX() + row2.contentWidth() / 3,
      y = row2.contentStartY(),
      width = row2.contentWidth() / 3,
      height = row2.contentHeight(),
      margin = Margin(0F, 15F, 0F, 15F),
      background = Background(Color.Gray)
    )
    val r2Column3 = Column(
      x = row2.contentStartX() + row2.contentWidth() / 3 * 2,
      y = row2.contentStartY(),
      width = row2.contentWidth() / 3,
      height = row2.contentHeight(),
      margin = Margin(0F, 0F, 0F, 15F),
      background = Background(Color.Gray)
    )
    row2 = row2.copy(columns = List(r2Column1, r2Column2, r2Column3))
    page.copy(rows = List(row1, row2))
