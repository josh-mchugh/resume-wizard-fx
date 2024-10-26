package net.sailware.resumewizard.resume

class ResumeServiceImpl extends ResumeService:

  val resume: Resume = new Resume()

  override def handleCreateResume(name: String): Resume =
    resume.copy(name = name)
