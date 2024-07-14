<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output omit-xml-declaration="yes" indent="yes" method="xml" />

    <xsl:template match="foo">
      <xsl:for-each select="node()">
        <xsl:value-of select="@id" />
      </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
