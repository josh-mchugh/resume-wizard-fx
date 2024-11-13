package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.pdf.PDFService
import net.sailware.resumewizard.resume.ResumeService
import org.slf4j.LoggerFactory
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class PreviewPresenterImpl(
  val model: PreviewModel,
  val resumeService: ResumeService,
  val pdfService: PDFService
) extends PreviewPresenter:

  val logger = LoggerFactory.getLogger(classOf[PreviewPresenterImpl])

  def generateResumePDF(): Unit =
    logger.info("generating pdf...")
    Future {
      Thread.sleep(1000)
      pdfService.generatePDF(resumeService.getResume())
    } onComplete:
      case Success(file) => model.pdf.value = file
      case Failure(t) => logger.error("error generating PDF", t)
