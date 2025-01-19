package net.sailware.resumewizard.template

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UnitOfTest:

  @Test
  def when1MMThenExpectPointValue(): Unit =
    Assertions.assertEquals(2.8346456693f, UnitOf(UnitType.Millimeter, 1F).toPoint())

  @Test
  def when1MMThenExpectPxValue(): Unit =
    Assertions.assertEquals(3.7795275590551185f, UnitOf(UnitType.Millimeter, 1F).toPx())

  @Test
  def when1MMThenExpectMMValue(): Unit =
    Assertions.assertEquals(1F, UnitOf(UnitType.Millimeter, 1F).toMM())

  @Test
  def when1PxThenExpectPxValue(): Unit =
    Assertions.assertEquals(1F, UnitOf(UnitType.Pixel, 1F).toPx())

  @Test
  def when1PxThenExpectMMValue(): Unit =
    Assertions.assertEquals(0.2645833333F, UnitOf(UnitType.Pixel, 1F).toMM())

  @Test
  def when1PxThenExpectPointValue(): Unit =
    Assertions.assertEquals(0.75F, UnitOf(UnitType.Pixel, 1F).toPoint())

  @Test
  def when1PointThenExpectPxValue(): Unit =
    Assertions.assertEquals(1.3333333333333333F, UnitOf(UnitType.Point, 1F).toPx())

  @Test
  def when1PointThenExpectPointValue(): Unit =
    Assertions.assertEquals(1F, UnitOf(UnitType.Point, 1F).toPoint())

  @Test
  def when1PointThenExpectMMValue(): Unit =
    Assertions.assertEquals(0.3527777778F, UnitOf(UnitType.Point, 1F).toMM())
