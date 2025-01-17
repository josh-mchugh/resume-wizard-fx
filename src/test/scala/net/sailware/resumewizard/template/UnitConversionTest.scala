package net.sailware.resumewizard.template

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UnitConversionTest:

  @Test
  def when1MMThenExpectPointValue(): Unit =
    Assertions.assertEquals(2.8346456693f, UnitConversion.mmToPoint(1f))

  @Test
  def when1MMThenExpectPxValue(): Unit =
    Assertions.assertEquals(3.7795275590551185f, UnitConversion.mmToPx(1f))
