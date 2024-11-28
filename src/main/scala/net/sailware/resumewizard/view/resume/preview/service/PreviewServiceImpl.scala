package net.sailware.resumewizard.view.resume.preview.service

import net.sailware.resumewizard.pdf.PDFService
import net.sailware.resumewizard.pdf.model.GeneratePDFRequest
import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.resume.preview.service.model.GeneratePreviewResponse
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PreviewServiceImpl(
    val resumeService: ResumeService,
    val pdfService: PDFService
) extends PreviewService:

  override def generatePreview(): Future[GeneratePreviewResponse] =
    Future {
      GeneratePreviewResponse(pdfService.generatePDF(GeneratePDFRequest(resumeService.getResume())))
    }
