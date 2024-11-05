package net.sailware.resumewizard.pdf

import java.io.File
import java.io.FileOutputStream
import java.io.StringReader
import net.sailware.resumewizard.resume.Resume
import org.slf4j.LoggerFactory
import org.w3c.dom.Document
import org.xhtmlrenderer.pdf.ITextRenderer
import org.xhtmlrenderer.resource.XMLResource

class PDFServiceImpl() extends PDFService:

  val logger = LoggerFactory.getLogger(classOf[PDFServiceImpl])
  val renderer = new ITextRenderer()

  override def generatePDF(resume: Resume): File =
    logger.info("Generating pdf...")
    val html =  "<!DOCTYPE html><html><body><h1>My First Heading</h1><p>My first paragraph.</p></body></html>"

    val file = new File("resume.pdf")
    try {
      val fileOutputStream = new FileOutputStream(file)
      val source = XMLResource.load(new StringReader(html)).getDocument()
      renderer.createPDF(source, fileOutputStream)
    } catch {
      case t: Throwable => logger.error("error generating PDF", t)
    }

    file
