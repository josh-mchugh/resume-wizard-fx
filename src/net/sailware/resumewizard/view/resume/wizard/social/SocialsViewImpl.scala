package net.sailware.resumewizard.view.resume.wizard.social

import net.sailware.resumewizard.ComponentUtil
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.scene.control.TextField
import scalafx.scene.layout.HBox
import scalafx.scene.layout.Region
import scalafx.scene.layout.VBox

class SocialsViewImpl(val presenter: SocialsPresenter, val model: SocialsModel) extends SocialsView:

  override def view(): Region =
    val content = List(
        ComponentUtil.createPageHeader(
          "Socials",
          createContinueButton()
        ),
        new VBox {
          children = model.socials.map(social => createSocialSection(social)).flatten
          model.socials.onInvalidate { (newValue) =>
            children = model.socials.map(social => createSocialSection(social)).flatten
          }
        },
        new Button("Add Social") {
          onAction = (event: ActionEvent) => model.socials += new SocialModel()
        },
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

  private def createSocialSection(social: SocialModel): List[Node] =
    List(
      new Label("Social Name"),
      new TextField {
        text <==> social.name
      },
      new Label("Social URL"),
      new TextField {
        text <==> social.url
      }
    )
