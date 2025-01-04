package net.sailware.resumewizard.template

import java.awt.Color
import org.apache.pdfbox.pdmodel.font.PDFont
import org.slf4j.LoggerFactory

sealed trait Content:
  def getHeight(): Float

case class BackgroundContent(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val color: Color
) extends Content:
  override def getHeight(): Float =
    0f

case class TextContent(
    val font: Font,
    val text: String,
    val characterSpacing: Float
) extends Content:

  val logger = LoggerFactory.getLogger(classOf[TextContent])

  override def getHeight(): Float =
    FontUtil.getFontHeight(font.font, font.size)

  def getStringWidth(): Float =
    text
      .codePoints()
      .mapToObj(codePoint => String(Array(codePoint), 0, 1))
      .map(codePoint =>
        try {
          font.font.getStringWidth(codePoint) * font.size / 1000f
        } catch {
          case e: IllegalArgumentException =>
            logger.error("Illegal argument for font width", e)
            font.font.getStringWidth("-") * font.size / 1000f
        }
      )
      .reduce(0.0f, (acc, value) => acc + value)
