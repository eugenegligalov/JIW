package lv.jake.jiw.services;

import com.google.inject.Inject;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lv.jake.jiw.DueDateChecker;
import lv.jake.jiw.domain.JiraIssue;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static lv.jake.jiw.Utils.loadFreeMarkerTemplate;

/**
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:04:40
 */
public class HtmlOutputServiceImpl implements OutputService {
    private static final Logger log = Logger.getLogger(HtmlOutputServiceImpl.class);
    private static final SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:ss");

    protected Object[] filters = null;
    protected Map issues = null;
    protected DueDateChecker dueDateChecker = null;
    protected Template template = null;
    protected Configuration configuration = null;

    @Inject
    public HtmlOutputServiceImpl(DueDateChecker dueDateChecker, Configuration configuration) {
        this.dueDateChecker = dueDateChecker;
        this.configuration = configuration;
        this.template = loadFreeMarkerTemplate(
                configuration.getHtmlReportTemplatePath(), configuration.getHtmlReportTemplateFileName()
        );
    }

    public void setData(Object[] filters, Map issues) {
        this.filters = filters;
        this.issues = issues;
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
        //noinspection unchecked
        for (JiraIssue currentIssue : (List<JiraIssue>) issues.get(currentIssues)) {
            String url = configuration.getUrl();

            url = "<A HREF=\"" + url + "/browse/" + currentIssue.getKey() +
                    "\">" + currentIssue.getKey() + "</A>";


            String status = dueDateChecker.getDueDateStatus(currentIssue.getCreatedDate(),
                    currentIssue.getDueDate(), currentIssue.getPriority(),
                    currentIssue.getLastUpdateDate());

            String issueDetail = "<TD>" + url + "</TD>\n<TD>" +
                    formatDate(currentIssue.getCreatedDate()) + "</TD>\n<TD>" +
                    formatDate(currentIssue.getLastUpdateDate()) + "</TD>\n<TD>" +
                    formatNullableDate(currentIssue.getDueDate()) + "</TD>\n<TD>" +
                    getPriorityById(currentIssue.getPriority()) + "</TD>\n<TD>" +
                    status + "</TD>\n<TD>" +
                    currentIssue.getSummary() + "</TD>";
            issuesArray.add(issueDetail);
        }
        return issuesArray;
    }

    private String formatNullableDate(final Date date) {
        if (date == null) {
            return "N/A";
        } else {
            return formatDate(date);
        }
    }

    private String formatDate(final Date date) {
        return outputDateFormat.format(date);
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
