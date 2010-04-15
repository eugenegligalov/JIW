package lv.jake.jiw.services;

import com.google.inject.Inject;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class JiraXmlRpcApi implements JiraService {

    private static org.apache.log4j.Logger log = Logger.getLogger(JiraXmlRpcApi.class);
    private XmlRpcClient rpcclient;
    private String login;
    private String password;
    private Vector<String> loginTokenVector;

    @Inject
    public JiraXmlRpcApi(Configuration configuration) throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(configuration.constructFullUrl());
        XmlRpcClient rpcClient = new XmlRpcClient();
        rpcClient.setConfig(config);

        this.login = configuration.getUsername();
        this.password = configuration.getPassword();
        this.rpcclient = rpcClient;
    }

    public void login() {
        Vector<String> loginParams = new Vector<String>(2);
        loginParams.add(login);
        loginParams.add(password);
        String loginToken = null;
        try {
            loginToken = (String) rpcclient.execute("jira1.login", loginParams);
        } catch (XmlRpcException e) {
            log.error(e);
        }

        Vector<String> loginTokenVector = new Vector<String>(1);
        loginTokenVector.add(loginToken);

        this.loginTokenVector = loginTokenVector;
    }

    public Object[] getFavouriteFilters() {
        Object object = null;
        try {
            object = rpcclient.execute("jira1.getFavouriteFilters", loginTokenVector);
        } catch (XmlRpcException e) {
            log.error(e);
        }
        return (Object[]) object;
    }

    public Object[] getIssuesFromFilter(String id) {
        Object object = null;
        Vector<String> currentProperties = new Vector<String>(loginTokenVector);
        currentProperties.add(id);
        try {
            object = rpcclient.execute("jira1.getIssuesFromFilter", currentProperties);
        } catch (XmlRpcException e) {
            log.error(e);
        }
        return (Object[]) object;
    }

    public Map getIssuesFromFilters(Object[] filters) {
        Map projects = new HashMap();
        for (Object filter : filters) {
            Map project = (Map) filter;
            Object[] currentFilter;
            //noinspection unchecked
            currentFilter = getIssuesFromFilter((String) project.get("id"));
            projects.put(project.get("id"), currentFilter);
        }
        return projects;
    }

    public Object[] getComments(String issueId) {
        Object object = null;
        Vector<String> currentProperties = new Vector<String>(loginTokenVector);
        currentProperties.add(issueId);
        try {
            object = rpcclient.execute("jira1.getComments", currentProperties);
        } catch (XmlRpcException e) {
            log.error(e);
        }
        return (Object[]) object;
    }

    public void logout() {
        Boolean bool = null;
        try {
            bool = (Boolean) rpcclient.execute("jira1.logout", loginTokenVector);
        } catch (XmlRpcException e) {
            log.error(e);
        }
        log.info("Logout successful: " + bool);
    }

}
