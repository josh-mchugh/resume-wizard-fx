package net.sailware.resumewizard.template

case class Section(
    val id: String,
    val parentId: Option[String],
    val margin: Margin,
    val padding: Padding,
    val order: Int,
    val content: Option[Content]
)
