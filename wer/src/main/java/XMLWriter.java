import com.sun.org.apache.xml.internal.serialize.OutputFormat;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class XMLWriter {
    private Document dom;
    private String filePath;

    public XMLWriter(String filePath) {
        this.filePath = filePath;
    }

    public void run(Map<String, String> map){
        createDocument();
        createDOMTree(map);
        printToFile(filePath);

    }

    private void createDocument() {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            dom = db.newDocument();

        }
        catch (ParserConfigurationException pce) {
            //dump it
            System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
            System.exit(1);
        }

    }

    private void createDOMTree(Map<String, String> map) {
        Element rootEle = dom.createElement("resources");
        dom.appendChild(rootEle);

        for (Map.Entry<String, String> entrySet : map.entrySet()) {
            rootEle.appendChild(createStringElement(entrySet.getKey(), entrySet.getValue()));
        }
    }

    private Element createStringElement(String key, String value) {
        Element element = dom.createElement("string");
        element.setAttribute("name", key);
        Text nodeValue = dom.createTextNode(value);
        element.appendChild(nodeValue);
        return element;
    }

    private void printToFile(String fileName) {

        try {
            OutputFormat format = new OutputFormat(dom);
            format.setIndenting(true);
            XMLSerializer serializer = new XMLSerializer(
                      new FileOutputStream(new File(fileName)), format);
            serializer.serialize(dom);

        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
