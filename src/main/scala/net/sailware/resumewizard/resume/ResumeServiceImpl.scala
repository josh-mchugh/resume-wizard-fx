package net.sailware.resumewizard.resume

import net.sailware.resumewizard.resume.model.CreateResumePersonalRequest
import net.sailware.resumewizard.resume.model.CreateResumePersonalResponse
import net.sailware.resumewizard.resume.model.CreateResumeRequest
import net.sailware.resumewizard.resume.model.CreateResumeResponse
import net.sailware.resumewizard.resume.model.CreateSocialsRequest
import net.sailware.resumewizard.resume.model.CreateSocialsResponse

class ResumeServiceImpl extends ResumeService:

  var resume: Resume = new Resume()

  override def getResume(): Resume =
    resume

  override def handleCreateResume(request: CreateResumeRequest): CreateResumeResponse =
    resume = resume.copy(name = request.name)
    CreateResumeResponse(resume)

  override def handlePersonalDetailsUpdate(request: CreateResumePersonalRequest): CreateResumePersonalResponse =
    val personalDetails = PersonalDetails(request.name, request.title, request.summary)
    resume = resume.copy(personalDetails = personalDetails)
    CreateResumePersonalResponse(resume)

  override def handleContactDetailsUpdate(phone: String, email: String, location: String): Resume =
    val contactDetails = ContactDetails(phone, email, location)
    resume = resume.copy(contactDetails = contactDetails)
    resume

  override def handleSocialsUpdate(request: CreateSocialsRequest): CreateSocialsResponse =
    val socials = request.socials.map(social => new Social(social.name, social.url))
    resume = resume.copy(socials = socials)
    CreateSocialsResponse(resume)

  override def handleExperiencesUpdate(experienceTuples: List[(String, String, String, String, String, String)]): Resume =
    val experiences = experienceTuples.map((title, organization, duration, location, description, skills) => new Experience(title, organization, duration, location, description, skills))
    resume = resume.copy(experiences = experiences)
    resume

  override def handleCertificationsUpdate(certificationTuples: List[(String, String, String, String)]): Resume =
    val certifications = certificationTuples.map((title, organization, duration, location) => new Certification(title, organization, duration, location))
    resume = resume.copy(certifications = certifications)
    resume
