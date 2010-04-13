package lv.jake.jiw;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class JiraXmlRpcApi {
    
    private static org.apache.log4j.Logger log = Logger.getLogger(JiraXmlRpcApi.class);

    public XmlRpcClient getRpcClient(ConnectionClassificator connectionClassificator) {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(connectionClassificator.getFullUrl());
        XmlRpcClient rpcClient = new XmlRpcClient();
        rpcClient.setConfig(config);
        return rpcClient;
    }

    public Vector<String> login(XmlRpcClient rpcclient, ConnectionClassificator connectionClassificator) {
        Vector<String> loginParams = new Vector<String>(2);
        loginParams.add(connectionClassificator.getLogin());
        loginParams.add(connectionClassificator.getPassword());
        String loginToken = null;
        try {
            loginToken = (String) rpcclient.execute("jira1.login", loginParams);
        } catch (XmlRpcException e) {
            log.error(e);
        }

        Vector<String> loginTokenVector = new Vector<String>(1);
        loginTokenVector.add(loginToken);
        return loginTokenVector;
    }

    public Object[] getFavouriteFilters(XmlRpcClient rpcclient, Vector loginTokenVector) {
        Object object = null;
        try {
            object = rpcclient.execute("jira1.getFavouriteFilters", loginTokenVector);
        } catch (XmlRpcException e) {
            log.error(e);
        }
        return (Object[]) object;
    }

    public Object[] getIssuesFromFilter(XmlRpcClient rpcclient, Vector<String> loginTokenVector, String id) {
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

    public Map getIssuesFromFilters(XmlRpcClient rpcclient, Vector loginTokenVector, Object[] filters) {
        Map projects = new HashMap();
        for (Object filter : filters) {
            Map project = (Map) filter;
            Object[] currentFilter;
            //noinspection unchecked
            currentFilter = getIssuesFromFilter(rpcclient, loginTokenVector, (String) project.get("id"));
            projects.put(project.get("id"), currentFilter);
        }
        return projects;
    }

    public Object[] getComments(XmlRpcClient rpcclient, Vector loginTokenVector, String issueId) {
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

    public void logout(XmlRpcClient rpcclient, Vector loginTokenVector) {
        Boolean bool = null;
        try {
            bool = (Boolean) rpcclient.execute("jira1.logout", loginTokenVector);
        } catch (XmlRpcException e) {
            log.error(e);
        }
        log.info("Logout successful: " + bool);
    }

    public JiraXmlRpcApi() {
    }
}
