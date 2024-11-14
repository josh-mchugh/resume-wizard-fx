package net.sailware.resumewizard.view.resume.wizard.certification

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

class CertificationsViewImpl(val presenter: CertificationsPresenter, val model: CertificationsModel) extends CertificationsView:

  override def view(): Region =
    val content = List(
      ComponentUtil.createPageHeader(
        "Certifications",
        createContinueButton()
      ),
      new VBox {
        children = model.certifications.map(certification => createCertificationSection(certification)).flatten
        model.certifications.onInvalidate { (newValue) =>
          children = model.certifications.map(certification => createCertificationSection(certification)).flatten
        }
      },
      new Button("Add Certification") {
        onAction = (event: ActionEvent) => model.certifications += new CertificationModel()
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

  private def createCertificationSection(certification: CertificationModel): List[Node] =
    List(
      new Label("Title"),
      new TextField {
        text <==> certification.title
      },
      new Label("Organization Name"),
      new TextField {
        text <==> certification.organization
      },
      new Label("Duration"),
      new TextField {
        text <==> certification.duration
      },
      new Label("Location"),
      new TextField {
        text <==> certification.location
      }
    )
