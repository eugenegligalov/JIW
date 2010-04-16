package lv.jake.jiw.services;

import java.util.Map;

/**
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:05:12
 */
public interface OutputService {
    public void setData(Object[] filters, Map issues);

    public void printOutput();

    public void showIssueDetail(Map issues, String currentIssues);

}
