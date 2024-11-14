package net.sailware.resumewizard.view.resume.wizard.experience

import net.sailware.resumewizard.view.core.ComponentUtil
import scalafx.Includes.*
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.scene.control.TextField
import scalafx.scene.layout.HBox
import scalafx.scene.layout.Region
import scalafx.scene.layout.VBox

class ExperiencesViewImpl(val presenter: ExperiencesPresenter, val model: ExperiencesModel) extends ExperiencesView:

  override def view(): Region =
    val content = List(
      ComponentUtil.createPageHeader(
        "Experiences",
        createContinueButton()
      ),
      new VBox {
        children = model.experiences.map(experience => createExperienceSection(experience)).flatten
        model.experiences.onInvalidate { (newValue) =>
          children = model.experiences.map(experience => createExperienceSection(experience)).flatten
        }
      },
      new Button("Add Experience") {
        onAction = (event: ActionEvent) => model.experiences += new ExperienceModel()
      }
    )

    ComponentUtil.createContentPage(content)

  private def createContinueButton(): List[Region] =
    val button = new Button("Continue"):
      onAction = (event: ActionEvent) => presenter.onContinue()

    List(
      new HBox:
        alignment = Pos.TopRight
        children = button
    )

  private def createExperienceSection(experience: ExperienceModel): List[Node] =
    List(
      new Label("Title"),
      new TextField {
        text <==> experience.title
      },
      new Label("Organization Name"),
      new TextField {
        text <==> experience.organization
      },
      new Label("Duration"),
      new TextField {
        text <==> experience.duration
      },
      new Label("Location"),
      new TextField {
        text <==> experience.location
      },
      new Label("Description"),
      new TextField {
        text <==> experience.description
      },
      new Label("Skills"),
      new TextField {
        text <==> experience.skills
      }
    )
