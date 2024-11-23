package net.sailware.resumewizard.view.resume.preview.service.model

import java.io.File

case class GeneratePDFResponse(
    val file: File
)

object GeneratePDFResponse:

  def apply(file: File): GeneratePDFResponse =
    new GeneratePDFResponse(file)
