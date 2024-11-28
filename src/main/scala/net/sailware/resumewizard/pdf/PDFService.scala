package net.sailware.resumewizard.pdf

import net.sailware.resumewizard.pdf.model.GeneratePDFRequest
import net.sailware.resumewizard.pdf.model.GeneratePDFResponse

trait PDFService:

  def generatePDF(request: GeneratePDFRequest): GeneratePDFResponse
