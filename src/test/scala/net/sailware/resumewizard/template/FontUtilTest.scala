package net.sailware.resumewizard.template

import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FontUnitTest:

  @Test
  def whenFontIsRobotoAndSize12PointThenExpectHeight(): Unit =
    val document = new PDDocument()
    val font = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    Assertions.assertEquals(8.53125f, FontUtil.getFontHeight(font, 12))
