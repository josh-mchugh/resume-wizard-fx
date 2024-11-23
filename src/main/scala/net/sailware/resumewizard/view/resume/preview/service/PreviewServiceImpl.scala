package net.sailware.resumewizard.view.resume.preview.service

import net.sailware.resumewizard.pdf.PDFService
import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.resume.preview.service.model.GeneratePDFResponse
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PreviewServiceImpl(
    val resumeService: ResumeService,
    val pdfService: PDFService
) extends PreviewService:

  override def generatePDF(): Future[GeneratePDFResponse] =
    Future {
      GeneratePDFResponse(pdfService.generatePDF(resumeService.getResume()))
    }
