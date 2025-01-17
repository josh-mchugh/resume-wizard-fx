package net.sailware.resumewizard.view.resume.preview.service

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.resume.preview.service.model.GeneratePreviewResponse
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PreviewServiceImpl(
    val resumeService: ResumeService
) extends PreviewService:

  override def generatePreview(): Future[GeneratePreviewResponse] =
    Future {
      GeneratePreviewResponse(resumeService.getResume())
    }
