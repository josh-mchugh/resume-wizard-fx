package net.sailware.resumewizard.template

import org.apache.pdfbox.pdmodel.font.PDFont
import java.awt.Color

case class Font(
  val font: PDFont,
  val size: Float,
  val color: Color
)
