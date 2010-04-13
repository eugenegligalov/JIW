package lv.jake.jira;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;

import java.util.Map;
import java.util.Vector;

public class KostikOMatic {
    private static org.apache.log4j.Logger log = Logger.getLogger(KostikOMatic.class);

    private final JiraXmlRpcApi jira = new JiraXmlRpcApi();
    private boolean initialized = false;
    private ConnectionClassificator connectionClassificator = null;
    private XmlRpcClient rpcclient = null;

    private final String propertyFileName;

    public static void main(String[] args) {
        final KostikOMatic matic = new KostikOMatic(args[0]);
        matic.run();
    }

    public KostikOMatic(final String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    public void run() {
        lazyInit();

        Vector loginToken;
        loginToken = jira.login(rpcclient, connectionClassificator);

        Object[] filters;
        filters = jira.getFavouriteFilters(rpcclient, loginToken);

        Map issues;
        issues = jira.getIssuesFromFilters(rpcclient, loginToken, filters);

        new DueDateChecker(new TimeServiceImpl()).showIssuesDetail(filters, issues);

        jira.logout(rpcclient, loginToken);
    }

    public void init() {
        connectionClassificator = new PropertyReader(propertyFileName).read();
        rpcclient = jira.getRpcClient(connectionClassificator);
        initialized = true;
    }

    private void lazyInit() {
        if (!initialized) {
            init();
        }
    }
}
