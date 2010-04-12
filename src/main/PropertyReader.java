import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyReader {
    private static org.apache.log4j.Logger log = Logger.getLogger(PropertyReader.class);
    String path = null;

    public PropertyReader(String path) {
        this.path = path;
    }

    public ConnectionClassificator read() {
        ConnectionClassificator connectionClassificator = new ConnectionClassificator();
        InputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            log.error(e);
        }
        ResourceBundle rb = null;
        try {
            rb = new PropertyResourceBundle(is);
        } catch (IOException e) {
            log.error(e);
        }
        assert rb != null;
        connectionClassificator.setURL(rb.getString("url"));
        connectionClassificator.setPath(rb.getString("path"));
        connectionClassificator.setLogin(rb.getString("login"));
        connectionClassificator.setPassword(rb.getString("password"));
        return connectionClassificator;
    }
}
