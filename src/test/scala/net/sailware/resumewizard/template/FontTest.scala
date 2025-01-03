package net.sailware.resumewizard.template

import java.awt.Color
import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FontTest:

  @Test
  def whenCreatedExpectFont(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 0F, Color(0, 0, 0))
    Assertions.assertEquals(pdFont, font.font)

  @Test
  def whenCreatedExpectSize(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 12F, Color(0, 0, 0))
    Assertions.assertEquals(12F, font.size)

  @Test
  def whenCreatedExpectColor(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 0F, Color(255, 255, 255))
    Assertions.assertEquals(Color(255, 255, 255
), font.color)
