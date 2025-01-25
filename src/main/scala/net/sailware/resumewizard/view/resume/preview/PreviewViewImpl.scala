package net.sailware.resumewizard.view.resume.preview

import scalafx.Includes.*
import scalafx.scene.Node
import scalafx.scene.paint.Color
import scalafx.scene.shape.Line
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.Pane
import scalafx.scene.layout.Region
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import org.slf4j.LoggerFactory

class PreviewViewImpl(val model: PreviewModel) extends PreviewView:

  val logger = LoggerFactory.getLogger(classOf[PreviewViewImpl])

  override def view(): Region =
    model.resume.onInvalidate { resume => logger.info("resume: {}", resume) }
    new Pane:
      style = "-fx-background-color: white"
      maxWidth = 793.7007874F
      maxHeight = 1122.519685F
      minWidth = 793.7007874F
      minHeight = 1122.519685F
      children = renderRow()

  private def renderRow(): List[Node] =
    val row = Row(width = 793.7007874F, height = 1122.519685F, border = Some(Border(Color.RED)))
    val results = collection.mutable.ListBuffer[Node]()
    if row.border.isDefined then
      val border = row.border.get
        results += createTopBorder(0F, 0F, row.width, row.height, border.color)
        results += createRightBorder(0F, 0F, row.width, row.height, border.color)
        results += createBottomBorder(0F, 0F, row.width, row.height, border.color)
        results += createLeftBorder(0F, 0F, row.width, row.height, border.color)
    results.toList

  private def createTopBorder(startX: Float, startY: Float, width: Float, height: Float, color: Color): Line =
    val endX = startX + width
    renderLine(startX, startY, endX, startY, color)

  private def createRightBorder(startX: Float, startY: Float, width: Float, height: Float, color: Color): Line =
    val endX = startX + width
    val endY = startY + height
    renderLine(endX, startY, endX, endY, color)

  private def createBottomBorder(startX: Float, startY: Float, width: Float, height: Float, color: Color): Line =
    val endX = startX + width
    val endY = startY + height
    renderLine(startX, endY, endX, endY, color)

  private def createLeftBorder(startX: Float, startY: Float, width: Float,  height: Float, color: Color): Line =
    val endY = startY + height
    renderLine(startX, startY, startX, endY, color)

  private def renderLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color): Line =
    val result = new Line()
    result.setStartX(startX)
    result.setStartY(startY)
    result.setEndX(endX)
    result.setEndY(endY)
    result.setStrokeWidth(1F)
    result.setStroke(color)
    result

case class Margin(
  val top: Float = 0F,
  val right: Float = 0F,
  val bottom: Float = 0F,
  val left: Float = 0F
)

case class Padding(
  val top: Float = 0F,
  val right: Float = 0F,
  val bottom: Float = 0F,
  val left: Float = 0F
)

case class Border(
  val color: Color
)

case class Row(
  val width: Float,
  val height: Float,
  val margin: Margin = Margin(),
  val padding: Padding = Padding(),
  val border: Option[Border] = None
)
