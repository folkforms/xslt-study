<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output omit-xml-declaration="yes" indent="yes" method="xml" />

  <!-- Identity template to copy nodes as they are -->
  <xsl:template match="@* | node()">
    <xsl:copy>
      <xsl:apply-templates select="@* | node()" />
    </xsl:copy>
  </xsl:template>

  <xsl:template match="bar">
    <xsl:copy>
      <xsl:copy-of select="@*" />
      <xsl:copy-of select="node()" />
      <xsl:for-each select="../muk">
        <xsl:copy-of select="." />
      </xsl:for-each>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="muk" />

</xsl:stylesheet>
