<?xml version="1.0"?>
<!-- greeting.xsl -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="bar">
    BAR
  </xsl:template>

  <xsl:template match="muk">
    MUK
  </xsl:template>

  <xsl:template match="foo/muk">
    FOOMUK
  </xsl:template>
</xsl:stylesheet>
