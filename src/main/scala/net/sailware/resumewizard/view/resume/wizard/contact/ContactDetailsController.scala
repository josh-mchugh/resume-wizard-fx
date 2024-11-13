package net.sailware.resumewizard.view.resume.wizard.contact

import net.sailware.resumewizard.resume.ResumeService
import net.sailware.resumewizard.view.core.Controller
import net.sailware.resumewizard.view.resume.wizard.contact.service.ContactDetailsServiceImpl
import scalafx.scene.layout.Region

class ContactDetailsController(val resumeService: ResumeService) extends Controller[Region]:
  val model = new ContactDetailsModel()
  val contactDetailsPresenter = new ContactDetailsPresenterImpl(model, new ContactDetailsServiceImpl(resumeService))
  val contactDetailsView = new ContactDetailsViewImpl(contactDetailsPresenter, model)

  override def view(): Region =
    contactDetailsView.view()
