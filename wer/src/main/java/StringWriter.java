import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StringWriter {
    private String fileName;

    public StringWriter(String fileName) {
        this.fileName = fileName;
    }

    public void printToFile(Map<String, String> map) throws IOException {
        StringBuffer buffer = new StringBuffer();
        int count = 1;
        for (Map.Entry<String, String> entrySet : map.entrySet()){
            buffer. append("   ").append(entrySet.getValue()).append("\n^\n");

        }
        BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
        out.write(buffer.toString());
        out.flush();
        out.close();
    }




}
