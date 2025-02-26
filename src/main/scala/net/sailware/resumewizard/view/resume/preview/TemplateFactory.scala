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
          height = Some(80F),
          order = 1,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_2",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 2,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_3",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 3,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_4",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 4,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_5",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 5,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_6",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 6,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_7",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 7,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_8",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 8,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_9",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 9,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_10",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 10,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_11",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 11,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_12",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 12,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_13",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 13,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_14",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 14,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_15",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 15,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_16",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 16,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
        SectionTemplate(
          id ="CONTENT_17",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 17,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(61, 141, 122))
        ),
        SectionTemplate(
          id ="CONTENT_18",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(80F),
          order = 18,
          margin = Margin(0F, 0F, 10F, 0),
          background = Background(color = Color.rgb(163, 209, 198))
        ),
      )
    )
