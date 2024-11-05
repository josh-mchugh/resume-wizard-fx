package net.sailware.resumewizard.pdf

import java.io.File
import net.sailware.resumewizard.resume.Resume

trait PDFService:

  def generatePDF(resume: Resume): File
