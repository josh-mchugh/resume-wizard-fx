package net.sailware.resumewizard.view.resume.preview

import collection.mutable.ListBuffer
import collection.mutable.HashMap
import collection.mutable.Map
import collection.mutable.Queue

class TemplateTransformer(templates: List[Template]):

  val root: Template = templateRoot(templates)
  val templateMap: Map[String, Template] = templatesGroupById(templates)
  val rowIds: Queue[String] = templateIds(templates, TemplateType.Row)
  val columnMap: Map[String, Queue[String]] = templateIdsGroupByParentId(templates, TemplateType.Column)
  val contentMap: Map[String, Queue[String]] = templateIdsGroupByParentId(templates, TemplateType.Content)

  // create page from root
  val page: Page = Page(
    padding = root.padding
  )

  // max y
  val maxY = page.paddingBoundingBox.bottomRight().y

  def transform(): List[Page] =
    val result = ListBuffer[Page]()

    while rowIds.nonEmpty do
      val start = Position(page.contentStartX(), page.contentStartY())
      val request = RowCreate(page.contentWidth(), page.contentHeight(), start)
      result += page.copy(rows = createRows(request))

    result.toList

  def createRows(request: RowCreate): List[Row] =
    var cursor: Position = request.start
    var continue: Boolean = true

    val result = ListBuffer[Row]()

    while continue && rowIds.nonEmpty do

      val rowId = rowIds.front
      val template = templateMap(rowId)
      val width = request.parentWidth
      val height = if template.height > 0 then template.height else request.parentHeight
      val contentStartPosition = ElementUtil.contentStartPosition(cursor, template.margin, template.padding, template.border)
      val continuableResults = createColumns(ColumnCreate(rowId, width, height, contentStartPosition))
      continue = continuableResults.continue
      if continue then rowIds.dequeue()

      result += Row(
        x = cursor.x,
        y = cursor.y,
        width = width,
        height = height,
        columns = continuableResults.items
      )

      cursor = Position(cursor.x, cursor.y + height)

    result.toList

  def createColumns(request: ColumnCreate): ContinuableResults[Column] =
    var cursor: Position = request.start
    var continue: Boolean = true

    val result = ListBuffer[Column]()

    while continue && columnMap(request.parentRowId).nonEmpty do

      val columnId = columnMap(request.parentRowId).front
      val template = templateMap(columnId)
      val width = request.parentWidth
      val height = if template.height > 0 then template.height else request.parentHeight
      val contentStartPosition = ElementUtil.contentStartPosition(cursor, template.margin, template.padding, template.border)
      val continuableResults = createContent(ContentCreate(columnId, width, height, contentStartPosition))
      continue = continuableResults.continue
      if continue then columnMap(request.parentRowId).dequeue()

      result += Column(
        x = cursor.x,
        y = cursor.y,
        width = width,
        height = height,
        content = continuableResults.items
      )

      cursor = Position(cursor.x, cursor.y + height)

    ContinuableResults(result.toList, continue)

  def createContent(request: ContentCreate): ContinuableResults[Content] =
    var cursor: Position = request.start

    val result = ListBuffer[Content]()

    while cursor.y < maxY && contentMap(request.parentColumnId).nonEmpty do

      val contentId = contentMap(request.parentColumnId).dequeue()
      val template = templateMap(contentId)
      val width = request.parentWidth
      val height = if template.height > 0 then template.height else request.parentHeight

      result += Content(
        x = cursor.x,
        y = cursor.y,
        width = width,
        height = height,
        margin = template.margin,
        background = template.background
      )

      cursor = Position(cursor.x, cursor.y + height)

    ContinuableResults(result.toList, contentMap(request.parentColumnId).isEmpty)

  def templateRoot(templates: List[Template]): Template =
    templates.find(template => template.parentId == None)
      .getOrElse(throw new Exception("Templates must have a root page node"))

  def templateIds(templates: List[Template], `type`: TemplateType): Queue[String] =
    val rows = templates
      .filter(_.`type` == `type`)
      .sortBy(_.order)
      .map(_.id)

    Queue.from(rows)

  def templatesGroupById(templates: List[Template]): Map[String, Template] =
    val map = HashMap[String, Template]()
    templates.foreach(template => map.addOne(template.id, template))
    map

  def templateIdsGroupByParentId(templates: List[Template], `type`: TemplateType): Map[String, Queue[String]] =
    val map = HashMap[String, Queue[String]]()
    templates
      .filter(_.`type` == `type`)
      .groupBy(_.parentId)
      .foreach((key, values) => map(key.getOrElse("")) = Queue.from(values.sortBy(_.order).map(_.id)))
    map

case class ContentCreate(
  val parentColumnId: String,
  val parentWidth: Float,
  val parentHeight: Float,
  val start: Position
)

case class ColumnCreate(
  val parentRowId: String,
  val parentWidth: Float,
  val parentHeight: Float,
  val start: Position
)

case class RowCreate(
  val parentWidth: Float,
  val parentHeight: Float,
  val start: Position
)

case class ContinuableResults[T](
  val items: List[T],
  val continue: Boolean
)
