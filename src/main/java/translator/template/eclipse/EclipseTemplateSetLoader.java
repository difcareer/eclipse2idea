package translator.template.eclipse;

import translator.template.TemplateSetLoader;

import org.apache.commons.digester.*;

import org.xml.sax.SAXException;

//~--- JDK IMPORTS ------------------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 7:33:17
 * Note: It's kind of fun to do the impossible
 */
public class EclipseTemplateSetLoader implements TemplateSetLoader {
    private final static EclipseTemplateSetLoader DEFAULT_XML_LOADER =
        new EclipseTemplateSetLoader();

    //~--- CONSTRUCTORS -------------------------------------------------------

    private EclipseTemplateSetLoader() {}

    //~--- METHODS ------------------------------------------------------------

    /**
     * Loades eclipse code templates from given file
     * @param path path to xml file with templates
     * @return list of created templates
     * @throws IOException
     * @throws SAXException
     */
    public EclipseTemplateSet load(String path) throws IOException, SAXException {
        File file = new File(path);

        if (!file.exists()) {
            throw new FileNotFoundException("File " + path + "not exist!");
        }

        EclipseTemplateSet templateSet = new EclipseTemplateSet();
        Digester    digester    = new Digester();

        // TODO make validation
        digester.setValidating(false);
        digester.push(templateSet);
        digester.addRule("templates/template",
                         new ObjectCreateRule(EclipseTemplate.class));
        digester.addRule("templates/template",
                         new SetPropertyRule("autoinsert", "autoinsert"));
        digester.addRule("templates/template", new SetPropertyRule("context", "context"));
        digester.addRule("templates/template", new SetPropertyRule("deleted", "deleted"));
        digester.addRule("templates/template",
                         new SetPropertyRule("description", "description"));
        digester.addRule("templates/template", new SetPropertyRule("enabled", "enabled"));
        digester.addRule("templates/template", new SetPropertyRule("name", "name"));
        digester.addRule("templates/template", new BeanPropertySetterRule("body"));
        digester.addRule("templates/template", new SetNextRule("addTemplate"));
        digester.parse(file);

        return templateSet;
    }

    //~--- GET METHODS --------------------------------------------------------

    public static EclipseTemplateSetLoader getDefaultXmlLoader() {
        return DEFAULT_XML_LOADER;
    }
}
