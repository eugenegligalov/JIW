package lv.jake.jiw.presentation;

import com.google.inject.Inject;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lv.jake.jiw.application.Configuration;
import lv.jake.jiw.application.IssueValidationService;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerList;
import org.mortbay.jetty.handler.ResourceHandler;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lv.jake.jiw.Utils.loadFreeMarkerTemplate;

/**
 * User: Eugene Gligalov
 * Date: 2010.14.4
 * Time: 22:04:40
 */
public class HtmlOutputServiceImpl extends AbstractOutputService {
    private static final Logger log = Logger.getLogger(HtmlOutputServiceImpl.class);

    protected Configuration configuration = null;

    @Inject
    public HtmlOutputServiceImpl(Configuration configuration, IssueValidationService issueValidationService) {
        super(configuration, issueValidationService);
        this.configuration = configuration;
        if (configuration.getUseHttpServer().equalsIgnoreCase("yes"))
            startServer(configuration.getLocalServerPort(), configuration.getOutputHtmlReportTemplatePath(),
                configuration.getOutputHtmlReportFileName());
    }

    private void startServer(int port, String outputReportPath, String outputReportName) {
        Server server = new Server(port);
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setWelcomeFiles(new String[]{outputReportName});
        resource_handler.setResourceBase(outputReportPath);
        log.debug("serving " + resource_handler.getBaseResource());
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, new DefaultHandler()});
        server.setHandler(handlers);
        try {
            server.start();
        } catch (Exception e) {
            log.error(e);
        }

    }

    public void generateReport() {
        List<ReportSection> sectionList = preparePresentationData();
        Map<String, Object> bindings = new HashMap<String, Object>();
        bindings.put("sections", sectionList);
        writeReport(configuration.getOutputHtmlReportTemplatePath() + "/" + 
                configuration.getOutputHtmlReportFileName(), bindings);
    }

    private void writeReport(String file, Map issues) {
        Writer out = null;
        Template template = null;
        template = loadFreeMarkerTemplate(
                configuration.getHtmlReportTemplatePath(), configuration.getHtmlReportTemplateFileName()
        );
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
