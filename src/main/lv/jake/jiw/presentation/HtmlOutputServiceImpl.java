package lv.jake.jiw.presentation;

import com.google.inject.Inject;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lv.jake.jiw.application.Configuration;
import lv.jake.jiw.application.IssueValidationService;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lv.jake.jiw.Utils.loadFreeMarkerTemplate;

/**
 * User: Jaker
 * Date: 2010.14.4
 * Time: 22:04:40
 */
public class HtmlOutputServiceImpl extends AbstractOutputService {
    private static final Logger log = Logger.getLogger(HtmlOutputServiceImpl.class);

    protected Template template = null;

    @Inject
    public HtmlOutputServiceImpl(Configuration configuration, IssueValidationService issueValidationService) {
        super(configuration, issueValidationService);
        this.template = loadFreeMarkerTemplate(
                configuration.getHtmlReportTemplatePath(), configuration.getHtmlReportTemplateFileName()
        );
    }

    public void generateReport() {
        List<ReportSection> sectionList = preparePresentationData();
        Map<String, Object> bindings = new HashMap<String, Object>();
        bindings.put("sections", sectionList);
        writeReport("report.html", bindings);
    }

    private void writeReport(String file, Map issues) {
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

    //FIXME: This method does absolutely nothing

}
