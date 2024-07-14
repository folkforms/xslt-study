# XPath selectors

Example document:

    <foo id="foo-1">
      <bar id="bar-1">
        <muk id="muk-1.1" />
        <muk id="muk-1.2" />
      </bar>
      <bar id="bar-2">
        <muk id="muk-2.1" />
        <muk id="muk-2.2" />
      </bar>
      <bar id="bar-3">
        <muk id="muk-3.1" />
        <muk id="muk-3.2" />
      </bar>
    </foo>

### Single dot selector "."

    <xsl:value-of select="." />

This template selects the context node, represented by a dot.

For example:

    <xsl:template match="/foo">
      <xsl:for-each select=".">
        <xsl:value-of select="@id">
      </xsl:for-each>
    </xsl:template>

Will return "foo-1".

### Star/Asterisk selector "*"

    <xsl:value-of select="*" />

Selects all element nodes in the current context.

For example:

    <xsl:template match="/foo">
      <xsl:for-each select="*">
        <xsl:value-of select="@id">
      </xsl:for-each>
    </xsl:template>

Will return "bar-1bar-2bar-3".

### Node selector "node()"

Selects all nodes in the current context, regardless of type. This includes elements, text, comments, processing instructions, attributes, and namespace nodes.

    <xsl:template match="foo">
      <xsl:for-each select="node()">
        <xsl:value-of select="."/>
      </xsl:for-each>
    </xsl:template>

Will return "bar-1\nbar-2\nbar-3".

### At selector "@*"

    <xsl:value-of select="@*" />

Selects all attribute nodes in the current context.

    <xsl:template match="bar">
      <xsl:for-each select="@*">
        <xsl:value-of select="."/>
      </xsl:for-each>
    </xsl:template>

Will return "bar-1\nbar-2\nbar-3".
