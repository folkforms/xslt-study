package folkforms.xsltstudy;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.camel.util.xml.StringSource;

public class Xslt {

  private String input;
  private String stylesheet;

  private Xslt(String input, String stylesheet) {
    this.input = input;
    this.stylesheet = stylesheet;
  }

  public String transform() {
    try {
      Source xmlSource = new StringSource(input);
      Source xsltSource = new StringSource(stylesheet);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer(xsltSource);
      StringWriter stringWriter = new StringWriter();
      StreamResult result = new StreamResult(stringWriter);

      transformer.transform(xmlSource, result);
      String output = stringWriter.toString();

      return output;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static Xslt fromText(String input, String stylesheet) {
    return new Xslt(input, stylesheet);
  }

  public static Xslt fromDisk(String inputFile, String stylesheetFile) throws IOException {
    String input = Files.readString(Path.of(inputFile));
    String stylesheet = Files.readString(Path.of(stylesheetFile));
    return new Xslt(input, stylesheet);
  }
}
