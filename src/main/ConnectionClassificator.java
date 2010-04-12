import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionClassificator {
    private static org.apache.log4j.Logger log = Logger.getLogger(ConnectionClassificator.class);
    public String stringURL = null;
    public String path = null;
    public String login = null;
    public String password = null;

    public String getURL() {
        return stringURL;
    }
    public void setURL(String URL) {
        this.stringURL = URL;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public URL getFullUrl() {
        try {
            return new URL(stringURL + path);
        } catch (MalformedURLException e) {
            log.error(e);
        }
        return null;
    }
}
