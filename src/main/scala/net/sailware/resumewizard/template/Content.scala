package net.sailware.resumewizard.template

import java.awt.Canvas
import java.awt.Color
import java.awt.Font

sealed trait Content:
  def getHeight(): Int

case class BackgroundContent(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val color: Color
) extends Content:
  override def getHeight(): Int =
    0

case class TextContent(
    val font: Font,
    val size: Int,
    val color: Color,
    val text: String,
    val characterSpacing: Float
) extends Content:

  override def getHeight(): Int =
    // FONT.PLAIN is set as default, if fonts are bold or italic then we need to change this
    // value to be configured on the TextContent
    Canvas().getFontMetrics(font.deriveFont(Font.PLAIN, size)).getHeight()
