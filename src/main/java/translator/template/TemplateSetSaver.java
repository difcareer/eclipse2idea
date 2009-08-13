package translator.template;

import java.io.OutputStream;
import java.util.List;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 7:51:45
 * Note: It's kind of fun to do the impossible
 */
public abstract class  TemplateSetSaver{
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    //~--- METHODS ------------------------------------------------------------

    public abstract void save(TemplateSet templateSet, String path);

    public abstract void save(TemplateSet templateSet, OutputStream out);
}
