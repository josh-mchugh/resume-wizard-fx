package net.sailware.resumewizard.template

import java.awt.Color
import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType0Font

object TestData:

  def sections(document: PDDocument): Array[Section] =
    val primary = Color(17, 33, 47)
    val white = Color(255, 255, 255)
    val regular = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val medium = PDType0Font.load(document, File(getClass.getResource("/font/Roboto-Medium.ttf").getPath))

    Array(
      Section(
        "LEFT_COLUMN",
        Option.empty,
        Padding(64.5f, 0f, 0f, 24f),
        0,
        Option(
          BackgroundContent(
            0,
            0,
            PDRectangle.A4.getWidth() * 0.347f,
            PDRectangle.A4.getHeight(),
            primary
          )
        )
      ),
      Section(
        "NAME",
        Option("LEFT_COLUMN"),
        Padding(0f, 0f, 0f, 0f),
        0,
        Option(
          TextContent(
            Font(medium, 22.5f, white),
            "John Doe",
            0f
          )
        )
      ),
      Section(
        "TITLE",
        Option("LEFT_COLUMN"),
        Padding(0f, 0f, 0f, 0f),
        1,
        Option(
          TextContent(
            Font(regular, 10.5f, white),
            "Web and Graphics Designer",
            0.6f
          )
        )
      )
    )
