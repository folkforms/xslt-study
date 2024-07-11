# Mathematical operators

`<xsl:value-of select="1 + 2"/>`

## Addition (+)

- All numbers are floats (as mentioned earlier)
- Strings representing numbers e.g. "4" or "1.5" are converted to numbers
- If a string can't be converted to a number ('Q'), the result is `NaN`
- The boolean values returned by `true()` and `false()` are converted to 1 and 0, respectively

FIXME `<xsl:value-of select="9 + '4'" version="1.0"/>` -- version means process as XSLT 1.0 stylesheet. Maybe more on this later? If not, delete this.

In XPath 2.0, the plus sign can be used to add combinations of dates, times, and durations. For example, adding two `xs:yearMonthDuration`s, two `xs:dayTimeDuration`s, adding an `xs:yearMonthDuration` to an `xs:dateTime`, adding an `xs:dayTimeDuration` to an `xs:dateTime`, adding an `xs:yearMonthDuration` to an `xs:date`, adding an `xs:dayTimeDuration` to an `xs:date`, adding an `xs:dayTimeDuration` to an `xs:time`, etc.

You can't add an `xs:yearMonthDuration` to an `xs:time`.

Example:

    <xsl:variable name="dTD1" as="xs:dayTimeDuration" select="xs:dayTimeDuration('P5DT9H23M12S')"/>
    <xsl:variable name="dTD2" as="xs:dayTimeDuration" select="xs:dayTimeDuration('P3DT16H12M17S')"/>
    <xsl:value-of select="($dTD1, '+', $dTD2, '=', $dTD1 + $dTD2)"/>

## Subtraction (-)

Pretty much what you would expect.

One thing to note is you can get negative timestamp values e.g.: `P1Y8M - P2Y7M = -P11M`

## Multiplication (*)

Pretty much what you would expect.

Remember that the numbers are floats, so you sometimes get weird results like: `9 * 3.8 = 34.199999999999996`.

The results in XPath 2.0 are cleaner; `34.19999...` is rounded to `34.2`.

In XPath 2.0, you can multiply `xs:yearMonthDurations` and `xs:dayTimeDurations` by numeric values:

    <xsl:variable name="yMD1" as="xs:yearMonthDuration" select="xs:yearMonthDuration('P1Y8M')"/>
    <xsl:value-of select="($yMD1, '* 3 =', $yMD1 * 3)"/>

## Division (div)

The operator `div` is used instead of the usual `/`, since the latter is already used for location paths.

Note that `9 div 0`, returns the value `INF` (infinity.)

In XPath 2.0, you can also divide durations in four different ways:

- Divide an `xs:yearMonthDuration` by an `xs:double`
- Divide one `xs:yearMonthDuration` by another
- Divide an `xs:dayTimeDuration` by an `xs:double`
- Divide one `xs:dayTimeDuration` by another

## Integer division (idiv)

XPath 2.0 introduces the idiv operator for integer division. The rules for integer division in XPath 2.0 are different from the rules you might know from C++ and Java; no rounding is done if there is any remainder from the division.

Example:

    9 idiv 3.8 = 2
    9 idiv 0 // error
    9 idiv number('Q') // error

# Modulo (mod)

    9 mod 3 = 0
    9 mod 3.8 = 1.4000000000000004 // 1.4 in XPath 2.0
    9 mod '4' = 1
    9 mod 'Q' = NaN
    9 mod true() = 0
    9 mod false() = NaN

## Unary minus (–x)

For example, if x = 10, then -x will return -10.

The negation of 0 or 0.0 is 0 and 0.0, respectively.

If the argument is an `xs:float` or `xs:double`, NaN returns NaN, 0.0E0 returns –0.0E0, –0.0E0 returns 0.0E0, INF returns –INF, and –INF returns INF.

## Unary plus (+x)

Makes no change to the operand. Just included here for completeness.
