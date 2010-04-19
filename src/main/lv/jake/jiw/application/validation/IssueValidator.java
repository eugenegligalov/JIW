package lv.jake.jiw.application.validation;

import lv.jake.jiw.application.IssueStatus;
import lv.jake.jiw.domain.JiraIssue;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 12:32:45 PM
 */
public interface IssueValidator {
    boolean accepts(JiraIssue issue);

    IssueStatus validate(JiraIssue issue);
}
