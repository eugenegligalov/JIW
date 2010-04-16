package lv.jake.jiw.presentation;

import lv.jake.jiw.domain.JiraFilter;
import lv.jake.jiw.domain.JiraIssue;

import java.util.List;
import java.util.Map;

/**
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:05:12
 */
public interface OutputService {

    public void setData(List<JiraFilter> filters, Map<String, List<JiraIssue>> issues);

    public void generateReport();

}
