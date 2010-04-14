package lv.jake.jiw.output;

import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:04:40
 * To change this template use File | Settings | File Templates.
 */
public class HtmlOutputServiceImpl implements OutputService {
    private static org.apache.log4j.Logger log = Logger.getLogger(HtmlOutputServiceImpl.class);

    public void setData(Object[] filters, Map issues) {

    }
    public void printOutput() {

    }
    public void showIssueDetail(Map issues, String currentIssues) {

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
