package net.sailware.resumewizard.pdf

import com.helger.pdflayout.PageLayoutPDF
import com.helger.pdflayout.base.PLColor
import com.helger.pdflayout.base.PLPageSet
import com.helger.pdflayout.element.box.PLBox
import com.helger.pdflayout.element.hbox.PLHBox
import com.helger.pdflayout.element.text.PLText
import com.helger.pdflayout.element.vbox.PLVBox
import com.helger.pdflayout.spec.FontSpec
import com.helger.pdflayout.spec.PreloadFont
import com.helger.pdflayout.spec.HeightSpec
import com.helger.pdflayout.spec.PaddingSpec
import com.helger.pdflayout.spec.PreloadFontManager
import com.helger.pdflayout.spec.WidthSpec
import com.helger.font.api.EFontStyle;
import com.helger.font.api.EFontType;
import com.helger.font.api.EFontWeight;
import com.helger.font.api.FontResource;
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
          case textContent: TextContent => processTextContent(contentStream, node, textContent)
      case None => logger.info("No content for section: {}", node.section.id)
    if (node.children.nonEmpty) node.children.foreach(child => render(contentStream, child))

  override def generatePDF(request: GeneratePDFRequest): GeneratePDFResponse =

    val file = new File("resume.pdf")
    try {
      //val document = new PDDocument()

      //val robotoRegular = PDType0Font.load(document, new File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
      //val robotoMedium = PDType0Font.load(document, new File(getClass.getResource("/font/Roboto-Medium.ttf").getPath))

      //val page = new PDPage(PDRectangle.A4)

      //val contentStream = new PDPageContentStream(document, page)

      //val sections = createSections(document)
      //val tree = createTree(sections)

      render2(file)

      //contentStream.close()

      //document.addPage(page)
      //document.save(file)
      //document.close()

      //logger.info("Tree: {}", tree)
    } catch {
      case t: Throwable => logger.error("error generating PDF", t)
    }

    GeneratePDFResponse(file)

  private def render2(file: File): Unit =
    // Fonts
    val fontManager = new PreloadFontManager(true);
    val robotoMediumResource = new FontResource("Roboto", EFontType.TTF, EFontStyle.REGULAR, EFontWeight.MEDIUM, "font/Roboto-Medium.ttf")
    val robotoRegularResource = new FontResource("Roboto", EFontType.TTF, EFontStyle.REGULAR, EFontWeight.REGULAR, "font/Roboto-Regular.ttf")
    val robotoMedium = fontManager.getOrAddEmbeddingPreloadFont(robotoMediumResource)
    val robotoRegular = fontManager.getOrAddEmbeddingPreloadFont(robotoRegularResource)

    // Name Section
    val name = new PLText("John Doe", new FontSpec(robotoMedium, 22.5F, new PLColor(255, 255, 255)))
    val nameBox = new PLBox()
      .setElement(name)

    // Title Section
    val title = new PLText("Web and Graphics Designer", new FontSpec(robotoRegular, 10.5F, new PLColor(255, 255, 255)))
    val titleBox = new PLBox()
      .setElement(title)

    // Left Column content
    val leftContent = new PLVBox()
      .addRow(nameBox)
      .addRow(titleBox)

    // Left side column
    val leftBox = new PLBox()
      .setFillColor(new PLColor(17, 33, 47))
      .setPadding(64.5F, 0F, 0F, 24F)
      .setElement(leftContent)

    val leftColumn = new PLVBox()
      .addRow(leftBox, HeightSpec.star())

    // Page root
    val root = new PLHBox()
      .addColumn(leftColumn, WidthSpec.perc(34.7))

    // Page set and Page layout
    val pageset = new PLPageSet(PDRectangle.A4)
      .addElement(root)

    val pageLayout = new PageLayoutPDF()
      .addPageSet(pageset)

    pageLayout.renderTo(file)

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
    contentStream.moveTo(24F, topOffset + FontUtil.getFontHeight(font, fontSize))
    contentStream.lineTo(24F + getStringWidth("John Doe", font, fontSize), topOffset + FontUtil.getFontHeight(font, fontSize))
    contentStream.closeAndStroke()

    // right border line stroke
    contentStream.setStrokingColor(white);
    contentStream.setLineWidth(1)
    contentStream.moveTo(24F + getStringWidth("John Doe", font, fontSize), topOffset)
    contentStream.lineTo(24F + getStringWidth("John Doe", font, fontSize), topOffset + FontUtil.getFontHeight(font, fontSize))
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
    contentStream.lineTo(24F, topOffset + FontUtil.getFontHeight(font, fontSize))
    contentStream.closeAndStroke()


  private def addTitle(contentStream: PDPageContentStream, document: PDDocument, page: PDPage, font: PDFont): Unit =

    val fontSize = 10.5F

    contentStream.beginText()
    contentStream.setNonStrokingColor(white)
    contentStream.setFont(font, fontSize)
    contentStream.setCharacterSpacing(0.6F)
    contentStream.newLineAtOffset(24, page.getMediaBox().getHeight() - 64.5F - FontUtil.getFontHeight(font, 22.5F))
    contentStream.showText("Web and Graphics Designer")
    contentStream.endText()

  private def processTextContent(contentStream: PDPageContentStream, node: Node, content: TextContent): Unit =
    contentStream.beginText()
    contentStream.setNonStrokingColor(content.font.color)
    contentStream.setFont(content.font.font, content.font.size)
    contentStream.setCharacterSpacing(content.characterSpacing)
    contentStream.newLineAtOffset(node.x, node.y)
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
        Padding(64.5F, 0F, 0F, 24F),
        0,
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
        Padding(0F, 0F, 0F, 0F),
        0,
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
        Padding(0F, 0F, 0F, 0F),
        1,
        Option(
          TextContent(
            Font(regular, 10.5F, white),
            "Senior Web Designer",
            0.6F
          )
        )
      )
    )

  private def windowS[A](l: List[A]): Iterator[List[Option[A]]] =
    (None :: l.map(Some(_)) ::: List(None)).sliding(3)

  private def createChildren(parent: Node, parentMap: Map[Option[String], Array[Section]]): List[Node] =
    if parentMap.contains(Option(parent.section.id)) then
      val childNodes = parentMap(Option(parent.section.id))
       .sortBy(_.order)
       .map(section => Node(Option(parent), None, section, List.empty))
       .toList

      windowS(childNodes)
        .map(group => group(1).get.copy(left = group(0), children = createChildren(group(1).get, parentMap)))
        .toList
    else
      List.empty

  private def createTree(sections: Array[Section]): Node =
    val parentMap = sections.groupBy(_.parentId)
    sections.filter(section => section.parentId == None)
      .sortBy(_.order)
      .map(section =>
        val node = Node(None, None, section, List.empty)
        node.copy(children = createChildren(node, parentMap))
      )
      .head

