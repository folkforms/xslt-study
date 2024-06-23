# XPath

## The Context

Everything we do in XPath is interpreted with respect to the context.

You can think of an XML document as a hierarchy of directories in a filesystem. If I go to a command line and execute a particular command (such as `ls *.txt`), the results I get vary depending on the current directory. Similarly, the results of evaluating an XPath expression will vary based on the context.

## Location paths

A _location path_ describes the location of something in an XML document. The pattern `/addressbook/address/city` describes the location of the elements we want to select.

### Single dot

    <xsl:value-of select="." />

This template selects the context node, represented by a dot.

### Double dot

    <xsl:value-of select=".." />

This template selects the parent of the context node, represented by two dots.

### Absolute vs relative expressions

An absolute XPath expression begins with a slash ("`/`"), which tells the XSLT processor to start at the root of the document, _regardless of the current context_. In other words, you can evaluate an absolute XPath expression from any context node you want, and the results will be the same.

## Using location paths to select things besides elements

### Selecting attributes

To select an attribute, use the at-sign ("`@`") along with the attribute name. For example, you can select the `type` attribute of the `<sonnet>` element with the XPath expression `/sonnet/@type`. If the context node is the `<sonnet>` element itself, then the relative XPath expression `@type` does the same thing.

### Selecting the text of an element

To select the text of an element, simply refer to it in your expression. The element `<xsl:value-of select="/sonnet/auth:author/last-name" />` returns "Shakespeare" for our [sample sonnet](sonnet.xml). You can also use the `string()` function, although that's typically not necessary.

Be aware that getting the text of an element with children probably doesn't do what you want. For example, the element `<xsl:value-of select="/sonnet/auth:author" />` returns the string "ShakespeareWilliamBritish15641616". All of the text descendants of the `<auth:author>` node are concatenated together. To format these nodes more attractively, you'll have to deal with them individually.

Finally, there is a `text()` node test that selects the text node children of the context item. That being said, you almost never want to use it. Getting the node's text the way we illustrated here is the best way to go.

### Selecting namespace nodes

XPath has one other kind of node--the rarely used _namespace node_. To retrieve namespace nodes, you have to use the _namespace axis_; we'll discuss axes in the "Axes" section later in this chapter. One note about namespace nodes, if you ever have to use them: when matching namespace nodes, the namespace prefix isn't important. As an example, our [sample sonnet](sonnet.xml) used the `auth` namespace prefix, which maps to the value `http://www.authors.com/`. If a stylesheet uses the namespace prefix `writers` to refer to the same URL, then the XPath expression `/sonnet/writers:*` would return the `<auth:author>` element. Even though the namespace prefixes are different, the URLs they refer to are the same.

### The asterisk ("`*`")

Selects all element nodes in the current context.

(FIXME This needs an example)

Be aware that the asterisk wildcard selects element nodes only. Attributes, text nodes, comments, or processing instructions aren't included.

You can also use a namespace prefix with an asterisk. In our [sample sonnet](sonnet.xml), the XPath expression `auth:*` returns all element nodes in the current context that are associated with the namespace URL `http://www.authors.com/`.

### The at-sign and asterisk ("`@*`")

Selects all attribute nodes in the current context.

You can use a namespace prefix with the attribute wildcard. In our [sample sonnet](sonnet.xml), `@auth:*` returns all attribute nodes in the current context that are associated with the namespace URL `http://www.authors.com`.

Example of namespacing attributes:

    <first-name auth:nickname="Bill">William</first-name>

The path `auth:nickname` would match the above expression.

### The `node()` test

Selects all nodes in the current context, regardless of type. This includes elements, text, comments, processing instructions, attributes, and namespace nodes.

### The double slash ("`//`")

The double slash indicates that zero or more elements may occur between the slashes.

`//foo` is an absolute XPath expression because it begins with a slash, but you can also use the double slash at any point in an XPath expression, for example `sonnet//line` and `sonnet/descendant-or-self::node()/line` are equivalent.

