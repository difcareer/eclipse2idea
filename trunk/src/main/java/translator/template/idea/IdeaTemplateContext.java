package translator.template.idea;

import java.util.LinkedList;
import java.util.List;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 7:04:37
 * Note: It's kind of fun to do the impossible
 */
public class IdeaTemplateContext {
    private List<Option> options = new LinkedList<Option>();

    //~--- CONSTRUCTORS -------------------------------------------------------

    private IdeaTemplateContext() {
        getOptions().add(new Option("XML", false));
        getOptions().add(new Option("OTHER", false));
        getOptions().add(new Option("JSP", false));
        getOptions().add(new Option("JAVA_STRING", false));
        getOptions().add(new Option("JAVA_SCRIPT", false));
        getOptions().add(new Option("JAVA_COMMENT", false));
        getOptions().add(new Option("JAVA_CODE", false));
        getOptions().add(new Option("HTML", false));
        getOptions().add(new Option("COMPLETION", false));
    }

    //~--- GET METHODS --------------------------------------------------------

    public static IdeaTemplateContext getDefaultContext() {
        return new IdeaTemplateContext();
    }

    public List<Option> getOptions() {
        return options;
    }

    //~--- SET METHODS --------------------------------------------------------

    public void setOption(String optionName, boolean optionValue) {
        for (Option option : getOptions()) {
            if (option.getName().equals(optionName)) {
                option.setValue(optionValue);

                return;
            }
        }
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    //~--- INNER CLASSES ------------------------------------------------------

    static class Option {
        private String  name;
        private boolean value;

        //~--- CONSTRUCTORS ---------------------------------------------------

        public Option() {}

        public Option(String name, boolean value) {
            this.name  = name;
            this.value = value;
        }

        //~--- GET METHODS ----------------------------------------------------

        public String getName() {
            return name;
        }

        public boolean isValue() {
            return value;
        }

        //~--- SET METHODS ----------------------------------------------------

        public void setName(String name) {
            this.name = name;
        }

        public void setValue(boolean value) {
            this.value = value;
        }
    }
}
