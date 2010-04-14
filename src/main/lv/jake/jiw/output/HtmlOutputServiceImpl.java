package lv.jake.jiw.output;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lv.jake.jiw.DueDateChecker;
import lv.jake.jiw.TimeServiceImpl;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
    Object[] filters = null;
    Map issues = null;
    DueDateChecker dueDateChecker = null;
    Template template = null;

    public void setData(Object[] filters, Map issues) {
        this.filters = filters;
        this.issues = issues;
        dueDateChecker = new DueDateChecker(new TimeServiceImpl());
        readTemplate(".","report_template.ftl");
    }
    private void readTemplate(String path, String filename){
        Configuration configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(new File(path));
        } catch (IOException e) {
            log.error(e);
        }
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        try {
            this.template = configuration.getTemplate(filename);
        } catch (IOException e) {
            log.error(e);
        }
    }
    
    private void writeReport(){
        Writer out = new OutputStreamWriter(System.out);
//        template.process(root, out);
        try {
            out.flush();
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void printOutput() {
        for (Object filter : filters) {
            Map currentFilter = (Map) filter;
            showIssueDetail(issues, (String) currentFilter.get("id"));
        }
    }

    public void showIssueDetail(Map issues, String currentIssues) {
        for (Object currentIssue : (Object[]) issues.get(currentIssues)) {
            Map issue = (Map) currentIssue;
        }
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
