package net.sailware.resumewizard.template

import org.apache.pdfbox.pdmodel.font.PDFont

object FontUtil:
  def getFontHeight(font: PDFont, size: Float): Float =
    font.getFontDescriptor().getCapHeight() * size / 1000f;
