package net.sailware.resumewizard.view.resume.wizard.contact.service

trait ContactDetailsService:

  def handleContactDetailsUpdate(phone: String, email: String, location: String): Unit
