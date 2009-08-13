package translator.template.idea;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 7:04:31
 * Note: It's kind of fun to do the impossible
 */
public class IdeaTemplateVariable {
    private String  defaultValue = "";
    private String  expression   = "";
    private String  name         = "";
    private boolean alwaysStopAt = true;

    //~--- GET METHODS --------------------------------------------------------

    public String getName() {
        return name;
    }

    public String getExpression() {
        return expression;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isAlwaysStopAt() {
        return alwaysStopAt;
    }

    //~--- SET METHODS --------------------------------------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setAlwaysStopAt(boolean alwaysStopAt) {
        this.alwaysStopAt = alwaysStopAt;
    }
}
