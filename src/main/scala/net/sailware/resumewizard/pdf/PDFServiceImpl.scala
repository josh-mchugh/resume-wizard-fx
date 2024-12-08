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
      val robotoMedium = PDType0Font.load(document, new File(getClass.getResource("/font/Roboto-Medium.ttf").getPath))
      val robotoBold = PDType0Font.load(document, new File(getClass.getResource("/font/Roboto-Bold.ttf").getPath))

      val page = new PDPage(PDRectangle.A4)

      val contentStream = new PDPageContentStream(document, page)

      addLeftBackground(contentStream, page)
      addName(contentStream, document, page, robotoMedium)
      addTitle(contentStream, document, page, robotoRegular)

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
