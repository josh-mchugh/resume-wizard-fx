package net.sailware.resumewizard.view.resume.preview

import net.sailware.resumewizard.template.Node
import net.sailware.resumewizard.template.Section
import net.sailware.resumewizard.template.TestData
import scala.collection.mutable.ListBuffer
import scalafx.Includes.*
import scalafx.scene.layout.Pane
import scalafx.scene.layout.Region
import scalafx.scene.text.Text

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  override def view(): Region =
    new Pane:
      style = "-fx-background-color: white"
      maxWidth = 793.7F
      maxHeight = 1122.52F
      minWidth = 793.7F
      minHeight = 1122.52F
      children = buildSections()

  private def buildSections(): List[scalafx.scene.Node] =
    val tree = Node(TestData.sections())
    val buffer = new ListBuffer[scalafx.scene.Node]()
    processNode(tree, buffer)
    val list = buffer.toList
    println(s"the list: $list")
    list

  private def processNode(node: Node, buffer: ListBuffer[scalafx.scene.Node]): Unit =
    buildSection(node) match
      case Some(text) => buffer += text
      case None => buffer
    node.children.foreach(child => processNode(child, buffer))

  private def buildSection(node: Node): Option[Text] =
    node.section.content match
      case Some(content) =>
        val text = new Text("Test"):
          x = node.x
          y = node.y
        Option(text)
      case None => None
