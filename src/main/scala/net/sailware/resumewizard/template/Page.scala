package net.sailware.resumewizard.template

case class Page(
  width: Float,
  height: Float
)

object Page:

  def A4 = Page(UnitConversion.mmToPoint(210F), UnitConversion.mmToPoint(297F))
