package lv.jake.jiw.presentation;

import lv.jake.jiw.application.DueDateChecker;
import lv.jake.jiw.application.Configuration;
import lv.jake.jiw.application.IssueStatus;
import lv.jake.jiw.domain.JiraFilter;
import lv.jake.jiw.domain.JiraIssue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 17, 2010
 * Time: 1:32:46 AM
 */
public abstract class AbstractOutputService implements OutputService {
    private static final SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:ss");
    protected List<JiraFilter> filters = null;
    protected Map<String, List<JiraIssue>> issues = null;
    protected DueDateChecker dueDateChecker = null;
    protected Configuration configuration = null;

    public AbstractOutputService(Configuration configuration, DueDateChecker dueDateChecker) {
        this.configuration = configuration;
        this.dueDateChecker = dueDateChecker;
    }

    public void setData(List<JiraFilter> filters, Map<String, List<JiraIssue>> issues) {
        this.filters = filters;
        this.issues = issues;
    }

    protected List<ReportSection> preparePresentationData() {
        List<ReportSection> sectionList = new LinkedList<ReportSection>();
        for (JiraFilter filter : filters) {
            ReportSection section = new ReportSection(filter);
            final List<JiraIssue> issuesFromFilter = issues.get(filter.getId());
            for (JiraIssue jiraIssue : issuesFromFilter) {
                section.add(createReportSectionRowFromJiraIssue(jiraIssue));
            }
            sectionList.add(section);
        }
        return sectionList;
    }

    private ReportSectionRow createReportSectionRowFromJiraIssue(JiraIssue issue) {
        return new ReportSectionRow(
                issue.getKey(), issue.getSummary(), getIssueStatus(issue),
                getPriorityById(issue.getPriority()), formatDate(issue.getCreatedDate()),
                formatDate(issue.getLastUpdateDate()), formatNullableDate(issue.getDueDate()),
                constructJiraIssueUrl(issue)
        );
    }

    private String constructJiraIssueUrl(JiraIssue issue) {
        return configuration.getUrl() + "/browse/" + issue.getKey();
    }

    private IssueStatus getIssueStatus(JiraIssue issue) {
        return dueDateChecker.getDueDateStatus(issue.getCreatedDate(),
                issue.getDueDate(), issue.getPriority(),
                issue.getLastUpdateDate());
    }

    private String formatNullableDate(final Date date) {
        if (date == null) {
            return "N/A";
        } else {
            return formatDate(date);
        }
    }

    private String formatDate(final Date date) {
        return outputDateFormat.format(date);
    }

    public String getPriorityById(String id) {
        if (Integer.valueOf(id) == 1) {
            return "Blocker";
        }
        if (Integer.valueOf(id) == 2) {
            return "Critical";
        }
        if (Integer.valueOf(id) == 3) {
            return "Major";
        }
        if (Integer.valueOf(id) == 4) {
            return "Minor";
        }
        if (Integer.valueOf(id) == 5) {
            return "Trivial";
        }
        return "not found";
    }
}
