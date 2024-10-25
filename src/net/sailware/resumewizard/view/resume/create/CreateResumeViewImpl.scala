package net.sailware.resumewizard.view.resume.create

import net.sailware.resumewizard.view.core.ComponentUtil
import scalafx.Includes.*
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.scene.control.TextField
import scalafx.scene.layout.Region
import scalafx.scene.layout.HBox

class CreateResumeViewImpl(val presenter: CreateResumePresenter, val model: CreateResumeModel) extends CreateResumeView:

  override def view(): Region =
    createNewResumePane()

  private def createNewResumePane(): Region =
    val content = List(
      ComponentUtil.createPageHeader(
        "Create New Resume",
        createContinueButton()
      ),
      new Label("Resume Name") { },
      new TextField {
        text <==> model.name
      }
    )

    ComponentUtil.createContentPage(content)

  private def createContinueButton(): List[HBox] =
    val button = new Button("Create Resume"):
      disable <== model.createBtnEnabled
      onAction = (event: ActionEvent) => presenter.onCreateForm()

    List(
      new HBox {
        alignment = Pos.TopRight
        children = button
      }
    )
