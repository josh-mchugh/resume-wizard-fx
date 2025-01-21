package net.sailware.resumewizard.template

import net.sailware.resumewizard.template.UnitType.Point
import java.awt.Color
import java.awt.Font
import java.io.File

object TestData:

  def sections(): Array[Section] =
    val primary = Color(17, 33, 47)
    val white = Color(255, 255, 255)
    val regular = Font.createFont(Font.TRUETYPE_FONT, File(getClass.getResource("/font/Roboto-Regular.ttf").getPath))
    val medium = Font.createFont(Font.TRUETYPE_FONT, File(getClass.getResource("/font/Roboto-Medium.ttf").getPath))

    Array(
      Section(
        "LEFT_COLUMN",
        Option.empty,
        Margin(0F, 0F, 0F, 0F, Point),
        Padding(64.5f, 0f, 0f, 24f, Point),
        0,
        Option(
          BackgroundContent(
            0,
            0,
            Page.A4.width * 0.347f,
            Page.A4.height,
            primary
          )
        )
      ),
      Section(
        "NAME",
        Option("LEFT_COLUMN"),
        Margin(0F, 0F, 0F, 0F, Point),
        Padding(0f, 0f, 0f, 0f, Point),
        0,
        Option(
          TextContent(
            medium,
            22,
            white,
            "John Doe",
            0f
          )
        )
      ),
      Section(
        "TITLE",
        Option("LEFT_COLUMN"),
        Margin(0F, 0F, 0F, 0F, Point),
        Padding(0f, 0f, 0f, 0f, Point),
        1,
        Option(
          TextContent(
            regular,
            10,
            white,
            "Web and Graphics Designer",
            0.6f
          )
        )
      )
    )
