package folkforms.xsltstudy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.camel.util.xml.StringSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public abstract class XsltTestUtils {

  public static void assertTransform(String xml, String stylesheet, String expected) {
    Xslt xslt = Xslt.fromText(xml, stylesheet);
    String actual = xslt.transform();
    boolean ok = compareXml(expected, actual);
    if (!ok) {
      System.out.printf("expected = %s%n", expected);
      System.out.printf("actual = %s%n", actual);
    }
    assertTrue(ok);
  }

  public static boolean compareXml(String expected, String actual) {
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilderFactory.setNamespaceAware(true);
      documentBuilderFactory.setCoalescing(true);
      documentBuilderFactory.setIgnoringElementContentWhitespace(true);
      documentBuilderFactory.setIgnoringComments(true);
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

      Document expectedDoc = documentBuilder.parse(new StringSource(expected).getInputStream());
      expectedDoc.normalizeDocument();
      Document actualDoc = documentBuilder.parse(new StringSource(actual).getInputStream());
      actualDoc.normalizeDocument();

      return expectedDoc.isEqualNode(actualDoc);
    } catch (IOException | ParserConfigurationException | SAXException e) {
      throw new RuntimeException(e);
    }
  }
}
