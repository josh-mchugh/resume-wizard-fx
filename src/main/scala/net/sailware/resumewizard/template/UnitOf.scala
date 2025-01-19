package net.sailware.resumewizard.template

enum UnitType:
  case Millimeter
  case Pixel
  case Point

class UnitOf(unitType: UnitType, value: Float):
  private val pointPerInch = 72F
  private val pxPerInch = 96F

  def toPx(): Float =
    unitType match
      case UnitType.Millimeter => value * 3.7795275591F
      case UnitType.Pixel => value
      case UnitType.Point => value * (pxPerInch / pointPerInch)

  def toPoint(): Float =
    unitType match
      case UnitType.Millimeter => value * 2.8346456693F
      case UnitType.Pixel => value * (pointPerInch / pxPerInch)
      case UnitType.Point => value

  def toMM(): Float =
    unitType match
      case UnitType.Millimeter => value
      case UnitType.Pixel => value * 0.2645833333F
      case UnitType.Point => value * 0.3527777778F
