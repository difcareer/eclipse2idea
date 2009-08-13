package translator.template;

import org.xml.sax.SAXException;

//~--- JDK IMPORTS ------------------------------------------------------------

import java.io.IOException;

//~--- INTERFACES -------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 7:33:38
 * Note: It's kind of fun to do the impossible
 */
public interface TemplateSetLoader {
    public TemplateSet load(String path) throws IOException, SAXException;
}
