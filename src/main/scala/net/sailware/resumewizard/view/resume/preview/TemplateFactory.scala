package net.sailware.resumewizard.view.resume.preview

import scalafx.scene.paint.Color

object TemplateFactory:

  def simpleBorderedPage(): LayoutTemplate =
    LayoutTemplate(
      page = PageTemplate.A4(
        margin = Margin(40F, 40F, 40F, 40F),
        padding = Padding(40F, 40F, 40F, 40F),
        border = Border(width = 2F)
      )
    )

  def twoRowSixColumnTemplate(): LayoutTemplate = ???
    /*val page = Page(
      padding = Padding(50F, 50F, 100F, 50F),
    )
    var row1 = Row(
      position = ElementUtil.contentStartPosition(page),
      width = page.contentWidth(),
      height = 50F,
    )
    val r1Column1 = Column(
      position = ElementUtil.contentStartPosition(row1),
      width = row1.contentWidth() / 3,
      height = row1.contentHeight(),
      margin = Margin(0F, 15F, 0F, 0F),
      background = Background(Color.Gray)
    )
    var r1Column2 = Column(
      position = r1Column1.position.copy(x = r1Column1.x + row1.contentWidth() / 3),
      width = row1.contentWidth() / 3,
      height = row1.contentHeight(),
      margin = Margin(0F, 15F, 0F, 15F),
      background = Background(Color.Gray)
    )
    var r1C2Content = Content(
      position = ElementUtil.contentStartPosition(r1Column2),
      width = r1Column2.contentWidth() / 2,
      height = 35F,
      margin = Margin(5F, 0F, 0F, 5F),
      background = Background(Color.WhiteSmoke)
    )
    r1Column2 = r1Column2.copy(content = List(r1C2Content))
    val r1Column3 = Column(
      position = r1Column1.position.copy(x = r1Column1.position.x + row1.contentWidth() / 3 * 2),
      width = row1.contentWidth() / 3,
      height = row1.contentHeight(),
      margin = Margin(0F, 0F, 0F, 15F),
      background = Background(Color.Gray)
    )
    row1 = row1.copy(columns = List(r1Column1, r1Column2, r1Column3))
    var row2 = Row(
      position = row1.position.copy(y = row1.position.y + row1.height),
      width = page.contentWidth(),
      height = page.contentHeight() - row1.height,
      margin = Margin(10F, 0F, 0F, 0F)
    )
    var r2Column1 = Column(
      position = ElementUtil.contentStartPosition(row2),
      width = row2.contentWidth() / 3,
      height = row2.contentHeight(),
      margin = Margin(0F, 15F, 0F, 0F),
    )
    val r2C1Content1 = Content(
      position = ElementUtil.contentStartPosition(r2Column1),
      width = r2Column1.contentWidth(),
      height = r2Column1.contentHeight() / 3,
      margin = Margin(0F, 0F, 10F, 0F),
      background = Background(Color.Gray)
    )
    val r2C1Content2 = Content(
      position = r2C1Content1.position.copy(y = r2C1Content1.position.y + r2C1Content1.height),
      width = r2Column1.contentWidth(),
      height = 50F,
      background = Background(Color.Gray)
    )
    r2Column1 = r2Column1.copy(content = List(r2C1Content1, r2C1Content2))
    val r2Column2 = Column(
      position = r2Column1.position.copy(x = r2Column1.position.x + row2.contentWidth() / 3),
      width = row2.contentWidth() / 3,
      height = row2.contentHeight(),
      margin = Margin(0F, 15F, 0F, 15F),
      background = Background(Color.Gray)
    )
    val r2Column3 = Column(
      position = r2Column1.position.copy(x = r2Column1.position.x + row2.contentWidth() / 3 * 2),
      width = row2.contentWidth() / 3,
      height = row2.contentHeight(),
      margin = Margin(0F, 0F, 0F, 15F),
      background = Background(Color.Gray)
    )
    row2 = row2.copy(columns = List(r2Column1, r2Column2, r2Column3))
    page.copy(rows = List(row1, row2))*/

  def alternatingGreen18(): LayoutTemplate =
    LayoutTemplate(
      page = PageTemplate.A4(padding = Padding(50F, 50F, 50F, 50F)),
      sections = List(
        SectionTemplate(
          id = "ROW",
          parentId = Some("PAGE_ROOT"),
          `type` = SectionType.Row,
          order = 1,
        ),
        SectionTemplate(
          id = "COLUMN",
          parentId = Some("ROW"),
          `type` = SectionType.Column,
          order = 1,
        ),
        SectionTemplate(
          id ="CONTENT_1",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 1,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_2",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 2,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_3",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 3,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_4",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 4,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_5",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 5,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_6",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 6,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_7",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 7,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_8",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 8,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_9",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 9,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_10",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 10,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_11",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 11,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_12",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 12,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_13",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 13,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_14",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 14,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_15",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 15,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_16",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 16,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_17",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 17,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_18",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 18,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
      )
    )
