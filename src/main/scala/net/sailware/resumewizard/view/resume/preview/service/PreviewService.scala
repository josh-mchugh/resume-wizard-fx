package net.sailware.resumewizard.view.resume.preview.service

import net.sailware.resumewizard.view.resume.preview.service.model.GeneratePreviewResponse
import scala.concurrent.Future

trait PreviewService:

  def generatePreview(): Future[GeneratePreviewResponse]
