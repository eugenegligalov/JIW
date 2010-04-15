package lv.jake.jiw.services;

import com.google.inject.Inject;
import org.apache.log4j.Logger;

import java.util.Map;

public class IssueReportGeneratorImpl implements IssueReportGenerator {
    private static org.apache.log4j.Logger log = Logger.getLogger(IssueReportGeneratorImpl.class);

    private final JiraService jiraService;
    private boolean initialized = false;
    private OutputService outputService = null;

    @Inject
    public IssueReportGeneratorImpl(final OutputService outputService, final JiraService jiraService) {
        this.outputService = outputService;
        this.jiraService = jiraService;
    }

    public void run() {
        lazyInit();

        jiraService.login();

        Object[] filters;
        filters = jiraService.getFavouriteFilters();

        Map issues;
        issues = jiraService.getIssuesFromFilters(filters);

        outputService.setData(filters, issues);
        outputService.printOutput();

        jiraService.logout();
    }

    public void init() {
        initialized = true;
    }

    private void lazyInit() {
        if (!initialized) {
            init();
        }
    }
}
