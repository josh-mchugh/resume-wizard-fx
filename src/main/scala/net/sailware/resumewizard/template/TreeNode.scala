package net.sailware.resumewizard.template

sealed trait TreeNode

object EmptyNode extends TreeNode

case class Node(
    val parent: Option[Node],
    val left: Option[Node],
    val x: Option[Float],
    val y: Option[Float],
    val section: Section,
    val children: List[Node]
) extends TreeNode
