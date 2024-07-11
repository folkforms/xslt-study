package folkforms.xsltstudy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class Chapter2Test {

  @Test
  public void htmlGreeting() throws IOException {
    String input = Files.readString(Path.of("src/test/resources/book/chapter2/input.xml"));
    String stylesheet = Files
        .readString(Path.of("src/test/resources/book/chapter2/html-greeting.xsl"));
    String expected = Files.readString(Path.of("src/test/resources/book/chapter2/expected.html"));
    XsltTestUtils.assertTransform(input, stylesheet, expected);
  }

  // Output is identical except that attributes and formatting are messed up
  // @Test
  public void svgGreeting() throws IOException {
    String input = Files.readString(Path.of("src/test/resources/book/chapter2/input.xml"));
    String stylesheet = Files
        .readString(Path.of("src/test/resources/book/chapter2/svg-greeting.xsl"));
    String expected = Files
        .readString(Path.of("src/test/resources/book/chapter2/expected.svg.xml"));
    XsltTestUtils.assertTransform(input, stylesheet, expected);
  }
}
