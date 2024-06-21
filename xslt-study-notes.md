# XSLT notes

## Miscellaneous

### Interfaces

`Node` is an interface.

`Document`, `Element`, `Attr`, `Text`, `Comment` and `ProcessingInstruction` all all interfaces that extend the `Node` interface.

### Text nodes are nodes

A text node is a node (`Text`) like anything else. Node.getTextContent() is a convenience wrapper around Node.getChildren().get(0).getNodeValue().
(FIXME This is correct but double-check the specifics)

### Tags vs Elements

A tag is the text between (and including) the angle brackets (< and >). There are start tags (<foo>), end tags (</foo>), and empty tags (<foo />).

A tag consists of an element name and, if it is a start tag or an empty tag, some optional attributes. An end tag cannot have attributes.

An element consists of its start and end tags and everything in between. This might include text, other elements, and comments, as well as other things such as entity references and processing instructions.

`<foo>` is a tag.

`<foo><bar/></foo>` is an element.

### CDATA nodes

CDATA stands for Character Data and it means that the data in between these strings includes data that *could* be interpreted as XML markup, but should not be.

    <![CDATA[
      Since this is a CDATA section
      I can use all sorts of reserved characters
      like > < " and &
      or write things like
      <foo></bar>
      but my document is still well formed!
    ]]>

## Tools

### DOM

A Dom parser will parse the entire document and create a tree structure to represent it. For example, it will create an `org.w3c.dom.Document` object.

### SAX

Sax is a tool to process a document and send events to your code as it does so. It's up to you to do something with those events and/or store the data. Once they're gone, they're gone.

### FOP

The Apache XML Project's FOP tool is used to convert XML to PDF, PS, PCL, AFP, XML (area tree representation), Print, AWT and PNG.

## XSLT processing

An XSLT processor will process an xml document as follows:

1. "Do I have any nodes to process?" The nodes to process are represented by the context. Initially, the context is the root of the XML document, but it changes throughout the stylesheet.
2. While any nodes are in the context, do the following: Get the next node from the context.
3. "Do I have any `<xsl:template>`s that match the context?" If one or more `<xsl:template>`s match, pick the right one and process it. The right one is the most specific template. For example, `<xsl:template match="/html/body/h1/p">` is more specific than `<xsl:template match="p">`.
4. The process begins again with the context changed to the selected node.

FIXME The above means order does not matter.

## XSLT processing

### Dots, Asterisks and Ands

`<xsl:value-of select="."/>` the '.' means the value of the current node.

### `<xsl:import>` vs `<xsl:include>`

Anything imported via `<xsl:import>` will have a lower priority than what is in the current stylesheet.

FIXME I presume anything included via <xsl:include> will error if it clashes with the current stylesheet.

### `<xsl:strip-space>` and `<xsl:preserve-space>`

These elements contain a space-separated list of elements from which whitespace should be removed or preserved in the output. To define these elements globally, use `<xsl:strip-space elements="*"/>` or `<xsl:preserve-space elements="*"/>`. If we want to specify that whitespace be removed for all elements except for `<greeting>` and `<salutation>` elements, we would add this markup to our stylesheet:

    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="greeting salutation"/>

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

### `<xsl:param>`

This element defines a parameter. As with `<xsl:variable>`, any `<xsl:param>` that is a top-level element is global to the entire stylesheet.

FIXME Difference between variables vs params

## XPath

### "/" is the root node

The root node is the XPath node that contains the entire document. In a document "`<foo />`", "/" is not the foo element. It contains the "foo" element. The "foo" element is the document element. This is because the file might have comments, processing instructions, etc. alongside the document element.

### Attributes

An element node is the parent of its attribute nodes, but those attribute nodes are not children of their parent.

The children of an element are the text, element, comment, and processing instruction nodes contained in the original element. If you want a document's attributes, you must ask for them specifically. Yes, this is weird. But it's for the best. (FIXME I hope the book eventually gets back to this)

### Node tests

XPath also has node tests. A node test looks like a function, but is used to match certain types of nodes. A node test works like a predicate in that it returns only nodes that meet certain criteria:

(FIXME Shorten all of the text below)

- node() - Matches all nodes. The test node() is true for every kind of node
- text() - Matches text nodes only
- comment() - Matches comment nodes
- processing-instruction() - Matches processing instruction nodes. If this node test includes a string, it matches processing instruction nodes with that name. For example, processing-instruction('cocoon-process') matches processing instruction nodes that begin with `<?cocoon-process>`.
- * - Matches all the nodes along a particular axis. For example, child::* matches all the element children of a node, whereas attribute::* matches all the attributes of a node.
- NCName:* - Matches all the nodes in a particular namespace. With `<auth:author xmlns:auth="http://www.authors.com/">`, auth:* matches any element that has the namespace URI http://www.authors.com/.
- element() - Matches any element. Using this node test without arguments is similar to select="*" in XPath 1.0. The difference is that in XPath 2.0, we can use the element() node test to check the name and datatype of an item. For example, element(author) matches any elements named `<author>`. The two-argument version of this node test allows us to find elements that match a particular datatype. The node test element(date-of-birth, xs:gYear) matches all `<date-of-birth> `elements whose datatype is xs:gYear. (That includes datatypes derived from xs:gYear.) Finally, we can use a wildcard for the element name to find all of the elements of a particular datatype. element(*, xs:gYear) matches all elements with a datatype of xs:gYear.
- schema-element(author) - Matches any element named `<author>` whose datatype matches the datatype of the `<author>` element declared in a schema. Unlike all the other node tests defined in XPath 1.0 and 2.0, it is an error to use this node test without an argument.
- attribute() - Matches any attribute. Using this node test without arguments is similar to select="@*" in XPath 1.0. As with element(), we can use attribute() to check the name and datatype of an item. The node test attribute(public-domain) matches any attributes named public-domain, regardless of the datatype. attribute(public-domain, xs:string) matches only attributes named public-domain with a datatype of xs:string. Finally, using a wildcard for the attribute name returns all the attributes that match a particular datatype. For example, attribute(*, xs:decimal) matches all attributes whose datatype is xs:decimal, regardless of the name of the attribute.
- *:NCName - Matches all the nodes with a particular local name. In our sonnet, *:author matches the `<auth:author>` element; it would also match the elements `<xyz:author>` and `<author>`. The match occurs regardless of the namespace URI or whether an element has a namespace URI at all.
- document-node() - Matches document nodes. The node test document-node(element(sonnet)) matches a document node with a single element child (<sonnet>). The document node can also contain comments or processing instructions, but it can only contain a single element node.

Note: Although item() looks like a node test, it is used only as a datatype.

### Location paths

A _location path_ describes the location of something in an XML document. The pattern `/addressbook/address/city` describes the location of the elements we want to select.

### The Context

Everything we do in XPath is interpreted with respect to the context.

You can think of an XML document as a hierarchy of directories in a filesystem. If I go to a command line and execute a particular command (such as `ls *.xsl`), the results I get vary depending on the current directory. Similarly, the results of evaluating an XPath expression will probably vary based on the context.
