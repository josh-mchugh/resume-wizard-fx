package net.sailware.resumewizard.template

object UnitConversion:
  private val pointsPerInch = 72
  private val pointsPerMM = 1f / (10f * 2.54f) * pointsPerInch.toFloat

  def mmToPoint(mm: Float): Float =
    mm * pointsPerMM
