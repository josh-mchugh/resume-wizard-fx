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

import java.awt.*

class PDFServiceImpl() extends PDFService:

  val logger = LoggerFactory.getLogger(classOf[PDFServiceImpl])

  val primary = new Color(17, 33, 47)
  val white = new Color(255, 255, 255)


  override def generatePDF(request: GeneratePDFRequest): GeneratePDFResponse =

    val file = new File("resume.pdf")
    try {

      val document = new PDDocument()

      val robotoRegular = PDType0Font.load(document, new File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))

      val page = new PDPage(PDRectangle.A4)

      val contentStream = new PDPageContentStream(document, page)

      addLeftBackground(contentStream, page)
      addResumeName(contentStream, document, page, robotoRegular)

      contentStream.close()

      document.addPage(page)
      document.save(file)
      document.close()
    } catch {
      case t: Throwable => logger.error("error generating PDF", t)
    }

    GeneratePDFResponse(file)

  private def addLeftBackground(contentStream: PDPageContentStream, page: PDPage): Unit =

    contentStream.setNonStrokingColor(primary)

    contentStream.addRect(
      0,
      0,
      page.getMediaBox().getWidth() * 0.347F,
      page.getMediaBox().getHeight()
    )

    contentStream.fill()


  private def addResumeName(contentStream: PDPageContentStream, document: PDDocument, page: PDPage, font: PDFont): Unit =

    contentStream.beginText()
    contentStream.setNonStrokingColor(white)
    contentStream.setFont(font, 22.5F)
    contentStream.newLineAtOffset(24, page.getMediaBox().getHeight() - 42)
    contentStream.showText("Josh McHugh")
    contentStream.endText()
    contentStream.setCharacterSpacing(0)
