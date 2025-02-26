package net.sailware.resumewizard.view.resume.preview

import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.stage.Screen
import org.slf4j.LoggerFactory
import scala.compiletime.ops.double

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  val logger = LoggerFactory.getLogger(classOf[PreviewViewImpl])

  override def view(): Node =
    model.resume.onInvalidate { resume => logger.info("resume: {}", resume) }
    val pages = Data().simpleBorderedPage()
    val canvases = pages.map(page =>
      val result = new Canvas(595F.toPx, 842F.toPx)
      val gc = result.getGraphicsContext2D()
      renderPage(page, gc)
      result
    )
    new VBox:
      spacing = 10
      alignment = Pos.CENTER
      children = canvases

  private def renderElement(element: RenderElement, debug: Boolean, gc: GraphicsContext): Unit =
    given GraphicsContext = gc
    if element.border.width > 0F then
      element.renderBorderTop()
      element.renderBorderRight()
      element.renderBorderBottom()
      element.renderBorderLeft()
    if debug then
      element.renderDebugMarginBorderTop()
      element.renderDebugMarginBorderRight()
      element.renderDebugMarginBorderBottom()
      element.renderDebugMarginBorderLeft()

      element.renderDebugPaddingBorderTop()
      element.renderDebugPaddingBorderRight()
      element.renderDebugPaddingBorderBottom()
      element.renderDebugPaddingBorderLeft()

    gc.setFill(element.background.color)
    val elementContentStartPosition = ElementUtil.contentStartPosition(element)
    gc.fillRect(elementContentStartPosition.x.toPx, elementContentStartPosition.y.toPx, element.contentWidth().toPx, element.contentHeight().toPx)

  private def renderPage(page: Page, gc: GraphicsContext): Unit =
    val debug = true
    gc.setFill(Color.WHITE)
    gc.fillRect(page.position.x.toPx, page.position.x.toPx, page.width.toPx, page.height.toPx)
    renderElement(page, debug, gc)
    for row <- page.rows do
      renderElement(row, debug, gc)
      for column <- row.columns do
        renderElement(column, debug, gc)
        for content <- column.content do
          renderElement(content, debug, gc)

extension (value: Float)
  def toPx: Float = value * (Screen.primary.getDpi().toFloat / 72F)

object PageConstants:
  object A4:
    val width: Float = 595F
    val height: Float = 842F

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

case class Position(
  val x: Float,
  val y: Float
)

sealed abstract class BoundingBox:
  def top(): Float
  def right(): Float
  def bottom(): Float
  def left(): Float
  def topLeft(): Position = Position(left(), top())
  def topRight(): Position = Position(right(), top())
  def bottomRight(): Position = Position(right(), bottom())
  def bottomLeft(): Position = Position(left(), bottom())

case class BorderBoundingBox(
  element: Element
) extends BoundingBox:
  def top(): Float = element.position.y + element.margin.top + (element.border.width / 2F)
  def right(): Float = element.position.x + element.width - element.margin.right - (element.border.width / 2F)
  def bottom(): Float = element.position.y + element.height - element.margin.bottom - (element.border.width / 2F)
  def left(): Float = element.position.x + element.margin.left + (element.border.width / 2F)


case class MarginBoundingBox(
  element: Element
) extends BoundingBox:
  def top(): Float = element.position.y
  def left(): Float = element.position.x
  def bottom(): Float = element.position.y + element.height
  def right(): Float = element.position.x + element.width

case class PaddingBoundingBox(
  element: Element
) extends BoundingBox:
  def top(): Float = element.position.y + element.margin.top + element.border.width + element.padding.top
  def right(): Float = element.position.x + element.width - element.margin.right - element.border.width - element.padding.right
  def bottom(): Float = element.position.y + element.height - element.margin.bottom - element.border.width - element.padding.bottom
  def left(): Float = element.position.x + element.margin.left + element.border.width + element.padding.left

