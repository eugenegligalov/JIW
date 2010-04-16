package lv.jake.jiw.application;

import lv.jake.jiw.domain.JiraFilter;
import lv.jake.jiw.domain.JiraIssue;

import java.util.List;
import java.util.Map;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 15, 2010
 * Time: 6:51:37 PM
 */
public interface JiraService {

    void login();

    List<JiraFilter> getFavouriteFilters() throws JiwServiceException;

    Map<String, List<JiraIssue>> getIssueMapFromFilters(List<JiraFilter> filters) throws JiwServiceException;

    Object[] getComments(String issueId);

    void logout();

}
