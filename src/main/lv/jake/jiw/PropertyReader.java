package lv.jake.jiw;

import lv.jake.jiw.services.Configuration;
import lv.jake.jiw.services.ConfigurationService;
import org.apache.log4j.Logger;

public class PropertyReader {
    private static org.apache.log4j.Logger log = Logger.getLogger(PropertyReader.class);

    private final ConfigurationService configurationService;

    public PropertyReader(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public ConnectionClassificator read() {
        ConnectionClassificator connectionClassificator = new ConnectionClassificator();

        final Configuration configuration = configurationService.getConfiguration();

        connectionClassificator.setURL(configuration.getUrl());
        connectionClassificator.setPath(configuration.getXmlRpcPath());
        connectionClassificator.setLogin(configuration.getUsername());
        connectionClassificator.setPassword(configuration.getPassword());
        return connectionClassificator;
    }
}
