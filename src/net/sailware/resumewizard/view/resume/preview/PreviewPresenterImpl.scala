package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.resume.ResumeService
import org.slf4j.LoggerFactory

class PreviewPresenterImpl(
  val model: PreviewModel,
  val resumeService: ResumeService
) extends PreviewPresenter:

  val logger = LoggerFactory.getLogger(classOf[PreviewPresenterImpl])
