package lv.jake.jiw;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import lv.jake.jiw.services.*;

import java.io.FileNotFoundException;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 15, 2010
 * Time: 4:44:55 PM
 */
public class JiwModule extends AbstractModule {
    private final String configurationFileName;
    private Configuration configuration;

    public JiwModule(String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }

    @Override
    protected void configure() {
        bind(TimeService.class).to(TimeServiceImpl.class).in(Singleton.class);
        bind(OutputService.class).to(HtmlOutputServiceImpl.class).in(Singleton.class);
        bind(JiraService.class).to(JiraXmlRpcApi.class).in(Singleton.class);
        bind(IssueReportGenerator.class).to(IssueReportGeneratorImpl.class).in(Singleton.class);
    }

    @Provides
    Configuration provideConfiguration() throws FileNotFoundException {
        if (configuration == null) {
            configuration = YamlConfigurationLoader.load(configurationFileName);
        }
        return configuration;
    }

}
