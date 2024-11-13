package net.sailware.resumewizard.view.resume.wizard.personal

import net.sailware.resumewizard.view.core.ComponentUtil
import scalafx.Includes.*
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.layout.HBox
import scalafx.scene.layout.Region
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.scene.control.TextArea
import scalafx.scene.control.TextField

class PersonalDetailsViewImpl(
  val presenter: PersonalDetailsPresenter,
  val model: PersonalDetailsModel
) extends PersonalDetailsView:

  override def view(): Region =
    createPersonalDetails

  private def createPersonalDetails =
    val content = List(
        ComponentUtil.createPageHeader(
          "Personal Details",
          createContinueButton()
        ),
        new Label("Your name"),
        new TextField {
          text <==> model.name
        },
        new Label("Your Current Title"),
        new TextField {
          text <==> model.title
        },
        new Label("Summary of your current career position"),
        new TextArea {
          prefRowCount = 3
          text <==> model.summary
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
