package net.sailware.resumewizard.view.resume.preview.service.model

import java.io.File
import net.sailware.resumewizard.resume.Resume

case class GeneratePreviewResponse(
    val resume: Resume
)

object GeneratePreviewResponse:

  def apply(resume: Resume): GeneratePreviewResponse =
    new GeneratePreviewResponse(resume)
