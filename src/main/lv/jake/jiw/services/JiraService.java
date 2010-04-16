package lv.jake.jiw.services;

import java.util.Map;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 15, 2010
 * Time: 6:51:37 PM
 */
public interface JiraService {

    void login();

    Object[] getFavouriteFilters();

    Object[] getIssuesFromFilter(String id);

    Map getIssuesFromFilters(Object[] filters) throws JiwServiceException;

    Object[] getComments(String issueId);

    void logout();

}
