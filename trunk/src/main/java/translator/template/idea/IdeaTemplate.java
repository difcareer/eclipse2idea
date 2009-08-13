package translator.template.idea;

import translator.template.Template;

//~--- JDK IMPORTS ------------------------------------------------------------

import java.util.LinkedList;
import java.util.List;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 7:03:16
 * Note: It's kind of fun to do the impossible
 */
public class IdeaTemplate implements Template {
    private List<IdeaTemplateVariable> variables        =
        new LinkedList<IdeaTemplateVariable>();
    private String                     description      = "";
    private String                     name             = "";
    private String                     value            = "";
    private boolean                    toReformat       = true;
    private boolean                    toShortenFQNames = true;
    private IdeaTemplateContext        context          =
        IdeaTemplateContext.getDefaultContext();

    //~--- GET METHODS --------------------------------------------------------

    public IdeaTemplateContext getContext() {
        return context;
    }

    public List<IdeaTemplateVariable> getVariables() {
        return variables;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public IdeaTemplateVariable getVariableWithName(String name) {
        List<IdeaTemplateVariable> variables = getVariables();

        for (IdeaTemplateVariable variable : variables) {
            if (variable.getName().equals(name)) {
                return variable;
            }
        }

        return null;
    }

    public boolean hasVariableWithName(String name) {
        return getVariableWithName(name) != null;
    }

    public boolean isToReformat() {
        return toReformat;
    }

    public boolean isToShortenFQNames() {
        return toShortenFQNames;
    }

    //~--- SET METHODS --------------------------------------------------------

    public void setContext(IdeaTemplateContext context) {
        this.context = context;
    }

    public void setVariables(List<IdeaTemplateVariable> variables) {
        this.variables = variables;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setToReformat(boolean toReformat) {
        this.toReformat = toReformat;
    }

    public void setToShortenFQNames(boolean toShortenFQNames) {
        this.toShortenFQNames = toShortenFQNames;
    }

    public void setName(String name) {
        this.name = name;
    }
}
