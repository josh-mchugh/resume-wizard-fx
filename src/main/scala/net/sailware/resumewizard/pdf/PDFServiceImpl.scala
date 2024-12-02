package net.sailware.resumewizard.pdf

import java.io.File
import net.sailware.resumewizard.pdf.model.GeneratePDFRequest
import net.sailware.resumewizard.pdf.model.GeneratePDFResponse
import net.sailware.resumewizard.resume.Resume
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName
import org.slf4j.LoggerFactory
import org.vandeseer.easytable.TableDrawer
import org.vandeseer.easytable.settings.BorderStyle
import org.vandeseer.easytable.settings.HorizontalAlignment
import org.vandeseer.easytable.structure.Row
import org.vandeseer.easytable.structure.Table
import org.vandeseer.easytable.structure.cell.TextCell

import java.awt.*

class PDFServiceImpl() extends PDFService:

  val logger = LoggerFactory.getLogger(classOf[PDFServiceImpl])

  override def generatePDF(request: GeneratePDFRequest): GeneratePDFResponse =

    val file = new File("resume.pdf")
    try {
      val document = new PDDocument()
      val page = new PDPage(PDRectangle.A4)
      val contentStream = new PDPageContentStream(document, page)

      addLeftBackground(contentStream, page)

      val myTable = Table.builder()
        .addColumnsOfWidth(page.getMediaBox().getWidth() * 0.3F, page.getMediaBox().getWidth() * 0.7F)
        .padding(0)
        .addRow(Row.builder()
          .add(TextCell.builder().text("One One").borderWidth(4).borderColorLeft(Color.MAGENTA).backgroundColor(Color.WHITE).build())
          .add(TextCell.builder().text("One Two").borderWidth(0).backgroundColor(Color.YELLOW).build())
          .build())
        .addRow(Row.builder()
          .padding(10)
          .add(TextCell.builder().text("Two One").textColor(Color.RED).build())
          .add(TextCell.builder().text("Two Two")
            .borderWidthRight(1f)
            .borderStyleRight(BorderStyle.DOTTED)
            .horizontalAlignment(HorizontalAlignment.RIGHT)
            .build())
          .build())
        .build()

      // Set up the drawer
      val tableDrawer = TableDrawer.builder()
        .contentStream(contentStream)
        .startX(20f)
        .startY(page.getMediaBox().getUpperRightY() - 20f)
        .table(myTable)
        .build()

      // And go for it!
      tableDrawer.draw()

      contentStream.close()

      document.addPage(page)
      document.save(file)
      document.close()
    } catch {
      case t: Throwable => logger.error("error generating PDF", t)
    }

    GeneratePDFResponse(file)

  private def addLeftBackground(contentStream: PDPageContentStream, page: PDPage): Unit =
    contentStream.setNonStrokingColor(new Color(17, 33, 47))

    contentStream.addRect(
      0,
      0,
      page.getMediaBox().getWidth() * 0.347F,
      page.getMediaBox().getHeight()
    )

    contentStream.fill()
