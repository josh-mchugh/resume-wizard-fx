package net.sailware.resumewizard.resume

import net.sailware.resumewizard.resume.model.CreateCertificationsRequest
import net.sailware.resumewizard.resume.model.CreateCertificationsResponse
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

trait ResumeService:

  def getResume(): Resume

  def handleCreateResume(request: CreateResumeRequest): CreateResumeResponse

  def handlePersonalDetailsUpdate(request: CreatePersonalRequest): CreatePersonalResponse

  def handleContactDetailsUpdate(request: CreateContactRequest): CreateContactResponse

  def handleSocialsUpdate(request: CreateSocialsRequest): CreateSocialsResponse

  def handleExperiencesUpdate(request: CreateExperiencesRequest): CreateExperiencesResponse

  def handleCertificationsUpdate(request: CreateCertificationsRequest): CreateCertificationsResponse
