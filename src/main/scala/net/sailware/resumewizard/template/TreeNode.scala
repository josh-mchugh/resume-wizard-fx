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

class NodeBuilder:
  private var parent: Option[Node] = None
  private var left: Option[Node] = None
  private var x: Option[Float] = None
  private var y: Option[Float] = None
  private var margin: Option[Margin] = None
  private var padding: Option[Padding] = None
  private var content: Option[Content] = None
  private var children: List[Node] = List.empty

  def setX(x: Float): NodeBuilder =
    this.x = Some(x)
    this

  def setY(y: Float): NodeBuilder =
    this.y = Some(y)
    this

  def setMargin(margin: Margin): NodeBuilder =
    this.margin = Some(margin)
    this

  def setPadding(padding: Padding): NodeBuilder =
    this.padding = Some(padding)
    this

  def setContent(content: Content): NodeBuilder =
    this.content = Some(content)
    this

  def setChildren(children: List[Node]): NodeBuilder =
    this.children = children
    this

  def build(): Node =
    Node(
      parent,
      left,
      x,
      y,
      margin.getOrElse(Margin(0, 0, 0, 0, UnitType.Pixel)),
      padding.getOrElse(Padding(0, 0, 0, 0, UnitType.Pixel)),
      content,
      children
    )
