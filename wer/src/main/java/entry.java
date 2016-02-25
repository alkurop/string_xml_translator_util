import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class entry {
    public static void main(String args[]) {
        for (String arg : args) {
            File f = new File(arg);
            String path = f.getAbsolutePath();
            String extention = path.substring(path.lastIndexOf(".") + 1);
            String newXmlFileName = path.substring(0, path.lastIndexOf(".")).concat("_new").concat(".").concat(extention);
            String newTxtFileName = path.substring(0, path.lastIndexOf(".")).concat("_new");
            System.out.println(extention);
            if (f.exists() && extention.equalsIgnoreCase("xml")) {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = null;
                try {
                    parser = factory.newSAXParser();
                    SaxHandler handler = new SaxHandler();
                    parser.parse(f, handler);
                    for (Map.Entry<String, String> stringMap : handler.items.entrySet()) {
                        System.out.println(String.format("%s key, %s value", stringMap.getKey(), stringMap.getValue()));

                    }
                    XMLWriter mXmlWriter = new XMLWriter(newXmlFileName);
                    mXmlWriter.run(handler.items);
                    StringWriter mStringWriter = new StringWriter(newTxtFileName);
                    mStringWriter.printToFile(handler.items);
                }
                catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                catch (SAXException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else
                throw new IllegalArgumentException("bad xml file");
        }

    }
}
