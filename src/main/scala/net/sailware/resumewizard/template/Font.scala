package net.sailware.resumewizard.template

import java.awt.Color
import org.apache.pdfbox.pdmodel.font.PDFont

case class Font(
  val font: PDFont,
  val size: Float,
  val color: Color
)
