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
    val font = Font(pdFont, 0f, Color(0, 0, 0))
    val content = TextContent(font, "test", 0f)
    Assertions.assertEquals(font, content.font)

  @Test
  def whenCreatedExpectText(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 0f, Color(0, 0, 0))
    val content = TextContent(font, "test", 0f)
    Assertions.assertEquals("test", content.text)

  @Test
  def whenCreatedExpectCharacterSpacing(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 0f, Color(0, 0, 0))
    val content = TextContent(font, "test", 1f)
    Assertions.assertEquals(1f, content.characterSpacing)

  @Test
  def whenFontRobotoAnd12PointAndSingleLineThenExpectHeight(): Unit =
    val document = new PDDocument()
    val pdFont = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val font = Font(pdFont, 12f, Color(0, 0, 0))
    val content = TextContent(font, "test", 1f)
    Assertions.assertEquals(8.53125f, content.getHeight())
