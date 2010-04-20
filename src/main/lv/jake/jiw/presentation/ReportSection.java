package lv.jake.jiw.presentation;

import lv.jake.jiw.domain.JiraFilter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 17, 2010
 * Time: 12:43:33 AM
 */
public class ReportSection {
    private JiraFilter filter;
    private List<ReportSectionRow> rows = new LinkedList<ReportSectionRow>();

    public ReportSection() {
    }

    public ReportSection(JiraFilter filter) {
        this.filter = filter;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public JiraFilter getFilter() {
        return filter;
    }

    public void setFilter(JiraFilter filter) {
        this.filter = filter;
    }

    public void add(ReportSectionRow row) {
        rows.add(row);
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public List<ReportSectionRow> getRows() {
        return Collections.unmodifiableList(rows);
    }
}
