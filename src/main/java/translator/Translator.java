package translator;

import org.apache.commons.cli.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xml.sax.SAXException;

import translator.template.converter.ConverterEclipse2Idea;
import translator.template.eclipse.EclipseTemplateSet;
import translator.template.eclipse.EclipseTemplateSetLoader;
import translator.template.idea.IdeaTemplateSet;
import translator.template.idea.IdeaTemplateSetSaver;

//~--- JDK IMPORTS ------------------------------------------------------------

import java.io.IOException;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Hello world!
 *
 */
public class Translator {
    private static final String      DEFAULT_OUTPUT_FILE = "./output.xml";
    private static CommandLineParser PARSER              = new GnuParser();
    private static final String      USAGE               =
        "eclipse2idea -i <file> [-o <file>]";
    private static final Logger      LOG                 =
        LoggerFactory.getLogger(Translator.class);
    private static Options           OPTIONS;

    //~--- STATIC INITIALIZERS ------------------------------------------------

    static {
        OPTIONS = new Options();
        OptionBuilder.isRequired();
        OptionBuilder.withDescription("path to eclipse template file");
        OptionBuilder.hasArg();
        OptionBuilder.withArgName("file");

        Option pathToEclipseTemplate = OptionBuilder.create("i");

        OPTIONS.addOption(pathToEclipseTemplate);
        OptionBuilder.withDescription("output idea template file (default: output.xml)");
        OptionBuilder.hasArg();
        OptionBuilder.withArgName("file");

        Option pathToIdeaTemplate = OptionBuilder.create("o");

        OPTIONS.addOption(pathToIdeaTemplate);
    }

    //~--- METHODS ------------------------------------------------------------

    public static void main(String[] args) {
        String        pathToEclipseTemplate;
        String        pathToIdeaTemplate;
        HelpFormatter formatter = new HelpFormatter();
        CommandLine   line      = null;

        try {
            line = PARSER.parse(OPTIONS, args);
        } catch (ParseException e) {
            formatter.printHelp(USAGE, OPTIONS);

            return;
        }

        if (line.hasOption("i")) {
            pathToEclipseTemplate = line.getOptionValue("i");
        } else {
            formatter.printHelp(USAGE, OPTIONS);

            return;
        }

        if (line.hasOption("o")) {
            pathToIdeaTemplate = line.getOptionValue("o");
        } else {
            pathToIdeaTemplate = DEFAULT_OUTPUT_FILE;
        }

        LOG.info("Converting..");

        try {
            EclipseTemplateSet eclipseTemplateSet =
                EclipseTemplateSetLoader.getDefaultXmlLoader().load(
                    pathToEclipseTemplate);

            LOG.info("+ Eclipse template loaded from {}", pathToEclipseTemplate);

            IdeaTemplateSet ideaTemplateSet =
                ConverterEclipse2Idea.convert(eclipseTemplateSet);

            LOG.info("+ Eclipse template converted to IDEA template", pathToIdeaTemplate);
            IdeaTemplateSetSaver.getDefaultXmlSaver().save(ideaTemplateSet,
                                                           pathToIdeaTemplate);
            LOG.info("+ IDEA template saved as {}", pathToIdeaTemplate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        LOG.info("Converted successfully!");
    }
}
