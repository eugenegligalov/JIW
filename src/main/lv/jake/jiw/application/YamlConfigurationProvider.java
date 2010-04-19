package lv.jake.jiw.application;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 13, 2010
 * Time: 8:25:50 PM
 */
public class YamlConfigurationProvider implements Provider<Configuration> {
    private final String configurationFileName;

    @Inject
    public YamlConfigurationProvider(@Named("configurationFileName") String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }

    public static Configuration load(String file) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Loader(new Constructor(Configuration.class)));
        return (Configuration) yaml.load(new FileReader(file));
    }

    public Configuration get() {
        try {
            return load(configurationFileName);
        } catch (FileNotFoundException e) {
            throw new JiwRuntimeException("Configuration file " + configurationFileName + " is not found.", e);
        }
    }
}
