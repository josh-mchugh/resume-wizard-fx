package net.sailware.resumewizard.template

enum PageSize(
    val width: Float,
    val height: Float
):
  case A4 extends PageSize(210f, 297f)

enum PageOriginPosition:
  case TopLeft, BottomLeft

enum PageMeasurementUnit:
  case Pixel, Point

case class PageConfig(
    val size: PageSize,
    val originPosition: PageOriginPosition,
    val measurementUnit: PageMeasurementUnit
)

case class CreateChildNodeRequest(
  parent: Node,
  section: Section,
  parentMap: Map[Option[String], Array[Section]],
  config: PageConfig
)

object PageBuilder:

  def apply(sections: Array[Section], config: PageConfig): Node = createTree(sections, config)

  private def createTree(sections: Array[Section], config: PageConfig): Node =
    val parentMap = sections.groupBy(_.parentId)
    sections
      .filter(section => section.parentId == None)
      .sortBy(_.order)
      .map(section =>
        var node = Node(None, None, None, None, section.margin, section.padding, section.content, List.empty)
        node = node.copy(x = Some(calculateX(node, config)), y = Some(calculateY(node, config)))
        node.copy(children = createChildren(CreateChildNodeRequest(node, section,  parentMap, config)))
      )
      .head

  private def createChildren(request: CreateChildNodeRequest): List[Node] =
    if request.parentMap.contains(Option(request.section.id)) then
      val childNodes = request.parentMap(Option(request.section.id))
        .sortBy(_.order)
        .map(section => (section, Node(Option(request.parent), None, None, None, section.margin, section.padding, section.content, List.empty)))

        .toList

      windowS(childNodes)
        .map(group =>
          var node = group(1).get._2
          val left = if group(0).isDefined then Some(group(0).get._2) else Option.empty
          val section = group(1).get._1
          node = node.copy(left = left)
          node = node.copy(x = Some(calculateX(node, request.config)), y = Some(calculateY(node, request.config)))
          node = node.copy(children = createChildren(CreateChildNodeRequest(node, section, request.parentMap, request.config)))
          node
        )
        .toList
    else List.empty

  private def windowS[A](l: List[A]): Iterator[List[Option[A]]] =
    (None :: l.map(Some(_)) ::: List(None)).sliding(3)

  private def calculateX(node: Node, config: PageConfig): Float =
    node.left match
      case Some(sibling) => calculateNodeXOffset(sibling)
      case None =>
        node.parent match
          case Some(parent) => calculateNodeXOffset(parent)
          case None         => 0f

  private def calculateY(node: Node, config: PageConfig): Float =
    node.left match
      case Some(sibling) => calculateNodeYOffset(sibling, config)
      case None =>
        node.parent match
          case Some(parent) => calculateNodeYOffset(parent, config)
          case None         => pageDefaultY(config)

  private def pageDefaultY(config: PageConfig): Float =
    config.originPosition match
      case PageOriginPosition.TopLeft    => 0f
      case PageOriginPosition.BottomLeft => convertToUnit(config.size.height, config)

  private def calculateNodeYOffset(node: Node, config: PageConfig): Float =
    config.originPosition match
      case PageOriginPosition.TopLeft    => node.y.getOrElse(0f) + node.getHeight()
      case PageOriginPosition.BottomLeft => node.y.getOrElse(0f) - node.getHeight()

  private def calculateNodeXOffset(node: Node): Float =
    node.x.getOrElse(0f) + node.padding.left

  private def convertToUnit(value: Float, config: PageConfig): Float =
    config.measurementUnit match
      case PageMeasurementUnit.Pixel => UnitOf(UnitType.Millimeter, value).toPx()
      case PageMeasurementUnit.Point => UnitOf(UnitType.Millimeter, value).toPoint()
