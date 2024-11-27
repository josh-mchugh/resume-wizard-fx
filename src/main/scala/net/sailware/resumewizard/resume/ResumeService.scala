package net.sailware.resumewizard.resume

import net.sailware.resumewizard.resume.model.CreateResumePersonalRequest
import net.sailware.resumewizard.resume.model.CreateResumePersonalResponse
import net.sailware.resumewizard.resume.model.CreateResumeRequest
import net.sailware.resumewizard.resume.model.CreateResumeResponse
import net.sailware.resumewizard.resume.model.CreateSocialsRequest
import net.sailware.resumewizard.resume.model.CreateSocialsResponse

trait ResumeService:

  def getResume(): Resume

  def handleCreateResume(request: CreateResumeRequest): CreateResumeResponse

  def handlePersonalDetailsUpdate(request: CreateResumePersonalRequest): CreateResumePersonalResponse

  def handleContactDetailsUpdate(phone: String, email: String, location: String): Resume

  def handleSocialsUpdate(request: CreateSocialsRequest): CreateSocialsResponse

  def handleExperiencesUpdate(experienceTuples: List[(String, String, String, String, String, String)]): Resume

  def handleCertificationsUpdate(certificationsTuple: List[(String, String, String, String)]): Resume
