package net.sailware.resumewizard.pdf.model

import java.io.File

case class GeneratePDFResponse(
  file: File
)

object GeneratePDFResponse:

  def apply(file: File): GeneratePDFResponse =
    new GeneratePDFResponse(file)
