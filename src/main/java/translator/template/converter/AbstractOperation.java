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
 * Time: 3:23:51
 * Note: It's kind of fun to do the impossible
 */
abstract class AbstractOperation implements Operation {
    private static Logger LOG = LoggerFactory.getLogger(AbstractOperation.class);

    //~--- METHODS ------------------------------------------------------------

    protected static String[] diggArguments(String operationString) {
        LOG.debug("Digging variables from raw string {}", operationString);
        operationString = StringUtils.substringAfter(operationString, "(");
        operationString = StringUtils.substringBefore(operationString, ")");

        String[] args = StringUtils.split(operationString, ",");

        LOG.debug("Digged arguments = {}", Arrays.toString(args));

        return args;
    }

    public abstract String perform(String body);
}
