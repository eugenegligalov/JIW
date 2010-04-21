package lv.jake.jiw.application;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 13, 2010
 * Time: 8:27:15 PM
 */
public class Configuration {
    public static final int DEFAULT_REPORT_GENERATION_DELAY = 60 * 1000; // 1 minute

    private String url;
    private String username;
    private String password;
    private String xmlRpcPath = "/rpc/xmlrpc";
    private String htmlReportTemplatePath = ".";
    private String htmlReportTemplateFileName = "report_template.ftl";
    private String outputHtmlReportTemplatePath = ".";
    private String outputHtmlReportFileName = "report.htm";
    private String useHttpServer = "no";

    private int localServerPort = 80;
    private long reportGenerationPeriod = DEFAULT_REPORT_GENERATION_DELAY;

    public Configuration() {
    }

    public String getUseHttpServer() {
        return useHttpServer;
    }

    public void setUseHttpServer(String useHttpServer) {
        this.useHttpServer = useHttpServer;
    }

    public String getOutputHtmlReportTemplatePath() {
        return outputHtmlReportTemplatePath;
    }

    public void setOutputHtmlReportTemplatePath(String outputHtmlReportTemplatePath) {
        this.outputHtmlReportTemplatePath = outputHtmlReportTemplatePath;
    }

    public String getOutputHtmlReportFileName() {
        return outputHtmlReportFileName;
    }

    public void setOutputHtmlReportFileName(String outputHtmlReportFileName) {
        this.outputHtmlReportFileName = outputHtmlReportFileName;
    }

    public int getLocalServerPort() {
        return localServerPort;
    }

    public void setLocalServerPort(int localServerPort) {
        this.localServerPort = localServerPort;
    }

    public String getUrl() {
        return url;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setPassword(String password) {
        this.password = password;
    }

    public String getXmlRpcPath() {
        return xmlRpcPath;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setXmlRpcPath(String xmlRpcPath) {
        this.xmlRpcPath = xmlRpcPath;
    }

    public URL constructFullUrl() throws MalformedURLException {
        return new URL(url + xmlRpcPath);
    }

    public String getHtmlReportTemplatePath() {
        return htmlReportTemplatePath;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setHtmlReportTemplatePath(String htmlReportTemplatePath) {
        this.htmlReportTemplatePath = htmlReportTemplatePath;
    }

    public String getHtmlReportTemplateFileName() {
        return htmlReportTemplateFileName;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setHtmlReportTemplateFileName(String htmlReportTemplateFileName) {
        this.htmlReportTemplateFileName = htmlReportTemplateFileName;
    }

    public long getReportGenerationPeriod() {
        return reportGenerationPeriod;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setReportGenerationPeriod(long reportGenerationPeriod) {
        this.reportGenerationPeriod = reportGenerationPeriod;
    }
}
