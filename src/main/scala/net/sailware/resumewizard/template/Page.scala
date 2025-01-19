package net.sailware.resumewizard.template

case class Page(
    width: Float,
    height: Float
)

object Page:

  def A4 = Page(210f, 297f)
