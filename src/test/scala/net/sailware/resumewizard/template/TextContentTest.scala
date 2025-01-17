package net.sailware.resumewizard.template

import java.awt.Color
import java.awt.Font
import java.io.File
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TextContentTest:

  @Test
  def whenCreatedExpectFont(): Unit =
    val font = Font.createFont(Font.TRUETYPE_FONT, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val content = TextContent(font, 0, Color(0, 0, 0), "test", 0f)
    Assertions.assertEquals(font, content.font)

  @Test
  def whenCreatedExpectText(): Unit =
    val font = Font.createFont(Font.TRUETYPE_FONT, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val content = TextContent(font, 0, Color(0, 0, 0), "test", 0f)
    Assertions.assertEquals("test", content.text)

  @Test
  def whenCreatedExpectCharacterSpacing(): Unit =
    val font = Font.createFont(Font.TRUETYPE_FONT, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val content = TextContent(font, 0, Color(0, 0, 0), "test", 1f)
    Assertions.assertEquals(1f, content.characterSpacing)

  @Test
  def whenFontRobotoAnd12PointAndSingleLineThenExpectHeight(): Unit =
    val font = Font.createFont(Font.TRUETYPE_FONT, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val content = TextContent(font, 12, Color(0, 0, 0), "test", 0f)
    Assertions.assertEquals(15, content.getHeight())
