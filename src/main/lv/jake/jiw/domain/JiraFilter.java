package lv.jake.jiw.domain;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 17, 2010
 * Time: 12:15:35 AM
 */
public class JiraFilter {
    private String id;
    private String name;

    public JiraFilter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
