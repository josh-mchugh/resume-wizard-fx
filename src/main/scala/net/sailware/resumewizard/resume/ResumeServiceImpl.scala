package net.sailware.resumewizard.resume

class ResumeServiceImpl extends ResumeService:

  var resume: Resume = new Resume()

  override def getResume(): Resume =
    resume

  override def handleCreateResume(name: String): Resume =
    resume = resume.copy(name = name)
    resume

  override def handlePersonalDetailsUpdate(name: String, title: String, summary: String): Resume =
    val personalDetails = PersonalDetails(name, title, summary)
    resume = resume.copy(personalDetails = personalDetails)
    resume

  override def handleContactDetailsUpdate(phone: String, email: String, location: String): Resume =
    val contactDetails = ContactDetails(phone, email, location)
    resume = resume.copy(contactDetails = contactDetails)
    resume

  override def handleSocialsUpdate(socialTuples: List[(String, String)]): Resume =
    val socials = socialTuples.map((name, url) => new Social(name, url))
    resume = resume.copy(socials = socials)
    resume

  override def handleExperiencesUpdate(experienceTuples: List[(String, String, String, String, String, String)]): Resume =
    val experiences = experienceTuples.map((title, organization, duration, location, description, skills) => new Experience(title, organization, duration, location, description, skills))
    resume = resume.copy(experiences = experiences)
    resume

  override def handleCertificationsUpdate(certificationTuples: List[(String, String, String, String)]): Resume =
    val certifications = certificationTuples.map((title, organization, duration, location) => new Certification(title, organization, duration, location))
    resume = resume.copy(certifications = certifications)
    resume
