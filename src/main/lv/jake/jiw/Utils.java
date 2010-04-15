package lv.jake.jiw;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 16, 2010
 * Time: 1:42:38 AM
 */
public class Utils {
    private static Logger log = Logger.getLogger(Utils.class);
    
    public static Template loadFreeMarkerTemplate(String path, String filename) {
        Configuration configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(new File(path));
        } catch (IOException e1) {
            Utils.log.error(e1);
        }
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        Template template = null;
        try {
            template = configuration.getTemplate(filename);
        } catch (IOException e) {
            Utils.log.error(e);
        }
        return template;
    }
}
