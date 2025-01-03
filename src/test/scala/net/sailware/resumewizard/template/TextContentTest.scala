package net.sailware.resumewizard.template

import java.awt.Color
import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TextContentTest:

  @Test
  def whenCreatedExpectFont(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 0F, Color(0, 0, 0))
    val content = TextContent(font, "test", 0F)
    Assertions.assertEquals(font, content.font)

  @Test
  def whenCreatedExpectText(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 0F, Color(0, 0, 0))
    val content = TextContent(font, "test", 0F)
    Assertions.assertEquals("test", content.text)

  @Test
  def whenCreatedExpectCharacterSpacing(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 0F, Color(0, 0, 0))
    val content = TextContent(font, "test", 1F)
    Assertions.assertEquals(1F, content.characterSpacing)

  @Test
  def whenFontRobotoAnd12PointAndSingleLineThenExpectHeight(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 12F, Color(0, 0, 0))
    val content = TextContent(font, "test", 1F)
    Assertions.assertEquals(8.53125F, content.getHeight())
