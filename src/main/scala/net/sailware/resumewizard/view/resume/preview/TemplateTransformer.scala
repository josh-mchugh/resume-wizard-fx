package net.sailware.resumewizard.view.resume.preview

import collection.mutable.ListBuffer
import collection.mutable.HashMap
import collection.mutable.Map
import collection.mutable.Queue

class TemplateTransformer(layout: LayoutTemplate):

  val sectionMap: Map[String, SectionTemplate] = sectionsGroupById(layout.sections)
  val rowIds: Queue[String] = sectionIds(layout.sections, SectionType.Row)
  val columnMap: Map[String, Queue[String]] = sectionIdsGroupByParentId(layout.sections, SectionType.Column)
  val contentMap: Map[String, Queue[String]] = sectionIdsGroupByParentId(layout.sections, SectionType.Content)

  // create page from root
  val page: Page = Page(
    padding = layout.page.padding
  )

  // max y
  val maxY = page.paddingBoundingBox.bottomRight().y

  def transform(): List[Page] =
    val result = ListBuffer[Page]()

    while rowIds.nonEmpty do
                              //TODO Remove this Point conversion
      val request = RowCreate(Point(page.contentWidth()), page.contentHeight(), ElementUtil.contentStartPosition(page))
      result += page.copy(rows = createRows(request))

    result.toList

  def createRows(request: RowCreate): List[Row] =
    var cursor: Position = request.start
    var continue: Boolean = true

    val result = ListBuffer[Row]()

    while continue && rowIds.nonEmpty do

      val rowId = rowIds.front
      val section = sectionMap(rowId)
      val width = sectionWidth(section, request.parentWidth)
      val height = if section.height > 0 then section.height else request.parentHeight
      val contentStartPosition = ElementUtil.contentStartPosition(cursor, section.margin, section.padding, section.border)
      val continuableResults = createColumns(ColumnCreate(rowId, width, height, contentStartPosition))
      continue = continuableResults.continue
      if continue then rowIds.dequeue()

      result += Row(
        position = cursor,
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
      val section = sectionMap(columnId)
      val width = sectionWidth(section, request.parentWidth)
      val height = if section.height > 0 then section.height else request.parentHeight
      val contentStartPosition = ElementUtil.contentStartPosition(cursor, section.margin, section.padding, section.border)
      val continuableResults = createContent(ContentCreate(columnId, width, height, contentStartPosition))
      continue = continuableResults.continue
      if continue then columnMap(request.parentRowId).dequeue()

      result += Column(
        position = cursor,
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
      val section = sectionMap(contentId)
      val width = sectionWidth(section, request.parentWidth)
      val height = if section.height > 0 then section.height else request.parentHeight

      result += Content(
        position = cursor,
        width = width,
        height = height,
        margin = section.margin,
        background = section.background
      )

      cursor = Position(cursor.x, cursor.y + height)

    ContinuableResults(result.toList, contentMap(request.parentColumnId).isEmpty)

  def sectionIds(sections: List[SectionTemplate], `type`: SectionType): Queue[String] =
    val rowIds = sections
      .filter(_.`type` == `type`)
      .sortBy(_.order)
      .map(_.id)

    Queue.from(rowIds)

  def sectionsGroupById(sections: List[SectionTemplate]): Map[String, SectionTemplate] =
    val map = HashMap[String, SectionTemplate]()
    sections.foreach(section => map.addOne(section.id, section))
    map

  def sectionIdsGroupByParentId(sections: List[SectionTemplate], `type`: SectionType): Map[String, Queue[String]] =
    val map = HashMap[String, Queue[String]]()
    sections
      .filter(_.`type` == `type`)
      .groupBy(_.parentId)
      .foreach((key, values) => map(key.getOrElse("")) = Queue.from(values.sortBy(_.order).map(_.id)))
    map

  private def sectionWidth(section: SectionTemplate, parentWidth: Point = Point(0F)): Point =
    section.width.getOrElse(parentWidth)

case class ContentCreate(
  val parentColumnId: String,
  val parentWidth: Point,
  val parentHeight: Float,
  val start: Position
)

case class ColumnCreate(
  val parentRowId: String,
  val parentWidth: Point,
  val parentHeight: Float,
  val start: Position
)

case class RowCreate(
  val parentWidth: Point,
  val parentHeight: Float,
  val start: Position
)

case class ContinuableResults[T](
  val items: List[T],
  val continue: Boolean
)
