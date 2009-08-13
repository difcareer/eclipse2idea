package translator.template.eclipse;

import translator.template.Template;

//~--- JDK IMPORTS ------------------------------------------------------------


//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 5:14:54
 * Note: It's kind of fun to do the impossible
 */
public class EclipseTemplate implements Template {
    private String autoinsert  = "";
    private String body        = "";
    private String context     = "";
    private String deleted     = "";
    private String description = "";
    private String enabled     = "";
    private String name        = "";

    //~--- GET METHODS --------------------------------------------------------

    public String getBody() {
        return body;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getContext() {
        return context;
    }

    public String getDescription() {
        return description;
    }

    public String getAutoinsert() {
        return autoinsert;
    }

    public String getDeleted() {
        return deleted;
    }

    public String getName() {
        return name;
    }

    //~--- SET METHODS --------------------------------------------------------

    public void setBody(String body) {
        this.body = body;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAutoinsert(String autoinsert) {
        this.autoinsert = autoinsert;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public void setName(String name) {
        this.name = name;
    }
}
