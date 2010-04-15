package lv.jake.jiw;

import lv.jake.jiw.output.HtmlOutputServiceImpl;
import lv.jake.jiw.output.OutputService;
import lv.jake.jiw.output.ScreenOutputServiceImpl;
import lv.jake.jiw.yaml.YamlConfigurationService;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Vector;

public class KostikOMatic {
    private static org.apache.log4j.Logger log = Logger.getLogger(KostikOMatic.class);

    private final JiraXmlRpcApi jira = new JiraXmlRpcApi();
    private boolean initialized = false;
    private ConnectionClassificator connectionClassificator = null;
    private XmlRpcClient rpcclient = null;
    private OutputService outputService = null;

    private final String propertyFileName;

    public static void main(String[] args) {
        final KostikOMatic matic = new KostikOMatic(args[0]);
        matic.run();
    }

    public KostikOMatic(final String propertyFileName) {
        this.propertyFileName = propertyFileName;
        this.outputService = new HtmlOutputServiceImpl();
    }

    public void run() {
        lazyInit();

        Vector loginToken;
        loginToken = jira.login(rpcclient, connectionClassificator);

        Object[] filters;
        filters = jira.getFavouriteFilters(rpcclient, loginToken);

        Map issues;
        issues = jira.getIssuesFromFilters(rpcclient, loginToken, filters);

        outputService.setData(filters, issues);
        outputService.printOutput();

        jira.logout(rpcclient, loginToken);
    }

    public void init() {
        final YamlConfigurationService yamlConfigurationService = new YamlConfigurationService();
        try {
            yamlConfigurationService.init(propertyFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to load " + propertyFileName + " configuration file", e);
        }

        connectionClassificator = new PropertyReader(yamlConfigurationService).read();
        rpcclient = jira.getRpcClient(connectionClassificator);
        initialized = true;
    }

    private void lazyInit() {
        if (!initialized) {
            init();
        }
    }
}
