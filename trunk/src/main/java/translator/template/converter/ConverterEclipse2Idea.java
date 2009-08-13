package translator.template.converter;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import translator.template.Template;
import translator.template.converter.ImportOperation;
import translator.template.converter.Operation;
import translator.template.converter.UnrecognizedOperationException;
import translator.template.eclipse.EclipseTemplate;
import translator.template.eclipse.EclipseTemplateSet;
import translator.template.idea.IdeaTemplate;
import translator.template.idea.IdeaTemplateSet;
import translator.template.idea.IdeaTemplateVariable;

//~--- JDK IMPORTS ------------------------------------------------------------

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//~--- CLASSES ----------------------------------------------------------------

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.08.2009
 * Time: 10:50:54
 * Note: It's kind of fun to do the impossible
 */
public class ConverterEclipse2Idea {
    private static final String NAME_PATTERN_STRING = "[a-zA-Z0-9\\.]+";
    private static final String OPERATION_PATTERN   =
        "\\$\\{:[a-zA-Z$_]+\\([a-zA-Z$_0-9\\.,]*\\)\\}";
    private static final String  VARIABLE_PATTERN = "\\$\\{[a-zA-Z$_]+[a-zA-Z$_0-9]*\\}";
    private static final Pattern NAME_PATTERN     = Pattern.compile(NAME_PATTERN_STRING);
    private static final Logger LOG               =
        LoggerFactory.getLogger(ConverterEclipse2Idea.class);

    //~--- METHODS ------------------------------------------------------------

    public static IdeaTemplateSet convert(EclipseTemplateSet templateSet) {
        IdeaTemplateSet ideaTemplateSet = new IdeaTemplateSet();
        String          group           = templateSet.getGroup();

        ideaTemplateSet.setGroup(group);
        LOG.debug("Set template group = {}", group);

        List<Template> eclipseTemplates = templateSet.getTemplates();

        LOG.debug("There are {} eclipse templates", eclipseTemplates.size());

        for (Template template : eclipseTemplates) {
            try {
                EclipseTemplate eclipseTemplate = (EclipseTemplate) template;
                IdeaTemplate    ideaTemplate    = convertTemplate(eclipseTemplate);

                improveVariables(ideaTemplate);
                ideaTemplateSet.addTemplate(ideaTemplate);
            } catch (UnrecognizedOperationException e) {
                e.printStackTrace();
            }
        }

        return ideaTemplateSet;
    }

    private static List<IdeaTemplateVariable> generateVariables(String body) {
        LOG.debug("Generation idea variables..");

        List<String> variables                           = diggVariableNames(body);
        List<IdeaTemplateVariable> ideaTemplateVariables =
            new LinkedList<IdeaTemplateVariable>();

        for (String variable : variables) {
            IdeaTemplateVariable ideaTemplateVariable = new IdeaTemplateVariable();
            String               expression           = "\"" + variable + "\"";

            LOG.debug("New idea variable created");
            ideaTemplateVariable.setName(variable);
            LOG.debug("Set idea variable name = {}", variable);
            ideaTemplateVariable.setExpression(expression);
            LOG.debug("Set idea variable expression = {}", expression);
            ideaTemplateVariables.add(ideaTemplateVariable);
            LOG.debug("Idea variable generated successfully");
        }

        return ideaTemplateVariables;
    }

    private static IdeaTemplate convertTemplate(EclipseTemplate eclipseTemplate)
            throws UnrecognizedOperationException {
        String name = eclipseTemplate.getName();
        String body = eclipseTemplate.getBody();

        LOG.debug("Converting eclipse template..", name);

        IdeaTemplate ideaTemplate = new IdeaTemplate();

        LOG.debug("Empty idea template created");
        ideaTemplate.getContext().setOption("JAVA_CODE", true);
        LOG.debug("Set idea template context option JAVA_CODE = true");
        ideaTemplate.setName(convertName(name));
        LOG.debug("Set idea template name = {}", name);
        ideaTemplate.setVariables(generateVariables(body));
        LOG.debug("Set idea template variables");

        String newBody = convertBody(body);

        ideaTemplate.setValue(newBody);
        LOG.debug("Set idea template value = {}", newBody);
        LOG.debug("Eclipse template converted successfully");

        return ideaTemplate;
    }

    private static String convertName(String name) {
        LOG.debug("Converting eclipse template name..");

        Matcher matcher = NAME_PATTERN.matcher(name);

        LOG.debug("Validating name = {}", name);

        if (matcher.matches()) {
            LOG.debug("Name valid, no convertion need");

            return name;
        } else {
            StringBuilder sb = new StringBuilder();

            for (Character c : name.toCharArray()) {
                if (Character.isDigit(c) || Character.isLetter(c) || (c == '.')) {
                    sb.append(c);
                } else {
                    LOG.debug("Found illegal character '{c}'. Replaced with '.' (dot)");
                    sb.append(".");
                }
            }

            String newName = sb.toString();

            LOG.debug("Name converted to {}", newName);

            return newName;
        }
    }

    private static String convertBody(String body) throws UnrecognizedOperationException {
        LOG.debug("Converting eclipse body..");
        body = replaceVariables(body);
        body = performOperations(body);
        body = StringUtils.trim(body);
        LOG.debug("Eclipse body converted successfully");

        return body;
    }

