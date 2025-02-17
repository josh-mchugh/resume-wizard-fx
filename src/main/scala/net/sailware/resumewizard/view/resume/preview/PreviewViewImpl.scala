package net.sailware.resumewizard.view.resume.preview

import scalafx.Includes.*
import scalafx.scene.Node
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import org.slf4j.LoggerFactory
import scalafx.geometry.Pos
import scala.compiletime.ops.double

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  val logger = LoggerFactory.getLogger(classOf[PreviewViewImpl])

  override def view(): Node =
    model.resume.onInvalidate { resume => logger.info("resume: {}", resume) }
    val pages = List(Data().longPage())
    val canvases = pages.map(page =>
      val result = new Canvas(793.7007874F, 1122.519685F)
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
    gc.fillRect(element.contentStartX(), element.contentStartY(), element.contentWidth(), element.contentHeight())

  private def renderPage(page: Page, gc: GraphicsContext): Unit =
    val debug = true
    gc.setFill(Color.WHITE)
    gc.fillRect(page.x, page.x, page.width, page.height)
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
  def top(): Float = element.y + element.margin.top + (element.border.width / 2F)
  def right(): Float = element.x + element.width - element.margin.right - (element.border.width / 2F)
  def bottom(): Float = element.y + element.height - element.margin.bottom - (element.border.width / 2F)
  def left(): Float = element.x + element.margin.left + (element.border.width / 2F)


case class MarginBoundingBox(
  element: Element
) extends BoundingBox:
  def top(): Float = element.y
  def left(): Float = element.x
  def bottom(): Float = element.y + element.height
  def right(): Float = element.x + element.width

case class PaddingBoundingBox(
  element: Element
) extends BoundingBox:
  def top(): Float = element.y + element.margin.top + element.border.width + element.padding.top
  def right(): Float = element.x + element.width - element.margin.right - element.border.width - element.padding.right
  def bottom(): Float = element.y + element.height - element.margin.bottom - element.border.width - element.padding.bottom
  def left(): Float = element.x + element.margin.left + element.border.width + element.padding.left

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
) extends RenderElement

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
) extends RenderElement

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
) extends RenderElement

case class Content(
  val x: Float,
  val y: Float,
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
  def borderTestTemplate(): Page =
    Page(
      margin = Margin(100F, 50F, 100F, 50F),
      padding = Padding(100F, 50F, 100F, 50F),
      border = Border(width = 5F)
    )

  /**
    * Creates a two row page with 3 columns in each row
    */
  def twoRowSixColumnTemplate(): Page =
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

  /**
    * Long page, it's a test to push the contents beyond the Page content max height
    */
  def longPage(): Page =

    // page root template
    val root: Template = templateRoot()

    // template map with id as key and template as value
    val templateMap: collection.mutable.Map[String, Template] = templateById()

    // list of row ids in order
    val rowIds: List[String] = templateRowIds()

    // map of columns with key being row id and list of column ids in order
    val columnRowMap: collection.mutable.Map[String, List[String]] = templateColumnByRowIdMap()

    // map of content with key being the column id and list of the content ids in order
    val contentColumnMap: collection.mutable.Map[String, List[String]] = templateContentByColumnIdMap()

    // create page from root
    val page: Page = Page(
      padding = root.padding
    )

    // max y
    val maxY = page.paddingBoundingBox.bottomRight().y

    def createRows: (Float, Float, Position) => List[Row] = (parentWidth: Float, parentHeight: Float, startPosition: Position) =>
      var cursor: Position = startPosition
      for rowId <- rowIds yield
        val template = templateMap(rowId)
        val row = Row(
          x = cursor.x,
          y = cursor.y,
          width = parentWidth,
          height = if template.height > 0 then template.height else  parentHeight
        )
        cursor = row.marginBoundingBox.bottomLeft()

        row.copy(columns = createColumns(rowId, row.width, row.height, Position(row.contentStartX(), row.contentStartY())))

    def createColumns: (String, Float, Float, Position) => List[Column] = (rowId: String, parentWidth: Float, parentHeight: Float, startPosition: Position) =>
      var cursor: Position = startPosition
      for columnId <- columnRowMap(rowId) yield
        val template = templateMap(columnId)
        val column = Column(
          x = cursor.x,
          y = cursor.y,
          width = parentWidth,
          height = if template.height > 0 then template.height else parentHeight
        )
        cursor = column.marginBoundingBox.topRight()

        column.copy(content = createContent(columnId, column.contentWidth(), column.contentHeight(), Position(column.contentStartX(), column.contentStartY())))

    def createContent: (String, Float, Float, Position) => List[Content] = (columnId: String, parentWidth: Float, parentHeight: Float, startPosition: Position) =>
      var cursor: Position = startPosition
      for contentId <- contentColumnMap(columnId) yield
        if cursor.y > maxY then println("Exceeding max y")
        val template = templateMap(contentId)
        val content = Content(
          x = cursor.x,
          y = cursor.y,
          width = parentWidth,
          height = if template.height > 0 then template.height else parentHeight,
          margin = template.margin,
          background = template.background
        )
        cursor = content.marginBoundingBox.bottomLeft()
        content

    page.copy(rows = createRows(page.contentWidth(), page.contentHeight(), Position(page.contentStartX(), page.contentStartY())))

  def templateRoot(): Template =
    Template(
      id = "PAGE_ROOT",
      parentId = None,
      `type` = TemplateType.Page,
      height = 0F,
      order = 0,
      padding = Padding(50F, 50F, 50F, 50F)
    )

  def templateById(): collection.mutable.Map[String, Template] =
    val map = collection.mutable.HashMap[String, Template]()
    templateData().foreach(template => map.addOne(template.id, template))
    map

  def templateRowIds(): List[String] =
    templateData()
      .filter(template => template.`type` == TemplateType.Row)
      .sortBy(_.order)
      .map(_.id)

  def templateColumnByRowIdMap(): collection.mutable.Map[String, List[String]] =
    val map = collection.mutable.HashMap[String, List[String]]()
    templateData()
      .filter(template => template.`type` == TemplateType.Column)
      .groupBy(_.parentId)
      .foreach((key, values) => map.addOne(key.getOrElse(""), values.sortBy(_.order).map(_.id)))
    map

  def templateContentByColumnIdMap(): collection.mutable.Map[String, List[String]] =
    val map = collection.mutable.HashMap[String, List[String]]()
    templateData()
      .filter(template => template.`type` == TemplateType.Content)
      .groupBy(_.parentId)
      .foreach((key, values) => map.addOne(key.getOrElse(""), values.sortBy(_.order).map(_.id)))
    map

  def templateData(): List[Template] =
    List(
      Template(
        id = "ROW",
        parentId = Some("PAGE_ROOT"),
        `type` = TemplateType.Row,
        height = 0F,
        order = 1,
      ),
      Template(
        id = "COLUMN",
        parentId = Some("ROW"),
        `type` = TemplateType.Column,
        height = 0F,
        order = 1,
      ),
      Template(
        id ="CONTENT_1",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 1,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_2",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 2,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_3",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 3,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_4",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 4,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_5",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 5,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_6",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 6,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_7",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 7,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_8",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 8,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_9",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 9,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_10",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 10,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_11",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 11,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_12",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 12,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_13",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 13,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_14",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 14,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_15",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 15,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_16",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 16,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_17",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 17,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_18",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 18,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
    )

enum TemplateType:
  case Page, Row, Column, Content

case class Template(
  val id: String,
  val parentId: Option[String],
  val `type`: TemplateType,
  val height: Float,
  val order: Int,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Border = Border(),
  val background: Background = Background(),
)