object ElementUtil:
  def contentStartX(x: Float, margin: Margin, padding: Padding, border: Border): Float =
    x + margin.left + border.width + padding.left

  def contentStartY(y: Float, margin: Margin, padding: Padding, border: Border): Float =
    y + margin.top + border.width + padding.top

  def contentStartPosition(x: Float, y: Float, margin: Margin, padding: Padding, border: Border): Position =
    Position(contentStartX(x, margin, padding, border), contentStartY(y, margin, padding, border))

  def contentStartPosition(position: Position, margin: Margin, padding: Padding, border: Border): Position =
    Position(contentStartX(position.x, margin, padding, border), contentStartY(position.y, margin, padding, border))

  def contentStartPosition(element: Element): Position =
    Position(contentStartX(element.position.x, element.margin, element.padding, element.border), contentStartY(element.position.y, element.margin, element.padding, element.border))

abstract class Element:
  def position: Position
  def width: Float
  def height: Float
  def margin: Margin
  def padding: Padding
  def border: Border
  def background: Background
  def contentWidth(): Float = width - margin.left - border.width - padding.left - padding.right - border.width - margin.right
  def contentHeight(): Float = height - margin.top - border.width - padding.top - padding.bottom - border.width - margin.bottom
  def x: Float = position.x
  def y: Float = position.y
  def borderBoundingBox = BorderBoundingBox(this)
  def marginBoundingBox = MarginBoundingBox(this)
  def paddingBoundingBox = PaddingBoundingBox(this)

abstract class RenderElement extends Element:
  def renderBorderTop()(using gc: GraphicsContext): Unit =
    renderBoundingBoxTop(borderBoundingBox, border, gc)

  def renderBorderRight()(using gc: GraphicsContext): Unit =
    renderBoundingBoxRight(borderBoundingBox, border, gc)

  def renderBorderBottom()(using gc: GraphicsContext): Unit =
    renderBoundingBoxBottom(borderBoundingBox, border, gc)

  def renderBorderLeft()(using gc: GraphicsContext): Unit =
    renderBoundingBoxLeft(borderBoundingBox, border, gc)

  def renderDebugMarginBorderTop()(using gc: GraphicsContext): Unit =
    renderBoundingBoxTop(marginBoundingBox, Color.AQUA, 1F, List(5d, 5d), gc)

  def renderDebugMarginBorderRight()(using gc: GraphicsContext): Unit =
    renderBoundingBoxRight(marginBoundingBox, Color.AQUA, 1F, List(5d, 5d), gc)

  def renderDebugMarginBorderBottom()(using gc: GraphicsContext): Unit =
    renderBoundingBoxBottom(marginBoundingBox, Color.AQUA, 1F, List(5d, 5d), gc)

  def renderDebugMarginBorderLeft()(using gc: GraphicsContext): Unit =
    renderBoundingBoxLeft(marginBoundingBox, Color.AQUA, 1F, List(5d, 5d), gc)

  def renderDebugPaddingBorderTop()(using gc: GraphicsContext): Unit =
    renderBoundingBoxTop(paddingBoundingBox, Color.PINK, 1F, List(5d, 5d), gc)

  def renderDebugPaddingBorderRight()(using gc: GraphicsContext): Unit =
    renderBoundingBoxRight(paddingBoundingBox, Color.PINK, 1F, List(5d, 5d), gc)

  def renderDebugPaddingBorderBottom()(using gc: GraphicsContext): Unit =
    renderBoundingBoxBottom(paddingBoundingBox, Color.PINK, 1F, List(5d, 5d), gc)

  def renderDebugPaddingBorderLeft()(using gc: GraphicsContext): Unit =
    renderBoundingBoxLeft(paddingBoundingBox, Color.PINK, 1F, List(5d, 5d), gc)

  private def renderBoundingBoxTop(boundingBox: BoundingBox, border: Border, gc: GraphicsContext): Unit =
    renderBoundingBoxTop(boundingBox, border.color, border.width, List.empty, gc)

  private def renderBoundingBoxRight(boundingBox: BoundingBox, border: Border, gc: GraphicsContext): Unit =
    renderBoundingBoxRight(boundingBox, border.color, border.width, List.empty, gc)

  private def renderBoundingBoxBottom(boundingBox: BoundingBox, border: Border, gc: GraphicsContext): Unit =
    renderBoundingBoxBottom(boundingBox, border.color, border.width, List.empty, gc)

  private def renderBoundingBoxLeft(boundingBox: BoundingBox, border: Border, gc: GraphicsContext): Unit =
    renderBoundingBoxLeft(boundingBox, border.color, border.width, List.empty, gc)

  private def renderBoundingBoxTop(boundingBox: BoundingBox, color: Color, width: Float, pattern: List[Double], gc: GraphicsContext): Unit =
    renderLine(boundingBox.topLeft().x, boundingBox.topLeft().y, boundingBox.topRight().x, boundingBox.topRight().y, color, width, pattern, gc)

  private def renderBoundingBoxRight(boundingBox: BoundingBox, color: Color, width: Float, pattern: List[Double], gc: GraphicsContext): Unit =
    renderLine(boundingBox.topRight().x, boundingBox.topRight().y, boundingBox.bottomRight().x, boundingBox.bottomRight().y, color, width, pattern, gc)

  private def renderBoundingBoxBottom(boundingBox: BoundingBox, color: Color, width: Float, pattern: List[Double], gc: GraphicsContext): Unit =
    renderLine(boundingBox.bottomLeft().x, boundingBox.bottomLeft().y, boundingBox.bottomRight().x, boundingBox.bottomRight().y, color, width, pattern, gc)

  private def renderBoundingBoxLeft(boundingBox: BoundingBox, color: Color, width: Float, pattern: List[Double], gc: GraphicsContext): Unit =
    renderLine(boundingBox.topLeft().x, boundingBox.topLeft().y, boundingBox.bottomLeft().x, boundingBox.bottomLeft().y, color, width, pattern, gc)

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color, strokeWidth: Float, gc: GraphicsContext): Unit =
    renderLine(startX, startY, endX, endY, color, strokeWidth,  List.empty, gc)

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color, strokeWidth: Float, strokeDashList: List[Double], gc: GraphicsContext): Unit =
    gc.setStroke(color)
    gc.setLineWidth(strokeWidth)
    gc.setLineDashes(strokeDashList*)
    gc.strokeLine(startX.toPx, startY.toPx, endX.toPx, endY.toPx)

