<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output omit-xml-declaration="yes" indent="yes" />
  <xsl:output method="html"/>

  <xsl:template match="person">
    <!-- This works fine -->
    <!--
      hi there <xsl:value-of select="@name" />!
    -->
    <!-- This only works for "literal result elements" i.e. html tags. Plain text will not work. -->
    <table border="{@name}" />
  </xsl:template>

</xsl:stylesheet>
