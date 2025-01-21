package net.sailware.resumewizard.template

sealed trait TreeNode

object EmptyNode extends TreeNode

case class Node(
    val parent: Option[Node],
    val left: Option[Node],
    val x: Option[Float],
    val y: Option[Float],
    val margin: Margin,
    val padding: Padding,
    val content: Option[Content],
    val children: List[Node]
) extends TreeNode:
  def getHeight(): Float =
    content match
      case Some(content) => margin.top + padding.top + content.getHeight() + padding.bottom + margin.bottom
      case None          => margin.top + padding.top + padding.bottom + margin.bottom
