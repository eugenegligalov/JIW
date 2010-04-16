package lv.jake.jiw.services;

import com.google.inject.Inject;
import lv.jake.jiw.DueDateChecker;
import lv.jake.jiw.Utils;
import lv.jake.jiw.domain.JiraIssue;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import static lv.jake.jiw.Utils.getPriorityById;

/**
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:03:49
 */
public class ScreenOutputServiceImpl implements OutputService {
    private static org.apache.log4j.Logger log = Logger.getLogger(ScreenOutputServiceImpl.class);
    Object[] filters = null;
    Map issues = null;
    DueDateChecker dueDateChecker = null;

    @Inject
    public ScreenOutputServiceImpl(DueDateChecker dueDateChecker) {
        this.dueDateChecker = dueDateChecker;
    }

    public void setData(Object[] filters, Map issues) {
        this.filters = filters;
        this.issues = issues;
    }

    public void printOutput() {
        for (Object filter : filters) {
            Map currentFilter = (Map) filter;
            log.info("\n///////--------------- Filter " + currentFilter.get("name") + " ----------------///////");
            showIssueDetail(issues, (String) currentFilter.get("id"));
        }
    }

    public void showIssueDetail(Map issues, String currentIssues) {
        //noinspection unchecked
        for (JiraIssue currentIssue : (List<JiraIssue>) issues.get(currentIssues)) {
            log.info("Key: " + currentIssue.getKey() /*+ " - created: " +
                    issue.get("created") + " - updated: " + issue.get("updated")"*/ +
                    " - Due date: " + currentIssue.getDueDate() + "- Priority: " +
                    getPriorityById(currentIssue.getPriority()) +
                    " /-/ STATUS: " + dueDateChecker.getDueDateStatus(currentIssue.getCreatedDate(),
                    currentIssue.getDueDate(), currentIssue.getPriority(),
                    currentIssue.getLastUpdateDate()));
            if (!DueDateChecker.OK.equals(dueDateChecker.getDueDateStatus(currentIssue.getCreatedDate(),
                    currentIssue.getDueDate(), currentIssue.getPriority(),
                    currentIssue.getLastUpdateDate())))
                log.info("||--Summary: " + currentIssue.getSummary());
//            Object[] comments = null;
//                comments = lv.jake.jiw.getComments(rpcclient, loginToken, (String) issue.get("key"));
//                log.info("comments count: " + comments.length);
        }

    }

}
