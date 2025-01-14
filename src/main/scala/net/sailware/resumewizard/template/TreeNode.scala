package net.sailware.resumewizard.template

sealed trait TreeNode

object EmptyNode extends TreeNode

case class Node(
    val parent: Option[Node],
    val left: Option[Node],
    val section: Section,
    val children: List[Node]
) extends TreeNode:

  val x: Float = calculateX()
  val y: Float = calculateY()

  private def calculateX(): Float =
    left match
      case Some(node) => node.x + node.section.padding.left
      case None =>
        parent match
          case Some(node) => node.x + node.section.padding.left
          case None       => 0f

  private def calculateY(): Float =
    left match
      case Some(node) => node.y - node.section.getContentHeight()
      case None =>
        parent match
          case Some(node) => node.y - node.section.getContentHeight()
          case None       => Page.A4.height

object Node:

  def apply(sections: Array[Section]): Node = createTree(sections)

  private def createTree(sections: Array[Section]): Node =
    val parentMap = sections.groupBy(_.parentId)
    sections
      .filter(section => section.parentId == None)
      .sortBy(_.order)
      .map(section =>
        val node = Node(None, None, section, List.empty)
        node.copy(children = createChildren(node, parentMap))
      )
      .head

  private def createChildren(parent: Node, parentMap: Map[Option[String], Array[Section]]): List[Node] =
    if parentMap.contains(Option(parent.section.id)) then
      val childNodes = parentMap(Option(parent.section.id))
        .sortBy(_.order)
        .map(section => Node(Option(parent), None, section, List.empty))
        .toList

      windowS(childNodes)
        .map(group => group(1).get.copy(left = group(0), children = createChildren(group(1).get, parentMap)))
        .toList
    else List.empty

  private def windowS[A](l: List[A]): Iterator[List[Option[A]]] =
    (None :: l.map(Some(_)) ::: List(None)).sliding(3)
