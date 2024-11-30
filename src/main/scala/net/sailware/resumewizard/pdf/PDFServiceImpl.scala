package net.sailware.resumewizard.pdf

import java.io.File
import net.sailware.resumewizard.pdf.model.GeneratePDFRequest
import net.sailware.resumewizard.pdf.model.GeneratePDFResponse
import net.sailware.resumewizard.resume.Resume
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName
import org.slf4j.LoggerFactory

class PDFServiceImpl() extends PDFService:

  val logger = LoggerFactory.getLogger(classOf[PDFServiceImpl])

  override def generatePDF(request: GeneratePDFRequest): GeneratePDFResponse =

    val file = new File("resume.pdf")
    try {
      val document = new PDDocument()
      val page = new PDPage()
      val contentStream = new PDPageContentStream(document, page)
      contentStream.beginText()
      contentStream.setFont(new PDType1Font(FontName.TIMES_BOLD_ITALIC), 14)
      contentStream.newLineAtOffset(0, 0)
      contentStream.showText(request.resume.personalDetails.name)
      contentStream.endText()
      contentStream.close()
      document.addPage(page)
      document.save(file)
      document.close()
    } catch {
      case t: Throwable => logger.error("error generating PDF", t)
    }

    GeneratePDFResponse(file)
