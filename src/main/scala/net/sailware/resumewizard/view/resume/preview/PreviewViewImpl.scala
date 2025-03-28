package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.resume.Resume
import org.slf4j.LoggerFactory
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.geometry.VPos
import scalafx.scene.Node
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import scalafx.scene.text.FontWeight
import scalafx.scene.text.TextAlignment
import scalafx.stage.Screen

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  val logger = LoggerFactory.getLogger(classOf[PreviewViewImpl])

  override def view(): Node =
    new VBox:
      spacing = 10
      alignment = Pos.CENTER
      children = List.empty
      model.resume.onInvalidate { resume =>
        logger.info("resume: {}", resume)
        val pages = Data().resumeTemplate(model.resume.value)
        children = pages.map(page =>
          val result = new Canvas(595F.toPx, 842F.toPx)
          val gc = result.getGraphicsContext2D()
          renderPage(page, gc)
          result
        )
      }

  private def renderElement(element: RenderElement, debug: Boolean, gc: GraphicsContext): Unit =
    given GraphicsContext = gc

    // set background fill for element
    gc.setFill(element.background.color)
    val fillPosition = element.fillPosition()
    gc.fillRect(fillPosition.x.toPx, fillPosition.y.toPx, element.fillWidth().toPx, element.fillHeight().toPx)

    // set border and debug borders for element
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

    element match
      case c: Content =>
        c.item match
          case Some(item) =>
            val contentPosition = ElementUtil.contentStartPosition(element)
            val font = ResumeFonts.loadFont(item.family.getOrElse("Arial"), item.weight.getOrElse(ResumeFontWeight.Normal), item.size.toPx)

            gc.setFont(font)
            gc.setFill(item.color)
            gc.setTextAlign(TextAlignment.LEFT)
            gc.setTextBaseline(VPos.TOP)
            for (line, index) <- item.text.zipWithIndex do
              gc.fillText(line, contentPosition.x.toPx, contentPosition.y.toPx + (item.size * index).toFloat.toPx)

          case None =>
      case _ =>

  private def renderPage(page: Page, gc: GraphicsContext): Unit =
    val debug = false
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
  position: Position,
  width: Float,
  height: Float,
  margin: Margin,
  border: Border
) extends BoundingBox:
  def top(): Float = position.y + margin.top + (border.width / 2F)
  def right(): Float = position.x + width - margin.right - (border.width / 2F)
  def bottom(): Float = position.y + height - margin.bottom - (border.width / 2F)
  def left(): Float = position.x + margin.left + (border.width / 2F)

object BorderBoundingBox:
  def apply(element: Element): BorderBoundingBox =
    BorderBoundingBox(element.position, element.width, element.height, element.margin, element.border)

case class MarginBoundingBox(
  position: Position,
  width: Float,
  height: Float
) extends BoundingBox:
  def top(): Float = position.y
  def left(): Float = position.x
  def bottom(): Float = position.y + height
  def right(): Float = position.x + width

object MarginBoundingBox:
  def apply(element: Element): MarginBoundingBox =
    MarginBoundingBox(element.position, element.width, element.height)

case class PaddingBoundingBox(
  position: Position,
  width: Float,
  height: Float,
  margin: Margin,
  border: Border,
  padding: Padding
) extends BoundingBox:
  def top(): Float = position.y + margin.top + border.width + padding.top
  def right(): Float = position.x + width - margin.right - border.width - padding.right
  def bottom(): Float = position.y + height - margin.bottom - border.width - padding.bottom
  def left(): Float = position.x + margin.left + border.width + padding.left

object PaddingBoundingBox:
  def apply(element: Element): PaddingBoundingBox =
    PaddingBoundingBox(element.position, element.width, element.height, element.margin, element.border, element.padding)

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

  def contentWidth(element: Element): Float =
    contentWidth(element.width, element.margin, element.padding, element.border)

  def contentWidth(width: Float, margin: Margin, padding: Padding, border: Border): Float =
    width - margin.left - border.width - padding.left - padding.right - border.width - margin.right

  def contentHeight(element: Element): Float =
    contentHeight(element.height, element.margin, element.padding, element.border)

  def contentHeight(height: Float, margin: Margin, padding: Padding, border: Border): Float =
    height - margin.top - border.width - padding.top - padding.bottom - border.width - margin.bottom

abstract class Element:
  def position: Position
  def width: Float
  def height: Float
  def margin: Margin
  def padding: Padding
  def border: Border
  def background: Background
  def contentWidth(): Float = ElementUtil.contentWidth(this)
  def contentHeight(): Float = ElementUtil.contentHeight(this)
  def fillPosition(): Position = Position(borderBoundingBox.left(), borderBoundingBox.top())
  def fillWidth(): Float = borderBoundingBox.right() - borderBoundingBox.left()
  def fillHeight(): Float = borderBoundingBox.bottom() - borderBoundingBox.top()
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
  val item: Option[ContentItem] = None
) extends RenderElement

