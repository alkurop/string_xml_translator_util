import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
    public static Map<String, String> items = new TreeMap<>();
    private String currentKey;
    private String currentVal;



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

     }
}
