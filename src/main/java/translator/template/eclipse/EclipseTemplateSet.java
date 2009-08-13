package translator.template.eclipse;

import translator.template.Template;
import translator.template.TemplateSet;

//~--- JDK IMPORTS ------------------------------------------------------------

import java.util.LinkedList;
import java.util.List;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 12.08.2009
 * Time: 0:40:35
 * Note: It's kind of fun to do the impossible
 */
public class EclipseTemplateSet implements TemplateSet {
    private List<Template> templates = new LinkedList<Template>();
    private String         group;

    //~--- METHODS ------------------------------------------------------------

    public void addTemplate(Template template) {
        getTemplates().add(template);
    }

    //~--- GET METHODS --------------------------------------------------------

    public List<Template> getTemplates() {
        return templates;
    }

    public String getGroup() {
        return group;
    }

    //~--- SET METHODS --------------------------------------------------------

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
