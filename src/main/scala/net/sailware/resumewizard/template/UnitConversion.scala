package net.sailware.resumewizard.template

object UnitConversion:
  private val pointsPerInch = 72
  private val pointsPerMM = 1F / (10F * 2.54F) * pointsPerInch.toFloat

  def mmToPoint(mm: Float): Float =
    mm * pointsPerMM
