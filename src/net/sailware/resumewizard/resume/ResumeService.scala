package net.sailware.resumewizard.resume

trait ResumeService:

  def handleCreateResume(name: String): Resume

  def handlePersonalDetailsUpdate(name: String, title: String, summary: String): Resume