case class ContentItem(
  val text: List[String] = List.empty,
  val size: Float = 0F,
  val color: Color = Color.Transparent,
  val family: Option[String] = None,
  val weight: Option[ResumeFontWeight] = None
)

class Data:
  /**
    * Simple template with with Margins, Padding, and Border to verify
    * that the lines are drawn correctly
    */
  def simpleBorderedPage(resume: Resume): List[Page] =
    TemplateTransformer(resume, TemplateFactory.simpleBorderedPage()).transform()

  /**
    * Creates a two row page with 3 columns in each row
    */
  def twoRowSixColumnTemplate(resume: Resume): List[Page] =
    TemplateTransformer(resume, TemplateFactory.twoRowSixColumnTemplate()).transform()

  /**
    * Long page, it's a test to push the contents beyond the Page content max height
    */
  def longPage(resume: Resume): List[Page] =
    TemplateTransformer(resume, TemplateFactory.alternatingGreen18()).transform()

  def resumeTemplate(resume: Resume): List[Page] =
    TemplateTransformer(resume, TemplateFactory.resumeTemplate()).transform()

enum SectionType:
  case Row, Column, Content

enum ResumeDataType:
  case Name, Title, Summary

case class ContentTemplate(
  val resumeDataType: Option[ResumeDataType] = None,
  val size: Float = 0F,
  val color: Color = Color.Transparent,
  val family: Option[String] = None,
  val weight: Option[ResumeFontWeight] = None
)

case class SectionTemplate(
  val id: String,
  val parentId: Option[String],
  val `type`: SectionType,
  val width: Option[Float] = None,
  val height: Option[Float] = None,
  val minHeight: Option[Float] = None,
  val order: Int,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
  val contentTemplate: Option[ContentTemplate] = None
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

object Palette:
  // generic colors
  val WHITE  = "WHITE"

  // test colors for alternating blocks
  val GREEN_DARK = "GREEN_DARK"
  val GREEN_LIGHT = "GREEN_LIGHT"

  // resume theme colors
  val PRIMARY = "PRIMARY"
  val GRAY_300 = "GRAY_300"

  def color(key: String): Color = colors(key)

  private val colors: Map[String, Color] = Map(
    // generic colors
    WHITE -> Color.rgb(255, 255, 255, 1F),
    // test colors for alternating blocks
    GREEN_DARK -> Color.rgb(61, 141, 122),
    GREEN_LIGHT -> Color.rgb(163, 209, 198),
    // resume theme colors
    PRIMARY -> Color.rgb(17, 33, 47, 1F),
    GRAY_300 -> Color.rgb(209, 213, 219, 1F)
  )

class TypeFaces(
  val fonts: Map[String, Font] = Map.empty
)

object ResumeFontFamily:
  val ROBOTO: String = "Roboto"

enum ResumeFontWeight:
  case Normal, Medium, Bold

case class ResumeFont (
  val family: String,
  val weight: ResumeFontWeight,
  val path: String,
)

object ResumeFonts:
  private val configs = Map[String, ResumeFont](
    s"${ResumeFontFamily.ROBOTO}_${ResumeFontWeight.Bold}" -> ResumeFont(
      ResumeFontFamily.ROBOTO,
      ResumeFontWeight.Bold,
      "/font/Roboto-Bold.ttf"
    ),
    s"${ResumeFontFamily.ROBOTO}_${ResumeFontWeight.Medium}" -> ResumeFont(
      ResumeFontFamily.ROBOTO,
      ResumeFontWeight.Medium,
      "/font/Roboto-Medium.ttf"
    ),
    s"${ResumeFontFamily.ROBOTO}_${ResumeFontWeight.Normal}" -> ResumeFont(
      ResumeFontFamily.ROBOTO,
      ResumeFontWeight.Normal,
      "/font/Roboto-Regular.ttf"
    )
  )

  private val fonts = collection.mutable.Map[String, Font]()

  def loadFont(family: String, weight: ResumeFontWeight, size: Float): Font =
    val key = s"${family}_${weight}_${size}"

    if fonts.get(key).isDefined then
      fonts(key)
    else
      val resumeFont = configs(s"${family}_${weight}")
      val font = Font.loadFont(getClass().getResource(resumeFont.path).openStream(), size)
      fonts.put(key, font)
      font

case class LayoutTemplate(
  val page: PageTemplate = PageTemplate.A4(),
  val sections: List[SectionTemplate] = List.empty,
  val typeFaces: TypeFaces = TypeFaces(),
)
