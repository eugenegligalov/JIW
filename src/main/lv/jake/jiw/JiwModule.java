package lv.jake.jiw;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import lv.jake.jiw.application.*;
import lv.jake.jiw.application.validation.IssueValidatorRepository;
import lv.jake.jiw.application.validation.IssueValidatorRepositoryImpl;
import lv.jake.jiw.presentation.HtmlOutputServiceImpl;
import lv.jake.jiw.presentation.OutputService;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 15, 2010
 * Time: 4:44:55 PM
 */
public class JiwModule extends AbstractModule {
    private final String configurationFileName;

    public JiwModule(String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("configurationFileName")).toInstance(configurationFileName);
        bind(Configuration.class).toProvider(YamlConfigurationProvider.class).in(Singleton.class);
        bind(TimeService.class).to(TimeServiceImpl.class).in(Singleton.class);
        bind(IssueValidatorRepository.class).to(IssueValidatorRepositoryImpl.class).in(Singleton.class);
        bind(OutputService.class).to(HtmlOutputServiceImpl.class).in(Singleton.class);
        bind(JiraService.class).to(JiraXmlRpcApi.class).in(Singleton.class);
        bind(IssueReportGenerator.class).to(IssueReportGeneratorImpl.class).in(Singleton.class);
    }

}