object FontUtil:
  def getFontHeight(font: PDFont, size: Float): Float =
    font.getFontDescriptor().getCapHeight() * size / 1000F;

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
  val padding: Padding,
  val order: Int,
  val content: Option[Content],
):
  def getContentHeight(): Float =
    content match
      case Some(content) => padding.top + content.getHeight() + padding.bottom
      case None => padding.top + padding.bottom

sealed trait Content:
  def getHeight(): Float

case class TextContent(
  val font: Font,
  val text: String,
  val characterSpacing: Float
) extends Content:
  override def getHeight(): Float =
    FontUtil.getFontHeight(font.font, font.size)

case class BackgroundContent(
  val x: Float,
  val y: Float,
  val width: Float,
  val height: Float,
  val color: Color
) extends Content:
  override def getHeight(): Float =
    0F

sealed trait TreeNode

case class Node(
  val parent: Option[Node],
  val left: Option[Node],
  val section: Section,
  val children: List[Node]
) extends TreeNode:

  val x: Float = calculateX()
  val y: Float = calculateY()

  private def calculateX(): Float =
    left match
      case Some(node) => node.x + node.section.padding.left
      case None => parent match
        case Some(node) => node.x + node.section.padding.left
        case None => 0F

  private def calculateY(): Float =
    left match
      case Some(node) => node.y - node.section.getContentHeight()
      case None => parent match
        case Some(node) => node.y - node.section.getContentHeight()
        case None => PDRectangle.A4.getHeight()

object EmptyNode extends TreeNode
