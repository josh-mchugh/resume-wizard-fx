package net.sailware.resumewizard.template

case class Section(
    val id: String,
    val parentId: Option[String],
    val padding: Padding,
    val order: Int,
    val content: Option[Content]
):
  def getContentHeight(): Float =
    content match
      case Some(content) => padding.top + content.getHeight() + padding.bottom
      case None          => padding.top + padding.bottom
