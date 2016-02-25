import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
    public static Map<String, String> items = new TreeMap<>();
    private String currentKey;
    private String currentVal;

    private void parseDocument(File file) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

             SAXParser sp = spf.newSAXParser();

             sp.parse(file, this);

        }
        catch (SAXException se) {
            se.printStackTrace();
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try{
        currentKey = attributes.getValue("name");}
        catch (Exception e){}
    }

    @Override public void endElement(String uri, String localName, String qName) throws SAXException {
         if(qName.equalsIgnoreCase("string")){
             items.put(currentKey,currentVal);
         }
    }

    @Override public void characters(char[] ch, int start, int length) throws SAXException {
        currentVal = new String(ch,start,length);
    }

    @Override public void endDocument() throws SAXException {
        for (Map.Entry<String, String> stringMap :  items.entrySet()) {
            System.out.println(String.format("%s key, %s value", stringMap.getKey(), stringMap.getValue()));

        }
     }
}
