package org.zuma.examplePlugin.domain.heimscript;

import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptParser {
    public static HashMap<String, String> parseArgs(String input, ScriptedStatement statement) {
        String trigger = statement.getTrigger();
        List<String> argsTemplates = statement.getArgsTemplates();

        boolean hasArgs = !argsTemplates.isEmpty();
        String cleanedInput = input.replace(trigger + (hasArgs ? "_" : ""), "");

        Pattern argPattern = Pattern.compile("\\{([^{}]+)\\}");
        Matcher matcher = argPattern.matcher(cleanedInput);

        List<String> splitArgs = new ArrayList<>();
        while (matcher.find()) {
            splitArgs.add(matcher.group(1)); // capture group 1 = contents without braces
        }

        if (splitArgs.size() != argsTemplates.size()) {
            throw new MalformedParametersException("Invalid amount of arguments found in " + trigger + " actions. Expected "
                    + String.join(", ", argsTemplates) + ", found " + splitArgs.size() + " args (" + String.join(", ", splitArgs) + ").");
        }

        HashMap<String, String> result = new HashMap<>();

        for (String inner : splitArgs) {
            // split on the last underscore — template may contain underscores, value may contain '-' or underscores
            int sep = inner.lastIndexOf('_');
            if (sep <= 0) {
                throw new MalformedParametersException("Invalid argument format in " + trigger + " actions: {" + inner + "}. Expected '<template>_<value>'.");
            }

            String templateCandidate = inner.substring(0, sep);
            String value = inner.substring(sep + 1);

            if (!argsTemplates.contains(templateCandidate)) {
                throw new MalformedParametersException("Invalid argument found in " + trigger + " actions. {" + inner + "} — template '"
                        + templateCandidate + "' does not exist in " + String.join(", ", argsTemplates) + ".");
            }

            if (value.isEmpty()) {
                throw new MalformedParametersException("Invalid argument found in " + trigger + " actions. {" + inner + "} — value is empty.");
            }

            result.put(templateCandidate, value);
        }

        if (result.size() != argsTemplates.size()) {
            throw new MalformedParametersException("Parsed arguments count differs from the actions template count for " + trigger + ".");
        }

        return result;
    }

    public static String getPreview(ScriptedStatement statement) {
        StringBuilder sb = new StringBuilder(statement.getTrigger());

        for (String argsTemplate : statement.getArgsTemplates()) {
            sb.append("_{")
                    .append(argsTemplate)
                    .append("_")
                    .append("X")
                    .append("}");
        }

        return sb.toString();
    }

    public static <T extends Enum<T> & ScriptedStatement> T matchInputToStatement(String input, Class<T> enumType) {
        List<T> actions = Arrays.asList(enumType.getEnumConstants());

        // Sort by trigger length descending to match the longest trigger first
        actions.sort((a, b) -> Integer.compare(b.getTrigger().length(), a.getTrigger().length()));

        for (T action : actions) {
            long argCount = input.chars().filter(c -> c == '{').count();

            if (input.startsWith(action.getTrigger()) && argCount == action.getArgsTemplates().size()) {
                return action;
            }
        }
        throw new MalformedParametersException("Invalid statement " + input + "! Searched in: " + enumType.getName());
    }
}
