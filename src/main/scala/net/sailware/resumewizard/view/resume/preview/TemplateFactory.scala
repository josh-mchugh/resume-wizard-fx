package net.sailware.resumewizard.view.resume.preview

import scalafx.scene.paint.Color

object TemplateFactory:

  def alternatingGreen18(): List[Template] =
    List(
      Template(
        id = "PAGE_ROOT",
        parentId = None,
        `type` = TemplateType.Page,
        height = 0F,
        order = 0,
        padding = Padding(50F, 50F, 50F, 50F)
      ),
      Template(
        id = "ROW",
        parentId = Some("PAGE_ROOT"),
        `type` = TemplateType.Row,
        height = 0F,
        order = 1,
      ),
      Template(
        id = "COLUMN",
        parentId = Some("ROW"),
        `type` = TemplateType.Column,
        height = 0F,
        order = 1,
      ),
      Template(
        id ="CONTENT_1",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 1,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_2",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 2,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_3",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 3,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_4",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 4,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_5",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 5,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_6",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 6,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_7",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 7,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_8",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 8,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_9",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 9,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_10",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 10,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_11",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 11,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_12",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 12,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_13",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 13,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_14",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 14,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_15",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 15,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_16",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 16,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
      Template(
        id ="CONTENT_17",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 17,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(61, 141, 122))
      ),
      Template(
        id ="CONTENT_18",
        parentId = Some("COLUMN"),
        `type` = TemplateType.Content,
        height = 80F,
        order = 18,
        margin = Margin(0F, 0F, 10F, 0),
        background = Background(color = Color.rgb(163, 209, 198))
      ),
    )
