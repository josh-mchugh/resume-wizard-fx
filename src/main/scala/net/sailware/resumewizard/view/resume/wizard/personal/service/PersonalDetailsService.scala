package net.sailware.resumewizard.view.resume.wizard.personal.service

import net.sailware.resumewizard.resume.Resume

trait PersonalDetailsService:

  def onPersonalDetailsSave(name: String, title: String, summary: String): Unit
