package net.sailware.resumewizard.pdf

import java.io.File
import net.sailware.resumewizard.pdf.model.GeneratePDFRequest
import net.sailware.resumewizard.pdf.model.GeneratePDFResponse
import net.sailware.resumewizard.resume.Resume
import net.sailware.resumewizard.template.BackgroundContent
import net.sailware.resumewizard.template.FontUtil
import net.sailware.resumewizard.template.Node
import net.sailware.resumewizard.template.Section
import net.sailware.resumewizard.template.TestData
import net.sailware.resumewizard.template.TextContent
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.slf4j.LoggerFactory

class PDFServiceImpl() extends PDFService:

  val logger = LoggerFactory.getLogger(classOf[PDFServiceImpl])

  override def generatePDF(request: GeneratePDFRequest): GeneratePDFResponse =

    val file = new File("resume.pdf")
    try {
      val document = new PDDocument()

      val page = new PDPage(PDRectangle.A4)

      val contentStream = new PDPageContentStream(document, page)

      val tree = Node(TestData.sections(document))

      render(contentStream, tree)

      contentStream.close()

      document.addPage(page)
      document.save(file)
      document.close()

      logger.info("Tree: {}", tree)
      logger.info("Page A4: {}", PDRectangle.A4)
    } catch {
      case t: Throwable => logger.error("error generating PDF", t)
    }

    GeneratePDFResponse(file)

  def render(contentStream: PDPageContentStream, node: Node): Unit =
    node.section.content match
      case Some(content) =>
        content match
          case backgroundContent: BackgroundContent => processBackgroundContent(contentStream, backgroundContent)
          case textContent: TextContent => processTextContent(contentStream, node, textContent)
      case None => logger.info("No content for section: {}", node.section.id)
    if (node.children.nonEmpty) node.children.foreach(child => render(contentStream, child))

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
