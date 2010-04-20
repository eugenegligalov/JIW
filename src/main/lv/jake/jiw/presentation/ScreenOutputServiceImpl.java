package lv.jake.jiw.presentation;

import com.google.inject.Inject;
import lv.jake.jiw.application.Configuration;
import lv.jake.jiw.application.IssueValidationService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:03:49
 */
public class ScreenOutputServiceImpl extends AbstractOutputService {
    private static org.apache.log4j.Logger log = Logger.getLogger(ScreenOutputServiceImpl.class);

    @Inject
    public ScreenOutputServiceImpl(Configuration configuration, IssueValidationService issueValidationService) {
        super(configuration, issueValidationService);
    }

    public void generateReport() {
        final List<ReportSection> reportSections = preparePresentationData();
        for (ReportSection section : reportSections) {
            log.info("\n///////--------------- Filter " + section.getFilter().getName() + " ----------------///////");
            for (ReportSectionRow row : section.getRows()) {
                log.info("Key: " + row.getKey() +
                        " - Due date: " + row.getDueDate() + "- Priority: " + row.getPriority() +
                        " /-/ STATUS: " + row.getStatus());
                if (!row.getStatus().isOk()) {
                    log.info("||--Summary: " + row.getSummary());
                }
            }
        }
    }

}
