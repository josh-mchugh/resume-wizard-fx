package net.sailware.resumewizard.view.resume.wizard.contact

import net.sailware.resumewizard.ComponentUtil
import scalafx.Includes.*
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.scene.control.TextField
import scalafx.scene.layout.HBox
import scalafx.scene.layout.Region

class ContactDetailsViewImpl(val presenter: ContactDetailsPresenter, val model: ContactDetailsModel) extends ContactDetailsView:
  override def view(): Region =
    val content = List(
        ComponentUtil.createPageHeader(
          "Contact Details",
          createContinueButton()
        ),
        new Label("Your phone number"),
        new TextField {
          text <==> model.phone
        },
        new Label("Your email address"),
        new TextField {
          text <==> model.email
        },
        new Label("Your location"),
        new TextField {
          text <==> model.location
        }
    )

    ComponentUtil.createContentPage(content)

  private def createContinueButton(): List[HBox] =
    val button = new Button("Continue"):
      onAction = (event: ActionEvent) => presenter.onContinue()

    List(
      new HBox:
        alignment = Pos.TopRight
        children = button
    )
