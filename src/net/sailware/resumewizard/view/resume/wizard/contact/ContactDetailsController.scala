package net.sailware.resumewizard.view.resume.wizard.contact

import net.sailware.resumewizard.view.core.Controller
import scalafx.scene.layout.Region

class ContactDetailsController() extends Controller[Region]:
  val model = new ContactDetailsModel()
  val contactDetailsPresenter = new ContactDetailsPresenterImpl(model)
  val contactDetailsView = new ContactDetailsViewImpl(contactDetailsPresenter, model)

  override def view(): Region =
    contactDetailsView.view()
