package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.view.resume.preview.service.PreviewService
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scalafx.application.Platform

class PreviewPresenterImpl(
    val model: PreviewModel,
    val service: PreviewService
) extends PreviewPresenter:

  val logger = LoggerFactory.getLogger(classOf[PreviewPresenterImpl])

  def generateResumePDF(): Unit =
    logger.info("Generating PDF...")
    service
      .generatePDF()
      .onComplete:
        case Success(response) => Platform.runLater(() => model.pdf.value = response.file)
        case Failure(t)        => logger.error("Error generating PDF", t)
