<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output omit-xml-declaration="yes" indent="yes" />

  <!-- Identity template to copy nodes as they are -->
  <xsl:template match="@* | node()">
    <xsl:copy>
      <xsl:apply-templates select="@* | node()"/>
    </xsl:copy>
  </xsl:template>

  <xsl:key name="key-name" match="foo" use="@name"/>

  <xsl:template match="parent">
    <xsl:for-each select="foo[count(. | key('key-name', @name)[1]) = 1]">
      <!-- "The returned node-set will contain every node that has this value for the given key" -->
      <xsl:message>ONE: <xsl:value-of select="@name" /></xsl:message>
      <xsl:variable name="name" select="@name" />
      <xsl:call-template name="foobar">
        <xsl:with-param name="nodeSet" select="//foo[@name = $name]" />
      </xsl:call-template>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="foobar">
    <xsl:param name="nodeSet"/>
    <xsl:element name="fooGroup">
      <xsl:for-each select="$nodeSet">
        <xsl:sort select="@other" />
        <xsl:message>TWO: <xsl:value-of select="@name" /> / <xsl:value-of select="@other" /></xsl:message>
        <xsl:element name="bar">
          <xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute> 
          <xsl:attribute name="other"><xsl:value-of select="@other" /></xsl:attribute>
        </xsl:element>
      </xsl:for-each>
    </xsl:element>
  </xsl:template>

</xsl:stylesheet>
