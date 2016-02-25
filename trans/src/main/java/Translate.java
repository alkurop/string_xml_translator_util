import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Translate {
    public static void main(String args[]) {

        File xmlFile = new File(args[0]);


        String path = xmlFile.getAbsolutePath();
        String extention = path.substring(path.lastIndexOf(".") + 1);
        String newXmlFileName = path.substring(0, path.lastIndexOf(".")).concat("_new").concat(".").concat(extention);



        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            parser.parse(xmlFile, handler);

            XMLTranslator mXmlWriter = new XMLTranslator(args[1],newXmlFileName);
            mXmlWriter.run(handler.items);

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

}

