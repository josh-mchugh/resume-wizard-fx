package net.sailware.resumewizard.view.resume.wizard.experience.service

trait ExperiencesService:

  def handleExperiencesUpdate(experienceTuples: List[(String, String, String, String, String, String)]): Unit
