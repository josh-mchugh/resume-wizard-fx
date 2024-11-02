package net.sailware.resumewizard.view.resume.wizard.certification.service

trait CertificationsService:

  def handleCertificationsUpdate(certificationTruples: List[(String, String, String, String)]): Unit
