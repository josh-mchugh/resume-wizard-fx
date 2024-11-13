package net.sailware.resumewizard.view.resume.wizard.social.service

trait SocialsService:

  def handleSocialsUpdate(socialTuples: List[(String, String)]): Unit
