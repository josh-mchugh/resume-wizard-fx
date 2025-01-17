package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.template.Node as ContentNode
import net.sailware.resumewizard.template.PageBuilder
import net.sailware.resumewizard.template.PageConfig
import net.sailware.resumewizard.template.PageMeasurementUnit
import net.sailware.resumewizard.template.PageOriginPosition
import net.sailware.resumewizard.template.PageSize
import net.sailware.resumewizard.template.Section
import net.sailware.resumewizard.template.TestData
import scala.collection.mutable.ListBuffer
import scalafx.Includes.*
import scalafx.scene.layout.Pane
import scalafx.scene.layout.Region
import scalafx.scene.Node
import scalafx.scene.text.Text

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  override def view(): Region =
    new Pane:
      style = "-fx-background-color: white"
      maxWidth = 793.7f
      maxHeight = 1122.52f
      minWidth = 793.7f
      minHeight = 1122.52f
      children = buildSections()

  private def buildSections(): List[Node] =
    val pageConfig = PageConfig(PageSize.A4, PageOriginPosition.TopLeft, PageMeasurementUnit.Pixel)
    val tree = PageBuilder(TestData.sections(), pageConfig)
    val buffer = new ListBuffer[Node]()
    processNode(tree, buffer)
    val list = buffer.toList
    println(s"the list: $list")
    list

  private def processNode(node: ContentNode, buffer: ListBuffer[Node]): Unit =
    buildSection(node) match
      case Some(text) => buffer += text
      case None       => buffer
    node.children.foreach(child => processNode(child, buffer))

  private def buildSection(node: ContentNode): Option[Text] =
    node.section.content match
      case Some(content) =>
        val text = new Text("Test"):
          x = node.x.get
          y = node.y.get
        Option(text)
      case None => None
