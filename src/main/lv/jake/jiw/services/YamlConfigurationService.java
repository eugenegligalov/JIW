package lv.jake.jiw.services;

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
public class YamlConfigurationService implements ConfigurationService {
    protected Configuration configuration;

    public void init(String file) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Loader(new Constructor(Configuration.class)));
        configuration = (Configuration) yaml.load(new FileReader(file));
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
