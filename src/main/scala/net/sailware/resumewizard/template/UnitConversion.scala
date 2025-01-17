package net.sailware.resumewizard.template

object UnitConversion:
  private val pointsPerInch = 72f
  private val pointsPerMM = 1f / (10f * 2.54f) * pointsPerInch

  private val mmPerInch = 25.4f
  private val pxPerInch = 96f

  def mmToPoint(mm: Float): Float =
    mm * pointsPerMM

  def mmToPx(mm: Float): Float =
    mm * (pxPerInch / mmPerInch)