    private static String replaceVariables(String body) {
        LOG.debug("Replacing variables..");

        List<String> variableNames = diggVariableNames(body);

        for (String variable : variableNames) {
            String eclipseRawVariableName = "${" + variable + "}";
            String ideaRawVariableName    = "$" + variable + "$";

            body = StringUtils.replace(body, eclipseRawVariableName, ideaRawVariableName);
            LOG.debug("Replaced {} all eclipse variables with {} idea variable name",
                      eclipseRawVariableName, ideaRawVariableName);
        }

        return body;
    }

    private static List<Operation> recognizeOperations(String body)
            throws UnrecognizedOperationException {
        LOG.debug("Recognizing operations..");

        List<String> operationRawStrings = diggOperations(body);

        LOG.debug("There are {} operations to recognize", operationRawStrings.size());

        List<Operation> operations = new LinkedList<Operation>();

        for (String operationRawString : operationRawStrings) {
            operations.add(recognizeOperation(operationRawString));
        }

        return operations;
    }

    private static String performOperations(String body)
            throws UnrecognizedOperationException {
        List<Operation> operations = recognizeOperations(body);

        LOG.debug("Performing operations..");
        LOG.debug("There are {} operations to perform", operations.size());

        for (Operation operation : operations) {
            body = operation.perform(body);
        }

        return body;
    }

    private static List<String> diggVariableNames(String body) {
        LOG.debug("Digging variables from string = [{}]", body);

        List<String> variableNames = new LinkedList<String>();
        Matcher      matcher       = Pattern.compile(VARIABLE_PATTERN).matcher(body);

        LOG.debug("New matcher created with pattern = {}", VARIABLE_PATTERN);

        while (matcher.find()) {
            String variableName = matcher.group();

            LOG.debug("Found variable with raw name = {}", variableName);
            variableName = chopName(variableName);

            if (!variableNames.contains(variableName)) {
                variableNames.add(variableName);
                LOG.debug("New variable added = {}", variableName);
            } else {
                LOG.debug("This variable is already added - skipped");
            }
        }

        LOG.debug("Found total {} variables", variableNames.size());

        return variableNames;
    }

    private static List<String> diggOperations(String body) {
        LOG.debug("Digging operations raw strings from body", body);

        List<String> operationNames = new LinkedList<String>();
        Matcher      matcher        = Pattern.compile(OPERATION_PATTERN).matcher(body);

        LOG.debug("New matcher created with pattern = {}", OPERATION_PATTERN);

        while (matcher.find()) {
            String operationName = matcher.group();

            LOG.debug("Found operation raw string = {}", operationName);
            operationName = chopName(operationName);
            operationNames.add(operationName);
            LOG.debug("New operation raw string added = ", operationName);
        }

        LOG.debug("Found total {} operation raw strings", operationNames.size());

        return operationNames;
    }

    private static String chopName(String name) {
        String chopped = name;

        // order is important as second includes first!
        chopped = StringUtils.remove(chopped, "${:");
        chopped = StringUtils.remove(chopped, "${");
        chopped = StringUtils.remove(chopped, "}");
        LOG.debug("Chopped {} -> {}", name, chopped);

        return chopped;
    }

    private static Operation recognizeOperation(String operationRawString)
            throws UnrecognizedOperationException {
        LOG.debug("Trying to recognize {} operation raw string ", operationRawString);

        if (StringUtils.startsWith(operationRawString, "import")) {
            LOG.debug("Import operation recognized");

            return new ImportOperation(operationRawString);
        } else {
            String errString = "Couldn't recognize operation from = "
                               + operationRawString;

            LOG.error(errString);

            throw new UnrecognizedOperationException(errString);
        }
    }

    private static boolean improveVariables(IdeaTemplate template) {
        LOG.debug("Improving variables in template..");

        long count = 0;

        for (IdeaTemplateVariable variable : template.getVariables()) {
            String name = variable.getName();

            // ElementType elementName -> suggestVariableName() for elementName
            if (name.endsWith("Name")) {
                // elementName -> element
                String elementType = StringUtils.substringBefore(name, "Name");

                // element -> Element
                elementType = StringUtils.capitalize(elementType);

                // Element -> ElementType
                elementType += "Type";

                if (template.hasVariableWithName(elementType)) {
                    variable.setExpression("suggestVariableName()");
                    variable.setAlwaysStopAt(false);
                    count++;
                }
            }

            // ElementType elementType -> suggerstVariableName() for elementType
            if (Character.isLowerCase(name.charAt(0))) {
                if (template.hasVariableWithName(StringUtils.capitalize(name))) {
                    variable.setExpression("suggestVariableName()");
                    variable.setAlwaysStopAt(false);
                    count++;
                }
            }
        }

        IdeaTemplateVariable variable = template.getVariableWithName("cursor");

        if (variable != null) {
            LOG.debug("Detected 'cursor' variable");
            LOG.debug("+removing it from idea variables");
            template.getVariables().remove(variable);
            LOG.debug("+replacing in body with $END$");
            template.setValue(template.getValue().replace("$cursor$", "$END$"));
        }

        if (count != 0) {
            LOG.debug("{} variables was improved successfully", count);

            return true;
        } else {
            LOG.debug("No one variable was improved");

            return false;
        }
    }
}
