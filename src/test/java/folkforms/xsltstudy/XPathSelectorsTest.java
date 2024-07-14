package folkforms.xsltstudy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class XPathSelectorsTest {

  @Test
  public void dotSelectorTest() throws IOException {
    String inputFile = "src/test/resources/xpath-selectors/example1-input.xml";
    String stylesheetFile = "src/test/resources/xpath-selectors/dot-selector-stylesheet.xsl";
    Xslt x = Xslt.fromDisk(inputFile, stylesheetFile);

    String output = x.transform();
    System.out.println(output);

    assertEquals("foo-1", output);
  }

  @Test
  public void starSelectorTest() throws IOException {
    String inputFile = "src/test/resources/xpath-selectors/example1-input.xml";
    String stylesheetFile = "src/test/resources/xpath-selectors/star-selector-stylesheet.xsl";
    Xslt x = Xslt.fromDisk(inputFile, stylesheetFile);

    String output = x.transform();
    System.out.println(output);

    assertEquals("bar-1bar-2bar-3", output);
  }
}
