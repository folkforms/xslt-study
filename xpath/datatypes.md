# Datatypes in XPath

## Datatypes in XPath 1.0

In XPath 1.0, an expression returns one of four datatypes:

### node-set

Represents a set of nodes. The set can be empty or it can contain any number of nodes.

### boolean

Represents the value true or false. Be aware that the "true" or "false" strings have no special meaning or value in XPath (FIXME He says see "Converting to boolean values" in Chapter 5 for a more detailed discussion of these so I guess we'll get back to them)

### number

Represents a floating-point number. All numbers in XPath and XSLT are implemented as floating-point numbers; the integer (or int) datatype does not exist in XPath and XSLT,
    - There are also five special values for numbers: positive and negative infinity, positive and negative zero, and NaN, the special symbol for anything that is not a number. (FIXME How do we represent these or test against them?)

### string

Represents zero or more characters, as defined in the XML specification

## Datatypes in XPath 2.0

We'll start with the basic datatypes. These are the only datatypes supported by a basic XSLT processor.

To support other datatypes, including datatypes we define (`po:purchaseOrder`, for example) and derived types defined in XML Schema (such as `xs:nonNegativeInteger`), you need a schema-aware XSLT processor.

Old way:

    <xsl:variable name="sample" select="'3'" as="xs:integer"/>

New constructor functions:

    <xsl:variable name="sample" select="xs:integer(3)"/>
    <xsl:variable name="birthday" select="xs:date('1995-02-11')"/>

### xs:string

The `xs:string` datatype represents a string. Every datatype supported by XPath 2.0 has a string representation. If you want to see how a datatype looks as a string, the XSLT `<xsl:value-of>` element will do the trick. You can convert anything to a string by using the constructor function `xs:string()`. The constructor is the equivalent of the Java `toString()` method that's inherited by every class.

### xs:boolean

As in XSLT 1.0, the string values "true" and "false" don’t have any special meaning. To work with the boolean values themselves, XPath provides the `true()` and `false()` functions which return the corresponding boolean values. You can also use the `xs:boolean()` constructor to create boolean values. `xs:boolean(1)` creates the value true, while `xs:boolean(0)` creates the value false.

### xs:decimal

A numeric value consisting of decimal digits (0 through 9), beginning with an optional plus or minus sign. It cannot contain an exponent. The XML Schema spec states that an implementation must support a minimum of 18 decimal digits. The values 42, 8.37284, and –83982.22 are all legal `xs:decimal` values.

### xs:float and xs:double

Unlike xs:decimal, xs:float and xs:double values can have exponents. There are three special values of xs:float and xs:double: INF (infinity), –INF (negative infinity), and NaN (not a number). The values 42, 8.37284, –83982.22, –1.11e4, INF, and –0 are all valid values for an xs:float or xs:double.

### xs:integer

An integer is a number without a decimal point. An integer can include a plus or minus sign (+ or –) to indicate a positive or negative value. If neither is present the integer is assumed to be positive.

### xs:duration

Represents a span of time. It has six components: year, month, day, hour, minute, and second. The XML Schema spec states that an implementation should support at least a four-digit year and a seconds value with at least three decimal points (millisecond precision). A duration of 1 year, 7 months, 18 days, 4 hours, 27 minutes, and 3.673 seconds is written as `P1Y7M18DT4H27M3.673S`.

### Date and time values

There are three datatypes for dates and times: `xs:date`, `xs:time`, and `xs:dateTime`. The format of a date is YYYY-MM-DD, as in 1995-04-21 for April 21st, 1995. A time value is in the format hh:mm:ss.sss, so 17:38:22.183 is the same as 22.183 seconds past 5:38 p.m.

Both `xs:date` and `xs:time` values have an optional time zone indicator, shown by a plus or minus sign (+ | –) that indicates that the date or time is some number of hours ahead or behind Coordinated Universal Time (UTC, also known as Greenwich Mean Time). For example, during the winter (when Daylight Savings Time is not in effect), the time zone on the East coast of the United States is -05:00. Be aware that if the XSLT processor normalizes a date or time value, parts of the value can change. The time value 17:30:22.183-05:00 is the same as the time value 00:30:22.183Z (a date or time value that ends with Z has been normalized to UTC).

Finally, an `xs:dateTime` value is the combination of an `xs:date` and an `xs:time`. The written representation of an `xs:dateTime` has a `T` between the two portions. To combine our earlier examples, 1995-04-21T17:38:22.183-05:00 is 22.183 seconds past 5:38 p.m. on April 21st, 1995, five hours behind UTC. That value is equivalent to 1995-04-22T00:38:22.183Z.

Be aware that `xs:date`, `xs:time` and `xs:dateTime` can have negative values.

### Parts of date and time values

XML Schema defines the datatypes `xs:gYearMonth`, `xs:gYear`, `xs:gMonthDay`, `gDay` and `gMonth`. Examples of these values, in order, are 1995-04 for April, 1995; 1995 for the year 1995; --04-21 for the 21st day of April; ---21 for the 21st day of a month; and --04 for April.

### xs:hexBinary and xs:base64Binary

The `xs:hexBinary` datatype is a string composed of binary octets. In other words, it must contain only pairs of hexadecimal digits ([0-9a-fA-F]).

The `xs:base64Binary` datatype uses base 64 encoding to represent binary data. It is also a string. An xs:base64Binary value consists of the 65 characters defined in RFC2045: [0-9a-zA-Z], the plus sign (+), the forward slash (/), and the equals sign (=), along with whitespace characters. (The RFC is available at http://www.ietf.org/rfc/rfc2045.txt.)

### xs:anyURI

An `xs:anyURI` value is any string that forms a valid URI as defined by RFC 2396 (and later updated by RFC2732). The RFCs are available at http://www.ietf.org/rfc/rfc2396.txt and http://www.ietf.org/rfc/rfc2732.txt.

### xs:QName

An `xs:QName` (qualified name) is an XML name qualified with a namespace prefix. For example, `auth:author` is a qualified name. To use an `xs:QName` in a stylesheet, the namespace prefix must be in scope.

### xs:anyType and xs:anySimpleType

The datatype `xs:anyType` is considered the base datatype (called the ur-type in the XML Schema datatypes spec) from which all other datatypes are derived. A value of `xs:anyType` can contain any data; it is not constrained in any way.

The `xs:anySimpleType` datatype is a restricted version of `xs:anyType`. An `xs:anySimpleType` value can be any legal value for any of the primitive datatypes defined in XML Schema. A primitive datatype is a datatype that is not defined in terms of another.

The `xs:float` datatype is a primitive datatype, so an `xs:float` value would be considered `xs:anySimpleType`.

On the other hand, `xs:integer` is not a simple type as it is defined in terms of `xs:float`.

### xs:yearMonthDuration and xs:dayTimeDuration

These two types were added to the XML Schema datatypes namespace by the XPath 2.0 and XQuery 1.0 Data Model spec. They represent the two halves of an `xs:duration`. An `xs:yearMonthDuration` is some number of years, months, and days, whereas an `xs:dayTimeDuration` is some number of days, hours, minutes, and seconds. Both of these datatypes can have negative values. The duration 12 years and 2 months is written as `P12Y2M`. Note that an `xs:yearMonthDuration` doesn't have a days component. The duration 4 days, 7 hours, 47 minutes, and 32.883 seconds is written as `P4DT7H47M32.883S`.

### xs:untyped and xs:untypedAtomic

These datatypes are defined by the XPath 2.0 and XQuery 1.0 Data Model spec. A node that has not been validated has a dynamic type of `xs:untyped`, whereas an atomic value that has not been validated has a dynamic type of `xs:untypedAtomic`.

### xs:anyAtomicType

In XPath 2.0 and XQuery 1.0, all simple types have `xs:anyAtomicType` as their base type. For example, `xs:integer`, `xs:boolean`, and `xs:string` are all derived from `xs:anyAtomicType`. (FIXME But he just said above that `xs:integer` was not a simple type? Or does it get it through float whereas boolean and string get it directly?)
