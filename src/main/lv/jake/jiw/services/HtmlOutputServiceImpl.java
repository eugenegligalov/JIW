package lv.jake.jiw.services;

import com.google.inject.Inject;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lv.jake.jiw.DueDateChecker;
import lv.jake.jiw.services.TimeServiceImpl;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:04:40
 */
public class HtmlOutputServiceImpl implements OutputService {
    private static org.apache.log4j.Logger log = Logger.getLogger(HtmlOutputServiceImpl.class);
    Object[] filters = null;
    Map issues = null;
    DueDateChecker dueDateChecker = null;
    Template template = null;

    @Inject
    public HtmlOutputServiceImpl(DueDateChecker dueDateChecker) {
        this.dueDateChecker = dueDateChecker;
    }

    public void setData(Object[] filters, Map issues) {
        this.filters = filters;
        this.issues = issues;
        readTemplate(".", "report_template.ftl");
    }

    private void readTemplate(String path, String filename) {
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

    private void writeReport(Map issues, String file) {
        Writer out = null;
        try {
            OutputStream os = new FileOutputStream(file);
            out = new OutputStreamWriter(os);
        } catch (FileNotFoundException e) {
            log.error(e);
        }
        try {
            template.process(issues, out);
        } catch (TemplateException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }
        try {
            out.flush();
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void printOutput() {
        List issuesArray = new ArrayList();
        for (Object filter : filters) {
            Map currentFilter = (Map) filter;
            issuesArray.add("<TD><B>" + currentFilter.get("name") + "</B></TD>");
            issuesArray.addAll(getIssueDetail(issues, (String) currentFilter.get("id")));
        }
        Map issuesMap = new HashMap();
        issuesMap.put("issues", issuesArray);
        writeReport(issuesMap, "report.html");
    }

    public List getIssueDetail(Map issues, String currentIssues) {
        List issuesArray = new ArrayList();
        for (Object currentIssue : (Object[]) issues.get(currentIssues)) {
            Map issue = (Map) currentIssue;
            String issueDetail = "<TD>" + issue.get("key") + "</TD>\n<TD>" +
                    issue.get("created") + "</TD>\n<TD>" + issue.get("updated") +
                    "</TD>\n<TD>" + issue.get("duedate") + "</TD>\n<TD>" +
                    getPriorityById(issue.get("priority").toString()) +
                    "</TD>\n<TD>" + dueDateChecker.getDueDateStatus(issue.get("created").toString(),
                    (String) issue.get("duedate"), issue.get("priority").toString(),
                    issue.get("updated").toString()) +
                    "</TD>\n<TD>" + issue.get("summary") + "</TD>";
            issuesArray.add(issueDetail);
        }
        return issuesArray;
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
