package lv.jake.jiw.presentation;

import com.google.inject.Inject;
import lv.jake.jiw.DueDateChecker;
import lv.jake.jiw.application.Configuration;
import lv.jake.jiw.domain.JiraFilter;
import lv.jake.jiw.domain.JiraIssue;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:03:49
 */
public class ScreenOutputServiceImpl extends AbstractOutputService {
    private static org.apache.log4j.Logger log = Logger.getLogger(ScreenOutputServiceImpl.class);

    @Inject
    public ScreenOutputServiceImpl(Configuration configuration, DueDateChecker dueDateChecker) {
        super(configuration, dueDateChecker);
    }

    public void generateReport() {
        final List<ReportSection> reportSections = preparePresentationData();
        for (ReportSection section : reportSections) {
            log.info("\n///////--------------- Filter " + section.getFilter().getName() + " ----------------///////");
            for (ReportSectionRow row : section.getRows()) {
                log.info("Key: " + row.getKey() +
                        " - Due date: " + row.getDueDate() + "- Priority: " + row.getPriority() +
                        " /-/ STATUS: " + row.getStatus());
                if (!DueDateChecker.OK.equals(row.getStatus())) {
                    log.info("||--Summary: " + row.getSummary());
                }
            }
        }
    }

}
