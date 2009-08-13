package translator.template.idea;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

import translator.template.TemplateSet;
import translator.template.TemplateSetSaver;

//~--- JDK IMPORTS ------------------------------------------------------------

import java.io.*;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 7:54:49
 * Note: It's kind of fun to do the impossible
 */
public class IdeaTemplateSetSaver extends TemplateSetSaver {
    private static final IdeaTemplateSetSaver DEFAULT_XML_SAVER =
        new IdeaTemplateSetSaver();

    //~--- CONSTRUCTORS -------------------------------------------------------

    private IdeaTemplateSetSaver() {}

    //~--- METHODS ------------------------------------------------------------

    public void save(TemplateSet templateSet, String path) {
        OutputStream out = null;

        try {
            out = new FileOutputStream(path);
        } catch (FileNotFoundException e) {

            // TODO fix logging
            e.printStackTrace();
        }

        save(templateSet, out);
    }

    public void save(TemplateSet templateSet, OutputStream out) {
        if (!(templateSet instanceof IdeaTemplateSet)) {
            // TODO throw exception
            return;
        }

        XStream xstream = new XStream();

        new XppDriver();

        // Template set configuration
        xstream.alias("templateSet", IdeaTemplateSet.class);
        xstream.useAttributeFor(IdeaTemplateSet.class, "group");
        xstream.addImplicitCollection(IdeaTemplateSet.class, "templates");

        // Template configuration
        xstream.alias("template", IdeaTemplate.class);
        xstream.useAttributeFor(IdeaTemplate.class, "name");
        xstream.useAttributeFor(IdeaTemplate.class, "description");
        xstream.useAttributeFor(IdeaTemplate.class, "toReformat");
        xstream.useAttributeFor(IdeaTemplate.class, "toShortenFQNames");
        xstream.useAttributeFor(IdeaTemplate.class, "value");
        xstream.addImplicitCollection(IdeaTemplate.class, "variables");

        // Variable configuration
        xstream.alias("variable", IdeaTemplateVariable.class);
        xstream.useAttributeFor(IdeaTemplateVariable.class, "name");
        xstream.useAttributeFor(IdeaTemplateVariable.class, "expression");
        xstream.useAttributeFor(IdeaTemplateVariable.class, "defaultValue");
        xstream.useAttributeFor(IdeaTemplateVariable.class, "alwaysStopAt");

        // Context configuration
        xstream.alias("context", IdeaTemplateContext.class);
        xstream.addImplicitCollection(IdeaTemplateContext.class, "options");

        // Option configuration
        xstream.alias("option", IdeaTemplateContext.Option.class);
        xstream.useAttributeFor(IdeaTemplateContext.Option.class, "name");
        xstream.useAttributeFor(IdeaTemplateContext.Option.class, "value");

        String str = xstream.toXML(templateSet);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, "UTF-8");
            Writer             writer             =
                new BufferedWriter(outputStreamWriter);

            writer.write(XML_HEADER);
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();    // To change body of catch statement use File | Settings | File Templates.
        }
    }

    //~--- GET METHODS --------------------------------------------------------

    public static TemplateSetSaver getDefaultXmlSaver() {
        return DEFAULT_XML_SAVER;
    }
}