**WARNING:** The double slash is a very powerful operator, but be aware that it can make your stylesheets incredibly inefficient. If we use the XPath expression `//line`, the XSLT processor has to check every node in the document to see whether there are any `<line>` elements. The more specific you can be in your XPath expressions, the less work the XSLT processor has to do and the faster your stylesheets will execute.

## Axes

XPath provides a number of axes (plural of axis) that let you specify various collections of nodes.

These are:

- child axis (e.g. `child::foo`) contains the children of the context node. Used by default.
- parent axis contains the parent of the context node, if there is one. (If the context node is the root node, the parent axis returns an empty node-set.) Can be abbreviated with the double-dot "`..`".
- self axis contains the context node itself. Can be abbreviated with a single dot "`.`".
- attribute axis: Contains the attributes of the context node. If the context node is not an element node, this axis is empty. Can be abbreviated with the at sign ("`@`"). The expressions `attribute::type` and `@type` are equivalent.
- ancestor axis: Contains the parent of the context node, the parent's parent, etc. The ancestor axis always contains the root node unless the context node is the root node.
- ancestor-or-self axis: As above, but contains the context node as well.
- descendant axis: Contains all children of the context node, all children of all the children of the context node, and so on. The children are all of the comment, element, processing instruction, and text nodes beneath the context node, but not the attribute or namespace nodes.
- descendant-or-self axis: As above, but contains the context node as well.
- preceding-sibling axis: Contains all preceding siblings of the context node; in other words, all nodes that have the same parent as the context node and appear before the context node in the XML document. If the context node is an attribute node or a namespace node, the preceding-sibling axis is empty.
- following-sibling axis: Contains all the following siblings of the context node; in other words, all nodes that have the same parent as the context node and appear after the context node in the XML document. If the context node is an attribute node or a namespace node, the following-sibling axis is empty.
- preceding axis: Contains all nodes that appear before the context node in the document, except ancestors, attribute nodes, and namespace nodes.
- following axis: Contains all nodes that appear after the context node in the document, except descendants, attribute nodes, and namespace nodes.
- namespace axis: Contains the namespace nodes of the context node. Only works for element notes, otherwise it is empty.

## Predicates

Predicates are filters that restrict the nodes selected by an XPath expression.

For example, these expressions both select the seventh `<line>` element in the current context:

    <xsl:apply-templates select="line[position() = 7]" />
    <xsl:apply-templates select="line[7]" />

The expression `line[position()=3 and @style]` matches all `<line>` elements that occur third and that have a style attribute, while `line[position()=3 or @style]` matches all `<line>` elements that either occur third or have a style attribute.

### Multiple predicates

You can use more than one predicate if you like; `line[3][@style]` or `line[@style][3]` are both legal. They aren't equivalent, however. Predicates are evaluated from left to right.

For the first pattern, the processor selects all of the `<line>` nodes that appear third in a set of sibling `<line>` nodes, then selects all of those nodes that have a style attribute.

For the second pattern, the processor selects all the `<line>` elements that have a style attribute, then selects the third node from that sequence.

The first pattern can match any number of nodes, while the second pattern will never match more than one.

In general, the first predicate filters the nodes, the second predicate filters the nodes that made it past the first predicate, and then the third predicate filters the nodes that made it past the second predicate, and so on.

### Functions in predicates

In addition to numbers, we can use XPath and XSLT functions inside predicates. Here are some examples:

- `line[last()]`: Selects the last `<line>` element in the current context
- `line[position() mod 2 = 0]`: Selects all even-numbered `<line>` elements
- `sonnet[@type="Shakespearean"]`: Selects all `<sonnet>` elements that have a type attribute with the value Shakespearean. Note that double versus single quotes are not significant; this XPath expression matches either `<sonnet type="Shakespearean">` or `<sonnet type='Shakespearean'>`.
- `ancestor::table[@border="1"]`: Selects all `<table>` ancestors of the current context that have a border attribute with the value "1".
- `count(/body/table[@border="1"])`: Returns the number of `<table>` elements with a border attribute equal to "1" that are children of `<body>` elements that are children of the root node. Notice that in this case we're using a predicate as part of the location path.

