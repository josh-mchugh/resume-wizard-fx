package net.sailware.resumewizard.view.resume.preview

import collection.mutable.ListBuffer
import collection.mutable.HashMap
import collection.mutable.Map
import collection.mutable.Queue
import net.sailware.resumewizard.resume.Resume
import scalafx.scene.paint.Color

class TemplateTransformer(resume: Resume, layout: LayoutTemplate):

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
    var cursor: Cursor[Content] = Cursor(x = request.start.x, y = request.start.y)
    val result = ListBuffer[Content]()

    while
      contentMap.contains(request.parentColumnId)
      && contentMap(request.parentColumnId).nonEmpty
      && sectionLookAhead(cursor)
    do

      if cursor.next.isEmpty then

        // remove template id from queue and get section template
        val contentId = contentMap(request.parentColumnId).dequeue()
        val section = sectionMap(contentId)

        result += createContent(section, cursor, request)

        val nextContentId = contentMap(request.parentColumnId).headOption
        val nextContent = nextContentId.map(id =>
          val nextSection = sectionMap(id)
          Some(createContent(nextSection, cursor, request))
        ).getOrElse(None)

        cursor = Cursor(
          state = if nextContent.isDefined then CursorState.Progress else CursorState.Complete,
          x = cursor.x + sectionWidth(section),
          y = cursor.y + sectionHeight(section),
          next = nextContent
        )

      else

        val content = cursor.next.get
        result += content

        val contentId = contentMap(request.parentColumnId).dequeue()
        val section = sectionMap(contentId)

        val nextContentId = contentMap(request.parentColumnId).headOption
        val nextContent = nextContentId.map(id =>
          val nextSection = sectionMap(id)
          Some(createContent(nextSection, cursor, request))
        ).getOrElse(None)

        cursor = Cursor(
          state = if nextContent.isDefined then CursorState.Progress else CursorState.Complete,
          x = cursor.x + sectionWidth(section),
          y = cursor.y + sectionHeight(section),
          next = nextContent
        )

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

  private def createContent(section: SectionTemplate, cursor: Cursor[Content], request: ContentCreate): Content =

    // get the section template's content
    val contentItem = sectionContent(section)

    // calculate the content element's width and height
    val width = sectionWidth(section, request.parentWidth)
    val height = sectionHeight(section, request.parentHeight)

    Content(
      position = Position(cursor.x, cursor.y),
      width = width,
      height = height,
      padding = section.padding,
      margin = section.margin,
      border = section.border,
      background = section.background,
      item = contentItem
    )

  private def sectionLookAhead(cursor: Cursor[Content]): Boolean =
    CursorState.Complete != cursor.state && cursor.y + cursor.next.map(_.height).getOrElse(0F) < maxY

  private def sectionWidth(section: SectionTemplate, parentWidth: Float = 0F): Float =
    section.width.getOrElse(parentWidth)

  private def sectionHeight(section: SectionTemplate, parentHeight: Float = 0F): Float =
    section.height.getOrElse(parentHeight)

  private def sectionContent(section: SectionTemplate): Option[ContentItem] =
    val resumeContent = (resumeDataType: ResumeDataType) =>
      resumeDataType match
        case ResumeDataType.Name => resume.name

    section.contentTemplate
      .map(content =>
        val text = content.resumeDataType
         .map(resumeContent(_))
         .getOrElse("")
        Some(ContentItem(text, content.size, content.color))
      )
      .getOrElse(None)

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

enum CursorState:
  case Progress, Complete

case class Cursor[T](
  state: CursorState = CursorState.Process,
  x: Float = 0F,
  y: Float = 0F,
  next: Option[T] = None
)
