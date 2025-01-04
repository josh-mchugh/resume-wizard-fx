package net.sailware.resumewizard.template

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PageTest:

  @Test
  def whenPageHasWidthExpectWidth(): Unit =
    val page = Page(1, 0)
    Assertions.assertEquals(1, page.width)

  @Test
  def whenPageHasHeightExpectHeight(): Unit =
    val page = Page(0, 1)
    Assertions.assertEquals(1, page.height)

  @Test
  def whenPageIsA4ExpectWidth(): Unit =
    val page = Page.A4
    Assertions.assertEquals(595.27563f, page.width)

  @Test
  def whenPageIsA4ExpectHeight(): Unit =
    val page = Page.A4
    Assertions.assertEquals(841.8898f, page.height)
