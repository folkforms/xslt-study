# Introduction

## Interfaces

`Node` is an interface.

`Document`, `Element`, `Attr`, `Text`, `Comment` and `ProcessingInstruction` are all interfaces that extend the `Node` interface.

## Text nodes are nodes

A text node is a node (`Text`) like anything else. `Node.getTextContent()` is a convenience wrapper around `Node.getChildren().get(0).getNodeValue()`. (FIXME This is correct but double-check the specifics)

## Tags vs Elements

A tag is the text between (and including) the angle brackets (< and >). There are start tags (`<foo>`), end tags (`</foo>`), and empty tags (`<foo />`).

`<foo>` is a tag.

`<foo><bar/></foo>` is an element.

A tag consists of an element name and, if it is a start tag or an empty tag, some optional attributes. An end tag cannot have attributes.

An element consists of its start and end tags and everything in between. This might include text, other elements, and comments, as well as other things such as entity references and processing instructions.

## CDATA nodes

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
