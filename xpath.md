# XPath

## "/" is the root node

The root node is the XPath node that contains the entire document. In a document "`<foo />`", "/" is not the foo element. It contains the "foo" element. The "foo" element is the document element. This is because the file might have comments, processing instructions, etc. alongside the document element.

## Attributes

An element node is the parent of its attribute nodes, but those attribute nodes are not children of their parent.

The children of an element are the text, element, comment, and processing instruction nodes contained in the original element. If you want a document's attributes, you must ask for them specifically. Yes, this is weird. But it's for the best. (FIXME I hope the book eventually gets back to this)

## Node tests

XPath also has node tests. A node test looks like a function, but is used to match certain types of nodes. A node test works like a predicate in that it returns only nodes that meet certain criteria:

(FIXME Shorten all of the text below)

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

## Location paths

A _location path_ describes the location of something in an XML document. The pattern `/addressbook/address/city` describes the location of the elements we want to select.

## The Context

Everything we do in XPath is interpreted with respect to the context.

You can think of an XML document as a hierarchy of directories in a filesystem. If I go to a command line and execute a particular command (such as `ls *.xsl`), the results I get vary depending on the current directory. Similarly, the results of evaluating an XPath expression will probably vary based on the context.
