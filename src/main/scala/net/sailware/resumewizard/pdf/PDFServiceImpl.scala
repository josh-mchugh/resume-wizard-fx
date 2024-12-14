package net.sailware.resumewizard.pdf

import java.io.File
import net.sailware.resumewizard.pdf.model.GeneratePDFRequest
import net.sailware.resumewizard.pdf.model.GeneratePDFResponse
import net.sailware.resumewizard.resume.Resume
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName
import org.slf4j.LoggerFactory

import java.awt.Color
import scala.collection.mutable.ListBuffer

class PDFServiceImpl() extends PDFService:

  val logger = LoggerFactory.getLogger(classOf[PDFServiceImpl])

  val primary = new Color(17, 33, 47)
  val white = new Color(255, 255, 255)

  def render(contentStream: PDPageContentStream, node: Node): Unit =
    node.section.content match
      case Some(content) =>
        content match
          case backgroundContent: BackgroundContent => processBackgroundContent(contentStream, backgroundContent)
          case textContent: TextContent => processTextContent(contentStream, node.getParentOffset(), textContent)
      case None => logger.info("No content for section: {}", node.section.id)
    if (node.children.nonEmpty) node.children.foreach(child => render(contentStream, child))

  override def generatePDF(request: GeneratePDFRequest): GeneratePDFResponse =

    val file = new File("resume.pdf")
    try {
      val document = new PDDocument()

      val robotoRegular = PDType0Font.load(document, new File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
      val robotoMedium = PDType0Font.load(document, new File(getClass.getResource("/font/Roboto-Medium.ttf").getPath))

      val page = new PDPage(PDRectangle.A4)

      val contentStream = new PDPageContentStream(document, page)

      val sections = createSections(document)
      val tree = createTree(sections)

      render(contentStream, tree)

      contentStream.close()

      document.addPage(page)
      document.save(file)
      document.close()
    } catch {
      case t: Throwable => logger.error("error generating PDF", t)
    }

    GeneratePDFResponse(file)


  private def addName(contentStream: PDPageContentStream, document: PDDocument, page: PDPage, font: PDFont): Unit =

    val fontSize = 22.5F
    val topOffset = page.getMediaBox().getHeight() - 64.5F

    contentStream.beginText()
    contentStream.setNonStrokingColor(white)
    contentStream.setFont(font, fontSize)
    contentStream.newLineAtOffset(24, topOffset)
    contentStream.showText("John Doe")
    contentStream.endText()

    // bottom border line stroke
    contentStream.setStrokingColor(white);
    contentStream.setLineWidth(1)
    contentStream.moveTo(24F, topOffset + getFontHeight(font, fontSize))
    contentStream.lineTo(24F + getStringWidth("John Doe", font, fontSize), topOffset + getFontHeight(font, fontSize))
    contentStream.closeAndStroke()

    // right border line stroke
    contentStream.setStrokingColor(white);
    contentStream.setLineWidth(1)
    contentStream.moveTo(24F + getStringWidth("John Doe", font, fontSize), topOffset)
    contentStream.lineTo(24F + getStringWidth("John Doe", font, fontSize), topOffset + getFontHeight(font, fontSize))
    contentStream.closeAndStroke()

    // bottom border line stroke
    contentStream.setStrokingColor(white);
    contentStream.setLineWidth(1)
    contentStream.moveTo(24F, topOffset)
    contentStream.lineTo(24F + getStringWidth("John Doe", font, fontSize), topOffset)
    contentStream.closeAndStroke()

    // left border line stroke
    contentStream.setStrokingColor(white);
    contentStream.setLineWidth(1)
    contentStream.moveTo(24F, topOffset)
    contentStream.lineTo(24F, topOffset + getFontHeight(font, fontSize))
    contentStream.closeAndStroke()


  private def addTitle(contentStream: PDPageContentStream, document: PDDocument, page: PDPage, font: PDFont): Unit =

    val fontSize = 10.5F

    contentStream.beginText()
    contentStream.setNonStrokingColor(white)
    contentStream.setFont(font, fontSize)
    contentStream.setCharacterSpacing(0.6F)
    contentStream.newLineAtOffset(24, page.getMediaBox().getHeight() - 64.5F - getFontHeight(font, 22.5F))
    contentStream.showText("Web and Graphics Designer")
    contentStream.endText()

  private def processTextContent(contentStream: PDPageContentStream, offset: (Float, Float),  content: TextContent): Unit =
    contentStream.beginText()
    contentStream.setNonStrokingColor(content.font.color)
    contentStream.setFont(content.font.font, content.font.size)
    contentStream.setCharacterSpacing(content.characterSpacing)
    contentStream.newLineAtOffset(offset._1, offset._2)
    contentStream.showText(content.text)
    contentStream.endText()

  private def processBackgroundContent(contentStream: PDPageContentStream, content: BackgroundContent): Unit =
    contentStream.setNonStrokingColor(content.color)
    contentStream.addRect(
      content.x,
      content.y,
      content.width,
      content.height,
    )
    contentStream.fill()

  private def getFontHeight(font: PDFont, size: Float): Float =
    font.getFontDescriptor().getCapHeight() * size / 1000F;

  private def getStringWidth(text: String, font: PDFont, size: Float): Float =
    text.codePoints()
      .mapToObj(codePoint => String(Array(codePoint), 0, 1))
      .map(codePoint =>
        try {
          font.getStringWidth(codePoint) * size / 1000F
        }
        catch {
          case e: IllegalArgumentException =>
            logger.error("Illegal argument for font width", e)
            font.getStringWidth("-") * size / 1000F
        }
      )
      .reduce(0.0F, (acc, value) => acc + value)

  private def createSections(document: PDDocument): Array[Section] =
    val regular = PDType0Font.load(document, new File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val medium = PDType0Font.load(document, new File(getClass.getResource("/font/Roboto-Medium.ttf").getPath))

    Array(
      Section(
        "LEFT_COLUMN",
        Option.empty,
        0,
        Padding(64.5F, 0F, 0F, 24F),
        Option(
          BackgroundContent(
            0,
            0,
            PDRectangle.A4.getWidth() * 0.347F,
            PDRectangle.A4.getHeight(),
            primary
          )
        ),
      ),
      Section(
        "NAME",
        Option("LEFT_COLUMN"),
        0,
        Padding(0F, 0F, 0F, 0F),
        Option(
          TextContent(
            Font(medium, 22.5F, white),
            "John Doe",
            0F
          )
        )
      ),
      Section(
        "TITLE",
        Option("LEFT_COLUMN"),
        1,
        Padding(0F, 0F, 0F, 0F),
        Option(
          TextContent(
            Font(regular, 10.5F, white),
            "Senior Web Designer",
            0.6F
          )
        )
      )
    )

  private def createChildren(parent: Node, sections: Array[Section]): List[Node] =
    sections.filter(section => section.parentId == Option(parent.section.id))
      .map(section =>
        val node = Node(Option(parent), section, List.empty)
        node.copy(children = createChildren(node, sections))
      )
      .toList

  private def createTree(sections: Array[Section]): Node =
    sections.find(section => section.parentId == None)
      .map(section =>
        val node = Node(None, section, List.empty)
        node.copy(children = createChildren(node, sections))
      )
      .head

case class Font(
  val font: PDFont,
  val size: Float,
  val color: Color
)

case class Padding(
  val top: Float = 0F,
  val right: Float = 0F,
  val bottom: Float = 0F,
  val left: Float = 0F
)

case class Section(
  val id: String,
  val parentId: Option[String],
  val order: Int,
  val padding: Padding,
  val content: Option[Content],
)

sealed trait Content

case class TextContent(
  val font: Font,
  val text: String,
  val characterSpacing: Float
) extends Content

case class BackgroundContent(
  val x: Float,
  val y: Float,
  val width: Float,
  val height: Float,
  val color: Color
) extends Content

sealed trait TreeNode

case class Node(
  val parent: Option[Node],
  val section: Section,
  val children: List[Node]
) extends TreeNode:
  def getParentOffset(): (Float, Float) =
    parent match
      case Some(node) =>
        val parentOffset = node.getParentOffset()
        (parentOffset._1 + section.padding.left, parentOffset._2 - section.padding.top)
      case None => (0F + section.padding._4, PDRectangle.A4.getHeight() - section.padding._1)

object EmptyNode extends TreeNode
