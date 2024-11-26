package net.sailware.resumewizard.resume

import net.sailware.resumewizard.resume.model.CreateResumeRequest
import net.sailware.resumewizard.resume.model.CreateResumeResponse

trait ResumeService:

  def getResume(): Resume

  def handleCreateResume(request: CreateResumeRequest): CreateResumeResponse

  def handlePersonalDetailsUpdate(name: String, title: String, summary: String): Resume

  def handleContactDetailsUpdate(phone: String, email: String, location: String): Resume

  def handleSocialsUpdate(socialTuples: List[(String, String)]): Resume

  def handleExperiencesUpdate(experienceTuples: List[(String, String, String, String, String, String)]): Resume

  def handleCertificationsUpdate(certificationsTuple: List[(String, String, String, String)]): Resume
