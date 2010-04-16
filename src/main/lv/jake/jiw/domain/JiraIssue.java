package lv.jake.jiw.domain;

import java.util.Date;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 16, 2010
 * Time: 2:13:07 PM
 */
public class JiraIssue {
    private String key;
    private String summary;
    private String priority;
    private Date dueDate;
    private Date createdDate;
    private Date lastUpdateDate;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date date) {
        this.createdDate = date;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JiraIssue)) return false;

        JiraIssue jiraIssue = (JiraIssue) o;

        if (!dueDate.equals(jiraIssue.dueDate)) return false;
        if (!key.equals(jiraIssue.key)) return false;
        if (!lastUpdateDate.equals(jiraIssue.lastUpdateDate)) return false;
        if (!priority.equals(jiraIssue.priority)) return false;
        if (!summary.equals(jiraIssue.summary)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + summary.hashCode();
        result = 31 * result + priority.hashCode();
        result = 31 * result + dueDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        return result;
    }

}
