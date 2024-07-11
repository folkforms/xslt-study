package folkforms.xsltstudy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

/**
 * Test that order does not matter. You can swap the "muk" and "foo/muk" templates around and you
 * still get the same answer because the most specific one will be used.
 *
 * Strange behaviour: Order does appear to matter if you add "/" or "//" to the match attribute.
 */
public class Test1Test {

  @Test
  public void test1() throws IOException {
    String input = Files.readString(Path.of("src/test/resources/book/test1/input.xml"));
    String stylesheet = Files.readString(Path.of("src/test/resources/book/test1/stylesheet.xsl"));
    String expected = Files.readString(Path.of("src/test/resources/book/test1/expected.html"));
    Xslt xslt = Xslt.fromText(input, stylesheet);
    String actual = xslt.transform();
    assertEquals(expected, actual);
  }
}