case class Page(
  val position: Position = Position(0F, 0F),
  val width: Float = PageConstants.A4.width,
  val height: Float = PageConstants.A4.height,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
  val rows: List[Row] = List.empty
) extends RenderElement

case class Row(
  val position: Position,
  val width: Float,
  val height: Float,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
  val columns: List[Column] = List.empty
) extends RenderElement

case class Column(
  val position: Position,
  val width: Float,
  val height: Float,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
  val content: List[Content] = List.empty
) extends RenderElement

case class Content(
  val position: Position,
  val width: Float,
  val height: Float,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
) extends RenderElement

class Data:
  /**
    * Simple template with with Margins, Padding, and Border to verify
    * that the lines are drawn correctly
    */
  def simpleBorderedPage(): List[Page] = TemplateTransformer(TemplateFactory.simpleBorderedPage()).transform()

  /**
    * Creates a two row page with 3 columns in each row
    */
  /*def twoRowSixColumnTemplate(): Page =
    val page = Page(
      padding = Padding(50F, 50F, 100F, 50F),
    )
    var row1 = Row(
      position = ElementUtil.contentStartPosition(page),
      width = page.contentWidth(),
      height = 50F,
    )
    val r1Column1 = Column(
      position = ElementUtil.contentStartPosition(row1),
      width = row1.contentWidth() / 3,
      height = row1.contentHeight(),
      margin = Margin(0F, 15F, 0F, 0F),
      background = Background(Color.Gray)
    )
    var r1Column2 = Column(
      position = r1Column1.position.copy(x = r1Column1.x + row1.contentWidth() / 3),
      width = row1.contentWidth() / 3,
      height = row1.contentHeight(),
      margin = Margin(0F, 15F, 0F, 15F),
      background = Background(Color.Gray)
    )
    var r1C2Content = Content(
      position = ElementUtil.contentStartPosition(r1Column2),
      width = r1Column2.contentWidth() / 2,
      height = 35F,
      margin = Margin(5F, 0F, 0F, 5F),
      background = Background(Color.WhiteSmoke)
    )
    r1Column2 = r1Column2.copy(content = List(r1C2Content))
    val r1Column3 = Column(
      position = r1Column1.position.copy(x = r1Column1.position.x + row1.contentWidth() / 3 * 2),
      width = row1.contentWidth() / 3,
      height = row1.contentHeight(),
      margin = Margin(0F, 0F, 0F, 15F),
      background = Background(Color.Gray)
    )
    row1 = row1.copy(columns = List(r1Column1, r1Column2, r1Column3))
    var row2 = Row(
      position = row1.position.copy(y = row1.position.y + row1.height),
      width = page.contentWidth(),
      height = page.contentHeight() - row1.height,
      margin = Margin(10F, 0F, 0F, 0F)
    )
    var r2Column1 = Column(
      position = ElementUtil.contentStartPosition(row2),
      width = row2.contentWidth() / 3,
      height = row2.contentHeight(),
      margin = Margin(0F, 15F, 0F, 0F),
    )
    val r2C1Content1 = Content(
      position = ElementUtil.contentStartPosition(r2Column1),
      width = r2Column1.contentWidth(),
      height = r2Column1.contentHeight() / 3,
      margin = Margin(0F, 0F, 10F, 0F),
      background = Background(Color.Gray)
    )
    val r2C1Content2 = Content(
      position = r2C1Content1.position.copy(y = r2C1Content1.position.y + r2C1Content1.height),
      width = r2Column1.contentWidth(),
      height = 50F,
      background = Background(Color.Gray)
    )
    r2Column1 = r2Column1.copy(content = List(r2C1Content1, r2C1Content2))
    val r2Column2 = Column(
      position = r2Column1.position.copy(x = r2Column1.position.x + row2.contentWidth() / 3),
      width = row2.contentWidth() / 3,
      height = row2.contentHeight(),
      margin = Margin(0F, 15F, 0F, 15F),
      background = Background(Color.Gray)
    )
    val r2Column3 = Column(
      position = r2Column1.position.copy(x = r2Column1.position.x + row2.contentWidth() / 3 * 2),
      width = row2.contentWidth() / 3,
      height = row2.contentHeight(),
      margin = Margin(0F, 0F, 0F, 15F),
      background = Background(Color.Gray)
    )
    row2 = row2.copy(columns = List(r2Column1, r2Column2, r2Column3))
    page.copy(rows = List(row1, row2))*/

  /**
    * Long page, it's a test to push the contents beyond the Page content max height
    */
  def longPage(): List[Page] = TemplateTransformer(TemplateFactory.alternatingGreen18()).transform()

enum SectionType:
  case Row, Column, Content

case class SectionTemplate(
  val id: String,
  val parentId: Option[String],
  val `type`: SectionType,
  val width: Option[Float] = None,
  val height: Option[Float] = None,
  val order: Int,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
)

case class PageTemplate(
  val width: Float,
  val height: Float,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
)

object PageTemplate:
  object A4:
    def apply(
      width: Float = PageConstants.A4.width,
      height: Float = PageConstants.A4.height,
      margin: Margin = Margin(),
      padding: Padding = Padding(),
      border: Border = Border()
    ): PageTemplate =
      PageTemplate(width, height, margin, padding, border)

case class Palette()

case class TypeFaces()

case class LayoutTemplate(
  val page: PageTemplate = PageTemplate.A4(),
  val sections: List[SectionTemplate] = List.empty,
  val typeFaces: TypeFaces = TypeFaces(),
  val palette: Palette = Palette()
)
