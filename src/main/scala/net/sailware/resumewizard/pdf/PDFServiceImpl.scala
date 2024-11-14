package net.sailware.resumewizard.pdf

import java.io.File
import java.io.FileOutputStream
import java.io.StringReader
import net.sailware.resumewizard.resume.Resume
import org.slf4j.LoggerFactory
import org.w3c.dom.Document
import org.xhtmlrenderer.pdf.ITextRenderer
import org.xhtmlrenderer.resource.XMLResource
import scalatags.Text.all.*

class PDFServiceImpl() extends PDFService:

  val logger = LoggerFactory.getLogger(classOf[PDFServiceImpl])
  val renderer = new ITextRenderer()

  override def generatePDF(resume: Resume): File =
    logger.info("Generating pdf...")

    val file = new File("resume.pdf")
    try {
      val fileOutputStream = new FileOutputStream(file)
      val source = XMLResource.load(new StringReader(generateHTML(resume))).getDocument()
      renderer.createPDF(source, fileOutputStream)
    } catch {
      case t: Throwable => logger.error("error generating PDF", t)
    }

    file

  private def generateHTML(resume: Resume): String =
    html(
      head(),
      body(
        // render personal details
        if (resume.hasPersonalDetails())
          div(
            h1(resume.personalDetails.name),
            p(resume.personalDetails.title),
            p(resume.personalDetails.summary)
          )
        else div(),
        // render contact details
        if (resume.hasContactDetails())
          div(
            p(resume.contactDetails.phone),
            p(resume.contactDetails.email),
            p(resume.contactDetails.location)
          )
        else div(),
        // render socials
        if (resume.hasSocials())
          for (social <- resume.socials)
            yield div(
              p(social.name),
              p(social.url)
            )
        else div(),
        // render experiences
        if (resume.hasExperiences())
          for (experience <- resume.experiences)
            yield div(
              p(experience.title),
              p(experience.organization),
              p(experience.duration),
              p(experience.location),
              p(experience.description),
              p(experience.skills)
            )
        else div(),
        // render certifications
        if (resume.hasCertifications())
          for (certification <- resume.certifications)
            yield div(
              p(certification.title),
              p(certification.organization),
              p(certification.duration),
              p(certification.location)
            )
        else div()
      )
    ).render
