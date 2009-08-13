package translator;

import translator.template.converter.ConverterEclipse2Idea;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 10:48:26
 * Note: It's kind of fun to do the impossible
 */
public class TemplateConverter {
    private static final ConverterEclipse2Idea ECLIPSE_TO_IDEA =
        new ConverterEclipse2Idea();

    //~--- CONSTRUCTORS -------------------------------------------------------

    private TemplateConverter() {}

    //~--- GET METHODS --------------------------------------------------------

    public static ConverterEclipse2Idea getDefaultEclipseToIDEAConverter() {
        return ECLIPSE_TO_IDEA;
    }
}
