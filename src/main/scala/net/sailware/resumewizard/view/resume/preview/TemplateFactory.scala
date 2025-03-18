package net.sailware.resumewizard.view.resume.preview

object TemplateFactory:

  def simpleBorderedPage(): LayoutTemplate =
    LayoutTemplate(
      page = PageTemplate.A4(
        margin = Margin(40F, 40F, 40F, 40F),
        padding = Padding(40F, 40F, 40F, 40F),
        border = Border(width = 2F)
      )
    )

  def twoRowSixColumnTemplate(): LayoutTemplate =
    LayoutTemplate(
      page = PageTemplate.A4(
        padding = Padding(40F, 40F, 80F, 40F),
      ),
      sections = List(
        SectionTemplate(
          id = "ROW1",
          parentId = None,
          `type` = SectionType.Row,
          order = 1,
          height = Some(40F)
        ),
        SectionTemplate(
          id = "ROW2",
          parentId = None,
          `type` = SectionType.Row,
          order = 2,
          margin = Margin(8F, 0F, 0F, 0F)
        ),
        SectionTemplate(
          id = "COLUMN1",
          parentId = Some("ROW1"),
          `type` = SectionType.Column,
          order = 1,
          width = Some(171.666F),
          margin = Margin(0F, 8F, 0F, 0F),
          background = Background(Palette.color(Palette.GRAY_300))
        ),
        SectionTemplate(
          id = "COLUMN2",
          parentId = Some("ROW1"),
          `type` = SectionType.Column,
          order = 2,
          width = Some(171.666F),
          margin = Margin(0F, 8F, 0F, 8F),
          background = Background(Palette.color(Palette.GRAY_300))
        ),
        SectionTemplate(
          id = "COLUMN3",
          parentId = Some("ROW1"),
          `type` = SectionType.Column,
          order = 3,
          width = Some(171.666F),
          margin = Margin(0F, 0F, 0F, 8F),
          background = Background(Palette.color(Palette.GRAY_300))
        ),
        SectionTemplate(
          id = "COLUMN4",
          parentId = Some("ROW2"),
          `type` = SectionType.Column,
          order = 1,
          width = Some(171.666F),
          margin = Margin(0F, 8F, 0F, 0F),
        ),
        SectionTemplate(
          id = "CONTENT1",
          parentId = Some("COLUMN4"),
          `type` = SectionType.Content,
          order = 1,
          height = Some(227.333F),
          margin = Margin(0F, 0F, 8F, 0F),
          background = Background(Palette.color(Palette.GRAY_300))
        ),
        SectionTemplate(
          id = "CONTENT2",
          parentId = Some("COLUMN4"),
          `type` = SectionType.Content,
          order = 2,
          height = Some(50F),
          background = Background(Palette.color(Palette.GRAY_300))
        ),
        SectionTemplate(
          id = "COLUMN5",
          parentId = Some("ROW2"),
          `type` = SectionType.Column,
          order = 2,
          width = Some(171.666F),
          margin = Margin(0F, 8F, 0F, 8F),
          background = Background(Palette.color(Palette.GRAY_300))
        ),
        SectionTemplate(
          id = "COLUMN6",
          parentId = Some("ROW2"),
          `type` = SectionType.Column,
          order = 3,
          width = Some(171.666F),
          margin = Margin(0F, 0F, 0F, 8F),
          background = Background(Palette.color(Palette.GRAY_300))
        ),
      ),
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
          height = Some(60F),
          order = 1,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_DARK))
        ),
        SectionTemplate(
          id ="CONTENT_2",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 2,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_LIGHT))
        ),
        SectionTemplate(
          id ="CONTENT_3",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 3,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_DARK))
        ),
        SectionTemplate(
          id ="CONTENT_4",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 4,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_LIGHT))
        ),
        SectionTemplate(
          id ="CONTENT_5",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 5,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_DARK))
        ),
        SectionTemplate(
          id ="CONTENT_6",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 6,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_LIGHT))
        ),
        SectionTemplate(
          id ="CONTENT_7",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 7,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_DARK))
        ),
        SectionTemplate(
          id ="CONTENT_8",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 8,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_LIGHT))
        ),
        SectionTemplate(
          id ="CONTENT_9",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 9,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_DARK))
        ),
        SectionTemplate(
          id ="CONTENT_10",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 10,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_LIGHT))
        ),
        SectionTemplate(
          id ="CONTENT_11",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 11,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_DARK))
        ),
        SectionTemplate(
          id ="CONTENT_12",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 12,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_LIGHT))
        ),
        SectionTemplate(
          id ="CONTENT_13",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 13,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_DARK))
        ),
        SectionTemplate(
          id ="CONTENT_14",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 14,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_LIGHT))
        ),
        SectionTemplate(
          id ="CONTENT_15",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 15,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_DARK))
        ),
        SectionTemplate(
          id ="CONTENT_16",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 16,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_LIGHT))
        ),
        SectionTemplate(
          id ="CONTENT_17",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 17,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_DARK))
        ),
        SectionTemplate(
          id ="CONTENT_18",
          parentId = Some("COLUMN"),
          `type` = SectionType.Content,
          height = Some(60F),
          order = 18,
          margin = Margin(0F, 0F, 8F, 0),
          background = Background(Palette.color(Palette.GREEN_LIGHT))
        ),
      )
    )

  def resumeTemplate(): LayoutTemplate =
    LayoutTemplate(
      page = PageTemplate.A4(),
      sections = List(
        SectionTemplate(
          id = "ROW",
          parentId = Some("PAGE_ROOT"),
          `type` = SectionType.Row,
          order = 1,
        ),
        SectionTemplate(
          id = "COLUMN1",
          parentId = Some("ROW"),
          `type` = SectionType.Column,
          order = 1,
          width = Some(206.465F),
          padding = Padding(42F, 0F, 0F, 24F),
          background = Background(Palette.color(Palette.PRIMARY))
        ),
        SectionTemplate(
          id = "COLUMN2",
          parentId = Some("ROW"),
          `type` = SectionType.Column,
          order = 2,
          width = Some(388.535F)
        ),
        SectionTemplate(
          id = "NAME",
          parentId = Some("COLUMN1"),
          `type` = SectionType.Content,
          order = 1,
          contentTemplate = Some(
            ContentTemplate(
              resumeDataType = Some(ResumeDataType.Name),
              size = 23F,
              color = Palette.color(Palette.WHITE),
              family = Some(ResumeFontFamily.ROBOTO),
              weight = Some(ResumeFontWeight.Bold)
            )
          )
        ),
        SectionTemplate(
          id = "TITLE",
          parentId = Some("COLUMN1"),
          `type` = SectionType.Content,
          order = 1,
          contentTemplate = Some(
            ContentTemplate(
              resumeDataType = Some(ResumeDataType.Title),
              size = 11F,
              color = Palette.color(Palette.WHITE),
              family = Some(ResumeFontFamily.ROBOTO),
              weight = Some(ResumeFontWeight.Normal)
            )
          )
        )
      )
    )
