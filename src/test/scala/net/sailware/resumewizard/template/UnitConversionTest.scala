package net.sailware.resumewizard.template

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UnitConversionTest:

  @Test
  def when1MMThenExpectPointValue(): Unit =
    Assertions.assertEquals(2.8346456693F, UnitConversion.mmToPoint(1F))
