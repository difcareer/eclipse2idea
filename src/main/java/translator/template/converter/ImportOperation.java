package translator.template.converter;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//~--- JDK IMPORTS ------------------------------------------------------------

import java.util.Arrays;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 12.08.2009
 * Time: 2:37:07
 * Note: It's kind of fun to do the impossible
 */
class ImportOperation extends AbstractOperation {
    private static final Logger LOG = LoggerFactory.getLogger(ImportOperation.class);

    //~--- FIELDS -------------------------------------------------------------

    private String operationRawString;

    //~--- CONSTRUCTORS -------------------------------------------------------

    public ImportOperation(String operationRawString) {
        this.operationRawString = operationRawString;
    }

    //~--- METHODS ------------------------------------------------------------

    public String perform(String body) {
        LOG.debug("Performing import operation..");

        String[] args = diggArguments(operationRawString);

        if ((args == null) || (args.length == 0)) {
            LOG.debug("There is no any class to import");

            return body;
        }

        LOG.debug("Import classes are = {}", Arrays.toString(args));
        body = StringUtils.remove(body, "${:" + operationRawString + "}");

        for (String fqClassName : args) {
            String className = StringUtils.substringAfterLast(fqClassName, ".");

            body = StringUtils.replace(body, className, fqClassName);
            LOG.debug("Replaced all occurances of {} class with FQ name {}", className,
                      fqClassName);
        }

        return body;
    }
}
