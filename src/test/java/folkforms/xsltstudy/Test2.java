package folkforms.xsltstudy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

/**
 * Test using keys to work on groups of child attributes.
 */
public class Test2 {

  @Test
  public void test() throws IOException {
    String input = Files.readString(Path.of("src/test/resources/test2/input.xml"));
    String stylesheet = Files.readString(Path.of("src/test/resources/test2/stylesheet.xsl"));
    String expected = Files.readString(Path.of("src/test/resources/test2/expected.xml"));
    Xslt xslt = Xslt.fromText(input, stylesheet);
    String actual = xslt.transform();
    assertEquals(expected, actual);
  }
}
