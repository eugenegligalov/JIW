package lv.jake.jiw.services;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:05:12
 * To change this template use File | Settings | File Templates.
 */
public interface OutputService {
    public void setData(Object[] filters, Map issues);
    public void printOutput();
    public void showIssueDetail(Map issues, String currentIssues);
    public String getPriorityById(String id);
}
