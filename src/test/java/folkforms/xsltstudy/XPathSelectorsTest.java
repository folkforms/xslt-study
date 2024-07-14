package folkforms.xsltstudy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class XPathSelectorsTest {

  private void test(String stylesheetFile, String expectedOutput) throws IOException {
    test(stylesheetFile, expectedOutput, false);
  }

  private void test(String stylesheetFile, String expectedOutput, boolean cleanOutput)
      throws IOException {
    String inputFile = "src/test/resources/xpath-selectors/example1-input.xml";
    Xslt x = Xslt.fromDisk(inputFile, stylesheetFile);
    String output = x.transform();
    // System.out.println(output);

    if (cleanOutput) {
      output = cleanOutput(output);
      expectedOutput = cleanOutput(output);
    }

    assertEquals(expectedOutput, output);
  }

  private String cleanOutput(String output) {
    return Arrays.stream(output.replaceAll("\\r\\n", "\n").split("\n")).map(x -> x.strip())
        .collect(Collectors.joining("\n"));
  }

  @Test
  public void dotSelectorTest() throws IOException {
    test("src/test/resources/xpath-selectors/dot-selector-stylesheet.xsl", "foo-1");
  }

  @Test
  public void starSelectorTest() throws IOException {
    test("src/test/resources/xpath-selectors/star-selector-stylesheet.xsl", "bar-1bar-2bar-3");
  }

  @Test
  public void atSelectorTest() throws IOException {
    test("src/test/resources/xpath-selectors/at-selector-stylesheet.xsl", "bar-1bar-2bar-3", true);
  }

  @Test
  public void nodeSelectorTest() throws IOException {
    test("src/test/resources/xpath-selectors/node-selector-stylesheet.xsl", "bar-1bar-2bar-3");
  }
}
