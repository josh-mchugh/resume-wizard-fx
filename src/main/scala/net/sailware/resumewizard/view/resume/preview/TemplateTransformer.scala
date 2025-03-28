package net.sailware.resumewizard.view.resume.preview

import collection.mutable.ListBuffer
import collection.mutable.HashMap
import collection.mutable.Map
import collection.mutable.Queue
import net.sailware.resumewizard.resume.Resume
import org.slf4j.LoggerFactory
import scalafx.scene.paint.Color
import java.awt.Font
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform

class TemplateTransformer(resume: Resume, layout: LayoutTemplate):

  private val logger = LoggerFactory.getLogger(classOf[TemplateTransformer])

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
      val height = sectionHeight(section)
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
      val height = sectionHeight(section)
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
    logBreak("*")
    logger.info("creating content for: {}", request)
    var cursor: Cursor[Content] = Cursor(x = request.start.x, y = request.start.y)
    val result = ListBuffer[Content]()

    while
      contentMap.contains(request.parentColumnId)
      && contentMap(request.parentColumnId).nonEmpty
      && sectionLookAhead(cursor)
    do
      logBreak("-")

      // Retrieves or creates Content and adds it to the result list
      val contentId = contentMap(request.parentColumnId).dequeue()
      val section = sectionMap(contentId)
      val content = cursor.next. match
        case Some(content) =>
          logger.info("Content provided by Cursor")
          logger.info("content id: {}", contentId)
          content
        case None =>
          val content = createContent(section, cursor.x, cursor.y, request)
          logger.info("Content not provided, creating content")
          logger.info("content id: {}", contentId)
          logger.info("content x: {}", content.x)
          logger.info("content y: {}", content.y)
          logger.info("content width: {}", content.width)
          logger.info("content.height: {}", content.height)
          content

      result += content

      // Generates the next Content to determine if the 'While' loop should continue
      logBreak("+")
      logger.info("Preparing next content")

      val nextContentId = contentMap(request.parentColumnId).headOption
      val nextContent = nextContentId.map(id =>
        val nextSection = sectionMap(id)
        Some(createContent(nextSection, cursor.x, cursor.y + content.height, request))
      ).getOrElse(None)

      val nextCursorState = if nextContent.isDefined then CursorState.Process else CursorState.Complete
      val nextCursorX = cursor.x + sectionWidth(section)
      val nextCursorY = cursor.y + content.height

      logger.info("Next content generated? {}", nextContent.isDefined)
      nextContent.foreach(nextContent =>
        logger.info("next content id: {}", nextContentId.get)
        logger.info("next content x: {}", nextContent.x)
        logger.info("next content y: {}", nextContent.y)
        logger.info("next content width: {}", nextContent.width)
        logger.info("next content height: {}", nextContent.height)
      )

      logger.info("next cursor ( x: {}, y: {} )", nextCursorX, nextCursorY)

      cursor = Cursor(nextCursorState, nextCursorX, nextCursorY, nextContent)

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

  private def sectionHeight(section: SectionTemplate): Float =
    sectionHeight(section, section.contentTemplate.map(_.size).getOrElse(0F))

  private def sectionHeight(section: SectionTemplate, contentHeight: Float): Float =
    val calculated = section.margin.top
      + section.border.width
      + section.padding.top
      + contentHeight
      + section.padding.bottom
      + section.border.width
      + section.margin.bottom

    section.minHeight
      .map(height => if calculated < height then height else calculated)
      .getOrElse(calculated)

  private def sectionLookAhead(cursor: Cursor[Content]): Boolean =
    CursorState.Complete != cursor.state && cursor.y + cursor.next.map(_.height).getOrElse(0F) < maxY

  private def createContent(section: SectionTemplate, x: Float, y: Float, request: ContentCreate): Content =

    // calculate the content element's width and height
    val width = sectionWidth(section, request.parentWidth)
      - section.margin.left
      - section.border.width
      - section.padding.left
      - section.padding.right
      - section.border.width
      - section.margin.right

    // get the section template's content
    val contentItem = sectionContent(section, width)

    val height = sectionHeight(section, contentItem.map(content => content.text.length * content.size).getOrElse(0F))

    Content(
      position = Position(x, y),
      width = width,
      height = height,
      padding = section.padding,
      margin = section.margin,
      border = section.border,
      background = section.background,
      item = contentItem
    )

  private def sectionContent(section: SectionTemplate, width: Float): Option[ContentItem] =
    val resumeContent = (resumeDataType: ResumeDataType) =>
      resumeDataType match
        case ResumeDataType.Name => resume.personalDetails.name
        case ResumeDataType.Title => resume.personalDetails.title
        case ResumeDataType.Summary => resume.personalDetails.summary

    section.contentTemplate
      .map(content =>
        val text = content.resumeDataType
         .map(resumeContent(_))
         .getOrElse("")

        val font = content.weight match
          case Some(weight) =>
            weight match
              case ResumeFontWeight.Normal => Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/Roboto-Regular.ttf"))
              case ResumeFontWeight.Bold => Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/Roboto-Bold.ttf"))
              case ResumeFontWeight.Medium => Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/Roboto-Medium.ttf"))
          case None => new Font("Arial", Font.PLAIN, content.size.toInt)
        val sizedFont= font.deriveFont(content.size)
        val fontRC = new FontRenderContext(new AffineTransform(), true, true)
        var calcWidth = 0D
        val trimmedText = text.trim()
        val splitWords = trimmedText.split("((?=\\s+)|(?<=\\s+))")
        val linesBuffer = ListBuffer[String]()
        val wordsBuffer = ListBuffer[String]()
        for word <- splitWords do
          val bounds = sizedFont.getStringBounds(word.toString(), fontRC)
          val testWidth = calcWidth + bounds.getWidth()
          if testWidth < width then
            wordsBuffer += word
            calcWidth += bounds.getWidth()
          else
            linesBuffer += wordsBuffer.toList.mkString
            wordsBuffer.clear()
            wordsBuffer += word
            calcWidth = bounds.getWidth()
        linesBuffer += wordsBuffer.toList.mkString

        val lines = linesBuffer.toList
        val line = lines.head

        logger.info("Width of text '{}': {}", line, sizedFont.getStringBounds(line, fontRC).getWidth())

        Some(ContentItem(lines, content.size, content.color, content.family, content.weight))
      )
      .getOrElse(None)

  private def logBreak(pattern: String): Unit =
    logger.info(pattern * 50)

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
  case Process, Complete

case class Cursor[T](
  state: CursorState = CursorState.Process,
  x: Float = 0F,
  y: Float = 0F,
  next: Option[T] = None
)
