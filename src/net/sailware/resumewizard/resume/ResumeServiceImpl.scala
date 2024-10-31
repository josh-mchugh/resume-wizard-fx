package net.sailware.resumewizard.resume

class ResumeServiceImpl extends ResumeService:

  val resume: Resume = new Resume()

  override def handleCreateResume(name: String): Resume =
    resume.copy(name = name)

  override def handlePersonalDetailsUpdate(name: String, title: String, summary: String): Resume =
    val personalDetails = PersonalDetails(name, title, summary)
    resume.copy(personalDetails = personalDetails)

  override def handleContactDetailsUpdate(phone: String, email: String, location: String): Resume =
    val contactDetails = ContactDetails(phone, email, location)
    resume.copy(contactDetails = contactDetails)

  override def handleSocialsUpdate(socialTuples: List[(String, String)]): Resume =
    val socials = socialTuples.map((name, url) => new Social(name, url))
    resume.copy(socials = socials)
