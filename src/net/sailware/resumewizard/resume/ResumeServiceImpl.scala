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

  override def handleExperiencesUpdate(experienceTuples: List[(String, String, String, String, String, String)]): Resume =
    val experiences = experienceTuples.map((title, organization, duration, location, description, skills) =>
      new Experience(title, organization, duration, location, description, skills)
    )
    resume.copy(experiences = experiences)

  override def handleCertificationsUpdate(certificationTuples: List[(String, String, String, String)]): Resume =
    val certifications = certificationTuples.map((title, organization, duration, location) =>
      new Certification(title, organization, duration, location)
    )
    resume.copy(certifications = certifications)
