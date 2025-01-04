package net.sailware.resumewizard.pdf.model

import net.sailware.resumewizard.resume.Resume

case class GeneratePDFRequest(
    resume: Resume
)

object GeneratePDFRequest:

  def apply(resume: Resume): GeneratePDFRequest =
    new GeneratePDFRequest(resume)
