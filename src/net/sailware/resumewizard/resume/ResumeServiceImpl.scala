package net.sailware.resumewizard.resume

class ResumeServiceImpl extends ResumeService:

  val resume: Resume = new Resume()

  override def handleCreateResume(name: String): Resume =
    resume.copy(name = name)

  override def handlePersonalDetailsUpdate(name: String, title: String, summary: String): Resume =
    val personalDetails = PersonalDetails(name, title, summary)
    resume.copy(personalDetails = personalDetails)
