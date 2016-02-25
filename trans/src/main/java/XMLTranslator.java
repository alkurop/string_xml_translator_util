import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class XMLTranslator {
    private String[] translation;
    private Document dom;
    private String filePath;

    static String readFile(String path, Charset encoding)
              throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public XMLTranslator(String translation, String filePath) {
        this.filePath = filePath;
        try {
            this.translation = readFile(translation, Charset.defaultCharset()).split("\\^");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(Map<String, String> map) {
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
        int count = 0;
        for (Map.Entry<String, String> entrySet : map.entrySet()) {
            try {
                rootEle.appendChild(createStringElement(entrySet.getKey(), translation[count]));
                count++;
            }
            catch (Exception w) {}
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
        System.out.println(fileName);
        try {
            File f = new File(fileName);

            f.createNewFile();
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
