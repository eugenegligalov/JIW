package lv.jake.jiw.services;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 13, 2010
 * Time: 8:27:15 PM
 */
public class Configuration {
    private String url;
    private String username;
    private String password;
    private String xmlRpcPath = "/rpc/xmlrpc";

    public Configuration() {
    }

    public String getUrl() {
        return url;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setPassword(String password) {
        this.password = password;
    }

    public String getXmlRpcPath() {
        return xmlRpcPath;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setXmlRpcPath(String xmlRpcPath) {
        this.xmlRpcPath = xmlRpcPath;
    }

    public URL constructFullUrl() throws MalformedURLException {
        return new URL(url + xmlRpcPath);
    }
}
