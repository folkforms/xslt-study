# XSLT processing

XSLT will walk the document tree (depth first or breadth first is implementation dependent and doesn't matter) and processes each node.

An XSLT processor will process an xml document as follows:

1. "Do I have any nodes to process?" The nodes to process are represented by the context. Initially, the context is the root of the XML document, but it changes throughout the stylesheet.
2. While any nodes are in the context, do the following: Get the next node from the context.
3. "Do I have any `<xsl:template>`s that match the context?" If one or more `<xsl:template>`s match, pick the right one and process it. The right one is the most specific template. For example, `<xsl:template match="/html/body/h1/p">` is more specific than `<xsl:template match="p">`.
4. The process begins again with the context changed to the selected node.

The initial stylesheet invocation generally does an implicit `<xsl:apply-templates select="/"/>`, that is, it finds the template rule that best matches the root (document) node of the source document.

## Input and output

It's worth noting that XSL does not "modify" the input file, but rather "constructs" a separate output file.

The processor will visit every node in the input document and will apply the appropriate template.

For example:

Imagine I have two templates:
- `<xsl:template select="updateFoo">` counts the number of `bar` elements in the document and sets the count as an attribute on the `foo` element.
- `<xsl:template select="removeBar">` deletes any `bar` elements it finds

You can see the problem here -- Should we delete the bar elements before counting them? Or count them and then delete them?

Which template executes first? It does not matter.

If we happen to walk the `bar` nodes first, the `removeBar` template will transfer nothing to the output document. Then when we walk the `foo` nodes and apply the `updateFoo` template it will count the `bar` nodes, because the `bar` nodes _still exist in the input document_.
