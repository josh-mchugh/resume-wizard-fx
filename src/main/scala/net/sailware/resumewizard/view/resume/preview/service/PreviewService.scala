package net.sailware.resumewizard.view.resume.preview.service

import net.sailware.resumewizard.view.resume.preview.service.model.GeneratePDFResponse
import scala.concurrent.Future

trait PreviewService:

  def generatePDF(): Future[GeneratePDFResponse]
