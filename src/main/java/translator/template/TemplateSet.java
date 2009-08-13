package translator.template;

//~--- JDK IMPORTS ------------------------------------------------------------

import java.util.List;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 8:48:33
 * Note: It's kind of fun to do the impossible
 */
public interface TemplateSet {

    //~--- METHODS ------------------------------------------------------------

    public void addTemplate(Template template);

    //~--- GET METHODS --------------------------------------------------------

    public String getGroup();

    public List<Template> getTemplates();

    //~--- SET METHODS --------------------------------------------------------

}
