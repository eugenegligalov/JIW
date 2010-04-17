package lv.jake.jiw.presentation;

import lv.jake.jiw.application.IssueStatus;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 17, 2010
 * Time: 1:00:55 AM
 */
public class ReportSectionRow {
    private final String key;
    private final IssueStatus status;
    private final String summary;
    private final String priority;
    private final String createdDate;
    private final String lastUpdateDate;
    private final String dueDate;
    private final String jiraIssueUrl;

    public ReportSectionRow(String key, String summary, IssueStatus status, String priority,
                            String createdDate, String lastUpdateDate, String dueDate, String jiraIssueUrl) {
        this.key = key;
        this.summary = summary;
        this.status = status;
        this.priority = priority;
        this.createdDate = createdDate;
        this.lastUpdateDate = lastUpdateDate;
        this.dueDate = dueDate;
        this.jiraIssueUrl = jiraIssueUrl;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getKey() {
        return key;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getPriority() {
        return priority;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getSummary() {
        return summary;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public IssueStatus getStatus() {
        return status;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getCreatedDate() {
        return createdDate;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getDueDate() {
        return dueDate;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public String getJiraIssueUrl() {
        return jiraIssueUrl;
    }
}
