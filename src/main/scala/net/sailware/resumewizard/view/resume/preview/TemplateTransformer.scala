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

    def createRows: (Float, Float, Position) => List[Row] = (parentWidth: Float, parentHeight: Float, startPosition: Position) =>
      var cursor: Position = startPosition
      var continue: Boolean = true

      val result = ListBuffer[Row]()

      while continue && rowIds.nonEmpty do

        val rowId = rowIds.front
        val template = templateMap(rowId)
        val width = parentWidth
        val height = if template.height > 0 then template.height else  parentHeight
        val contentStartPosition = ElementUtil.contentStartPosition(cursor, template.margin, template.padding, template.border)
        val columns = createColumns(rowId, width, height, contentStartPosition)
        continue = columns._2
        if continue then rowIds.dequeue()

        val currentCursor = cursor
        cursor = Position(cursor.x, cursor.y + height)

        result += Row(
          x = currentCursor.x,
          y = currentCursor.y,
          width = width,
          height = height,
          columns = columns._1
        )

      result.toList

    def createColumns: (String, Float, Float, Position) => (List[Column], Boolean) = (rowId: String, parentWidth: Float, parentHeight: Float, startPosition: Position) =>
      var cursor: Position = startPosition
      var continue: Boolean = true

      val result = ListBuffer[Column]()

      while continue && columnMap(rowId).nonEmpty do

        val columnId = columnMap(rowId).front
        val template = templateMap(columnId)
        val width = parentWidth
        val height = if template.height > 0 then template.height else parentHeight
        val contentStartPosition = ElementUtil.contentStartPosition(cursor, template.margin, template.padding, template.border)
        val content = createContent(ContentCreate(columnId, width, height, contentStartPosition))
        continue = content._2
        if continue then columnMap(rowId).dequeue()

        val currentCursor = cursor
        cursor = Position(cursor.x, cursor.y + height)

        result += Column(
          x = currentCursor.x,
          y = currentCursor.y,
          width = width,
          height = height,
          content = content._1
        )

      (result.toList, continue)

    val result = ListBuffer[Page]()

    while rowIds.nonEmpty do
      result += page.copy(rows = createRows(page.contentWidth(), page.contentHeight(), Position(page.contentStartX(), page.contentStartY())))

    result.toList

  def createContent(request: ContentCreate): (List[Content], Boolean) =
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

    (result.toList, contentMap(request.parentColumnId).isEmpty)

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
