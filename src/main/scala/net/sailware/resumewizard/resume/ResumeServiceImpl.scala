package net.sailware.resumewizard.resume

import net.sailware.resumewizard.resume.model.CreateContactRequest
import net.sailware.resumewizard.resume.model.CreateContactResponse
import net.sailware.resumewizard.resume.model.CreateExperiencesRequest
import net.sailware.resumewizard.resume.model.CreateExperiencesResponse
import net.sailware.resumewizard.resume.model.CreatePersonalRequest
import net.sailware.resumewizard.resume.model.CreatePersonalResponse
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

  override def handlePersonalDetailsUpdate(request: CreatePersonalRequest): CreatePersonalResponse =
    val personalDetails = PersonalDetails(request.name, request.title, request.summary)
    resume = resume.copy(personalDetails = personalDetails)
    CreatePersonalResponse(resume)

  override def handleContactDetailsUpdate(request: CreateContactRequest): CreateContactResponse =
    val contactDetails = ContactDetails(request.phone, request.email, request.location)
    resume = resume.copy(contactDetails = contactDetails)
    CreateContactResponse(resume)

  override def handleSocialsUpdate(request: CreateSocialsRequest): CreateSocialsResponse =
    val socials = request.socials.map(social => new Social(social.name, social.url))
    resume = resume.copy(socials = socials)
    CreateSocialsResponse(resume)

  override def handleExperiencesUpdate(request: CreateExperiencesRequest): CreateExperiencesResponse =
    val experiences = request.experiences.map(experience => new Experience(experience.title, experience.organization, experience.duration, experience.location, experience.description, experience.skills))
    resume = resume.copy(experiences = experiences)
    CreateExperiencesResponse(resume)

  override def handleCertificationsUpdate(certificationTuples: List[(String, String, String, String)]): Resume =
    val certifications = certificationTuples.map((title, organization, duration, location) => new Certification(title, organization, duration, location))
    resume = resume.copy(certifications = certifications)
    resume
