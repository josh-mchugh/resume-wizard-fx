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
    width = layout.page.width,
    height = layout.page.height,
    margin = layout.page.margin,
    padding = layout.page.padding,
    border = layout.page.border
  )

  // max y
  val maxY = page.paddingBoundingBox.bottomRight().y

  def transform(): List[Page] =
    val result = ListBuffer[Page]()

    while rowIds.nonEmpty do
      val request = RowCreate(page.contentWidth(), page.contentHeight(), ElementUtil.contentStartPosition(page))
      result += page.copy(rows = createRows(request))

    if result.isEmpty then List(page) else result.toList

  def createRows(request: RowCreate): List[Row] =
    var cursor: Position = request.start
    var continue: Boolean = true
    var remainingHeight = request.parentHeight

    val result = ListBuffer[Row]()

    while continue && rowIds.nonEmpty do

      val rowId = rowIds.front
      val section = sectionMap(rowId)
      val width = sectionWidth(section, request.parentWidth)
      val height = sectionHeight(section, remainingHeight)
      val continuableResults = createColumns(
        ColumnCreate(
          rowId,
          ElementUtil.contentWidth(width, section.margin, section.padding,section.border),
          ElementUtil.contentHeight(height, section.margin, section.padding, section.border),
          ElementUtil.contentStartPosition(cursor, section.margin, section.padding, section.border)
        )
      )
      continue = continuableResults.continue
      if continue then rowIds.dequeue()

      result += Row(
        position = cursor,
        width = width,
        height = height,
        padding = section.padding,
        margin = section.margin,
        border = section.border,
        background = section.background,
        columns = continuableResults.items
      )

      cursor = Position(cursor.x + sectionWidth(section), cursor.y + height)
      remainingHeight = remainingHeight - height

    result.toList

  def createColumns(request: ColumnCreate): ContinuableResults[Column] =
    var cursor: Position = request.start
    var continue: Boolean = true

    val result = ListBuffer[Column]()

    while continue && columnMap(request.parentRowId).nonEmpty do

      val columnId = columnMap(request.parentRowId).front
      val section = sectionMap(columnId)
      val width = sectionWidth(section, request.parentWidth)
      val height = sectionHeight(section, request.parentHeight)
      val continuableResults = createContent(
        ContentCreate(
          columnId,
          ElementUtil.contentWidth(width, section.margin, section.padding,section.border),
          ElementUtil.contentHeight(height, section.margin, section.padding, section.border),
          ElementUtil.contentStartPosition(cursor, section.margin, section.padding, section.border)
        )
      )
      continue = continuableResults.continue
      if continue then columnMap(request.parentRowId).dequeue()

      result += Column(
        position = cursor,
        width = width,
        height = height,
        padding = section.padding,
        margin = section.margin,
        border = section.border,
        background = section.background,
        content = continuableResults.items
      )

      cursor = Position(cursor.x + sectionWidth(section), cursor.y + sectionHeight(section))

    ContinuableResults(result.toList, continue)

  def createContent(request: ContentCreate): ContinuableResults[Content] =
    var cursor: Position = request.start

    val result = ListBuffer[Content]()

    while
      contentMap.contains(request.parentColumnId)
      && contentMap(request.parentColumnId).nonEmpty
      && cursor.y + sectionMap(contentMap(request.parentColumnId).front).height.getOrElse(0F) < maxY
    do

      val contentId = contentMap(request.parentColumnId).dequeue()
      val section = sectionMap(contentId)
      val width = sectionWidth(section, request.parentWidth)
      val height = sectionHeight(section, request.parentHeight)

      result += Content(
        position = cursor,
        width = width,
        height = height,
        padding = section.padding,
        margin = section.margin,
        border = section.border,
        background = section.background
      )

      cursor = Position(cursor.x + sectionWidth(section), cursor.y + height)

    val continue = !contentMap.contains(request.parentColumnId) || contentMap(request.parentColumnId).isEmpty
    ContinuableResults(result.toList, continue)

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

  private def sectionWidth(section: SectionTemplate, parentWidth: Float = 0F): Float =
    section.width.getOrElse(parentWidth)

  private def sectionHeight(section: SectionTemplate, parentHeight: Float = 0F): Float =
    section.height.getOrElse(parentHeight)

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
