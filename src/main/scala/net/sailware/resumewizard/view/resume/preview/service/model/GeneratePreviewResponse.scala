package net.sailware.resumewizard.view.resume.preview.service.model

import java.io.File
import net.sailware.resumewizard.pdf.model.GeneratePDFResponse

case class GeneratePreviewResponse(
    val file: File
)

object GeneratePreviewResponse:

  def apply(response: GeneratePDFResponse): GeneratePreviewResponse =
    new GeneratePreviewResponse(response.file)
