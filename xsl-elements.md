# Xsl elements

## `<xsl:message>`

Useful for debugging

    <xsl:message>foo = <xsl:value-of select="$foo" /></xsl:message>

## `<xsl:apply-templates>`

    <xsl:apply-templates select="greeting" />

Select all of the `<greeting>` elements in the current context and apply the appropriate template to each of them.

## `<xsl:call-template>`

Basically a function call

    <xsl:template match="...">
      ...
      <xsl:call-template name="addChild">
        <xsl:with-param name="foo" select="$x" />
        <xsl:with-param name="bar" select="$y" />
        <xsl:with-param name="muk" select="$z" />
      </xsl:call-template>
      ...
    </xsl:template>

Is equivalent to calling `addChild(foo, bar, muk)`

If you get a message like "Parameter foo does not exist" it is because the target template `addChild` does not have a parameter with that name.

    <xsl:template name="addChild">
      <xsl:param name="foo" />
      <xsl:param name="bar" />
      <xsl:param name="muk" />
      <!-- Do some work -->
    </xsl:template>

## `<xsl-copy>` vs `<xsl-copy-of>`

`<xsl-copy-of>` is a deep copy. It does not allow any child elements or modification.

`<xsl-copy>` is a shallow copy. It allows new child elements and/or modifications. It can perform a deep copy with the right combination of child elements. This allows you to deep copy but also modify the data. For example:

    <xsl:copy>                        <!-- Shallow copy -->
      <xsl:copy-of select="@*" />     <!-- Copy attributes -->
      <xsl:copy-of select="node()" /> <!-- Copy existing child elements -->
      <xsl:element name="foo" />      <!-- Add new child element -->
    </xsl:copy>

### `<xsl:param>`

    <xsl:template match="foo">
      <xsl:param name="barAttribute" select="@bar" /> <!-- $barAttribute is now the value of 'bar' in <foo bar="..." /> -->

See [xsl:call-template](#xslcall-template) above.

### `<xsl:text>`

Provides some text:

    <xsl:value-of select="/sonnet/author/first-name" />
    <xsl:text> P. </xsl:text>
    <xsl:value-of select="/sonnet/author/last-name" />

Might return "William P. Shakespeare"

----

FIXME Need to go over the text below:

### `<xsl:import>` vs `<xsl:include>`

Anything imported via `<xsl:import>` will have a lower priority than what is in the current stylesheet.

FIXME I presume anything included via `<xsl:include>`` will error if it clashes with the current stylesheet.

### `<xsl:variable>`

This element defines a variable. Any `<xsl:variable>` that appears as a top-level element is global to the entire stylesheet.

    <xsl:variable name="months" as="xs:string*"
      select="('January', 'February', 'March', 'April', 
               'May', 'June', 'July', 'August', 
               'September', 'October', 'November', 'December')"
    />

The `*` at the end of `xs:string*` means that the variable can have any number of values.

A sequence can also use XPath to select nodes from an XML document:

    <xsl:variable name="cities" as="xs:string*">
      <xsl:sequence select="addressbook/address/city"/>
      <xsl:sequence select="('London', 'Adelaide', 'Rome')"/>
      <xsl:sequence select="('Jakarta', 'Sao Paulo', 'Timbuktu')"/>
    </xsl:variable>

The variable `cities` will contain whatever nodes match `addressbook/address/city`, plus the hard-coded items.

Using `as="xs:string*"` will convert the nodes matching `addressbook/address/city` into strings. Using, for example, `as="item()*"` would keep them as nodes.

You can rewrite the above as:

    <xsl:variable name="cities" as="xs:string*">
      <xsl:sequence 
        select="(/addressbook/address/city, 
                 ('London', 'Adelaide', 'Rome'), 
                 ('Jakarta', 'Sao Paulo', 'Timbuktu')
                )"/>
    </xsl:variable>