FIXME Is there a full list of functions?

## Attribute value templates

    <table border="{@size}" />

In this example, the XPath expression `@size` is evaluated, and its value, whatever that happens to be, is inserted into the output tree as the value of the border attribute. Attribute value templates can be used in any literal result elements in your stylesheet.

(FIXME The book does not go into much detail here. Hopefully it will get back to this.)
(FIXME I have not had a chance to test this out yet)

----

## Miscellaneous

### "`/`" is the root node

The root node is the XPath node that contains the entire document. In a document "`<foo />`", "/" is not the foo element. It contains the "foo" element. The "foo" element is the document element. This is because the file might have comments, processing instructions, etc. alongside the document element.

### Attributes

An element node is the parent of its attribute nodes, but those attribute nodes are not children of their parent.

The children of an element are the text, element, comment, and processing instruction nodes contained in the original element. If you want a document's attributes, you must ask for them specifically. Yes, this is weird. But it's for the best. (FIXME ??? I hope the book eventually gets back to this.)

----

(FIXME Shorten all of the text below)

## Node tests

XPath also has node tests. A node test looks like a function, but is used to match certain types of nodes. A node test works like a predicate in that it returns only nodes that meet certain criteria:

- node() - Matches all nodes. The test node() is true for every kind of node
- text() - Matches text nodes only
- comment() - Matches comment nodes
- processing-instruction() - Matches processing instruction nodes. If this node test includes a string, it matches processing instruction nodes with that name. For example, processing-instruction('cocoon-process') matches processing instruction nodes that begin with `<?cocoon-process>`.
- * - Matches all the nodes along a particular axis. For example, child::* matches all the element children of a node, whereas attribute::* matches all the attributes of a node.
- NCName:* - Matches all the nodes in a particular namespace. With `<auth:author xmlns:auth="http://www.authors.com/">`, auth:* matches any element that has the namespace URI http://www.authors.com/.
- element() - Matches any element. Using this node test without arguments is similar to `select="*"` in XPath 1.0. The difference is that in XPath 2.0, we can use the element() node test to check the name and datatype of an item. For example, element(author) matches any elements named `<author>`. The two-argument version of this node test allows us to find elements that match a particular datatype. The node test element(date-of-birth, xs:gYear) matches all `<date-of-birth> `elements whose datatype is xs:gYear. (That includes datatypes derived from xs:gYear.) Finally, we can use a wildcard for the element name to find all of the elements of a particular datatype. element(*, xs:gYear) matches all elements with a datatype of xs:gYear.
- schema-element(author) - Matches any element named `<author>` whose datatype matches the datatype of the `<author>` element declared in a schema. Unlike all the other node tests defined in XPath 1.0 and 2.0, it is an error to use this node test without an argument.
- attribute() - Matches any attribute. Using this node test without arguments is similar to `select="@*"` in XPath 1.0. As with element(), we can use attribute() to check the name and datatype of an item. The node test attribute(public-domain) matches any attributes named public-domain, regardless of the datatype. attribute(public-domain, xs:string) matches only attributes named public-domain with a datatype of xs:string. Finally, using a wildcard for the attribute name returns all the attributes that match a particular datatype. For example, attribute(*, xs:decimal) matches all attributes whose datatype is xs:decimal, regardless of the name of the attribute.
- *:NCName - Matches all the nodes with a particular local name. In our sonnet, *:author matches the `<auth:author>` element; it would also match the elements `<xyz:author>` and `<author>`. The match occurs regardless of the namespace URI or whether an element has a namespace URI at all.
- document-node() - Matches document nodes. The node test document-node(element(sonnet)) matches a document node with a single element child (<sonnet>). The document node can also contain comments or processing instructions, but it can only contain a single element node.

Note: Although item() looks like a node test, it is used only as a datatype.
