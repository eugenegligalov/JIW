package lv.jake.jiw.application;

import com.google.inject.Inject;
import lv.jake.jiw.domain.JiraFilter;
import lv.jake.jiw.domain.JiraIssue;
import lv.jake.jiw.presentation.OutputService;
import org.apache.log4j.Logger;

import java.util.List;
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

    public void run() throws JiwServiceException {
        lazyInit();

        jiraService.login();

        List<JiraFilter> filters = jiraService.getFavouriteFilters();

        Map<String, List<JiraIssue>> issues = jiraService.getIssueMapFromFilters(filters);

        outputService.setData(filters, issues);
        outputService.generateReport();

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
