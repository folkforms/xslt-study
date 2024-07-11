<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:c="c"
  xmlns:xalan="xalan"
  xmlns:util="folkforms.xslt.XsltUtils">
<xsl:output omit-xml-declaration="yes" indent="yes" />
<xsl:param name="myJava" />
<xsl:param name="designation" />

<xsl:output method="html"/>

  <!-- Identity template to copy nodes as they are -->
  <xsl:template match="@* | node()">
    <xsl:copy>
      <xsl:apply-templates select="@* | node()"/>
    </xsl:copy>
  </xsl:template>

  <!-- Match c:ing nodes and transform them -->

  <!-- The order of these xsl:template match statements matters -->
  <!-- The xsl:apply-templates in xsl:template match="//c:ing" determines how you handle c:ings within c:ings -->

  <xsl:template match="//c:ing/c:ing">
    c:ing within c:ing was transformed to this
    <xsl:copy-of select="util:transformNode(@value)"/>
  </xsl:template>

  <xsl:template match="//c:ing">
    <xsl:copy-of select="util:transformNode(@value)"/>
    <xsl:apply-templates select="@* | node()"/>
  </xsl:template>

</xsl:stylesheet>
