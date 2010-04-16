package lv.jake.jiw;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 16, 2010
 * Time: 1:42:38 AM
 */
public class Utils {
    public static final String JIRA_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat jiraDateFormat = createJiraDateFormat();

    private static Logger log = Logger.getLogger(Utils.class);


    public static Date jiraDateStringToDate(final String dateString) throws ParseException {
        return getJiraDateFormat().parse(dateString);
    }

    public static SimpleDateFormat getJiraDateFormat() {
        return jiraDateFormat;
    }

    public static SimpleDateFormat createJiraDateFormat() {
        return new SimpleDateFormat(JIRA_DATE_PATTERN);
    }

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

    public static String getPriorityById(String id) {
        if (Integer.valueOf(id) == 1) {
            return "Blocker";
        }
        if (Integer.valueOf(id) == 2) {
            return "Critical";
        }
        if (Integer.valueOf(id) == 3) {
            return "Major";
        }
        if (Integer.valueOf(id) == 4) {
            return "Minor";
        }
        if (Integer.valueOf(id) == 5) {
            return "Trivial";
        }
        return "not found";
    }
}
