package lv.jake.jira;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;

import java.util.Map;
import java.util.Vector;

public class KostikOMatic {
    private static org.apache.log4j.Logger log = Logger.getLogger(KostikOMatic.class);

    public static void main(String[] args) {
        ConnectionClassificator connectionClassificator = new PropertyReader(args[0]).read();
        JiraXmlRpcApi jira = new JiraXmlRpcApi();
        XmlRpcClient rpcclient = jira.getRpcClient(connectionClassificator);
        Vector loginToken;
        loginToken = jira.login(rpcclient, connectionClassificator);

        Object[] filters;
        filters = jira.getFavouriteFilters(rpcclient, loginToken);

        Map issues;
        issues = jira.getIssuesFromFilters(rpcclient, loginToken, filters);

        new DueDateChecker().showIssuesDetail(filters, issues);

        jira.logout(rpcclient, loginToken);
    }
}
