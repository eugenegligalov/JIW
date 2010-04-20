package lv.jake.jiw.application;

import com.google.inject.Inject;
import lv.jake.jiw.domain.JiraFilter;
import lv.jake.jiw.domain.JiraIssue;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.*;

import static lv.jake.jiw.Utils.jiraDateStringToDate;

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

    public List<JiraFilter> getFavouriteFilters() throws JiwServiceException {
        Object object;
        try {
            object = rpcclient.execute("jira1.getFavouriteFilters", loginTokenVector);
        } catch (XmlRpcException e) {
            throw new JiwServiceException("Error loading favorite filter list from JIRA", e);
        }
        @SuppressWarnings({"unchecked"})

        final Object[] filterObjects = (Object[]) object;

        List<JiraFilter> filters = new LinkedList<JiraFilter>();
        for (Object o : filterObjects) {
            filters.add(convertMapToJiraFilter((Map<String, String>) o));
        }
        return filters;
    }

    public Map<String, List<JiraIssue>> getIssueMapFromFilters(final List<JiraFilter> filters)
            throws JiwServiceException {
        final Map<String, List<JiraIssue>> filterIssueMap = new HashMap<String, List<JiraIssue>>();
        for (JiraFilter filter : filters) {
            final String filterId = filter.getId();
            filterIssueMap.put(filter.getId(), loadIssuesByFilter(filterId));
        }
        return filterIssueMap;
    }

    private List<JiraIssue> loadIssuesByFilter(String filterId) throws JiwServiceException {
        final ArrayList<JiraIssue> list = new ArrayList<JiraIssue>();
        for (Object object : getIssuesFromFilter(filterId)) {
            @SuppressWarnings({"unchecked"})
            final JiraIssue issue = convertMapToJiraIssue((Map<String, String>) object);
            list.add(issue);
        }
        return list;
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
        log.debug("Logout successful: " + bool);
    }


    public JiraFilter convertMapToJiraFilter(Map<String, String> map) throws JiwServiceException {
        JiraFilter filter = new JiraFilter();
        filter.setId(map.get("id"));
        filter.setName(map.get("name"));
        return filter;
    }

    public JiraIssue convertMapToJiraIssue(Map<String, String> map) throws JiwServiceException {
        JiraIssue issue = new JiraIssue();
        issue.setKey(map.get("key"));
        issue.setSummary(map.get("summary"));
        issue.setPriority(map.get("priority"));
        try {
            final String dueDateString = map.get("duedate");
            if (dueDateString != null) {
                issue.setDueDate(jiraDateStringToDate(dueDateString));
            }
            issue.setCreatedDate(jiraDateStringToDate(map.get("created")));
            issue.setLastUpdateDate(jiraDateStringToDate(map.get("updated")));
        } catch (ParseException e) {
            throw new JiwServiceException("Cannot parse JIRA date for issue: " + issue.getKey(), e);
        }
        return issue;
    }

}
