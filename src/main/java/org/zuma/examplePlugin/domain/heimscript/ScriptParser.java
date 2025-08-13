package org.zuma.examplePlugin.domain.heimscript;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptParser {
    public static HashMap<String, String> parseArgs(String input, ScriptedStatement statement) {
        String trigger = statement.getTrigger();
        List<String> argsTemplates = statement.getArgsTemplates();

        boolean hasArgs = !argsTemplates.isEmpty();
        // advance_quest_{quest_id_1}_(step_id_5}
        String cleanedInput = input.replace(trigger + (hasArgs ? "_" : ""), "");
        // {quest_id_1}_(step_id_5}

        Pattern argPattern = Pattern.compile("\\{[^{}]+}");
        Matcher matcher = argPattern.matcher(cleanedInput);

        List<String> splitArgs = new ArrayList<>();
        while (matcher.find()) {
            splitArgs.add(matcher.group());
        }
        // {quest_id_1}
        // (step_id_5}

        HashMap<String, String> result = new HashMap<>();

        if (splitArgs.size() != argsTemplates.size()) {
            throw new MalformedParametersException("Invalid amount of arguments found in " + trigger + " actions. Expected " + String.join(", ", argsTemplates) + ", found " + String.join(", ", splitArgs) + " args.");
        }

        for (String splitArg : splitArgs) {
            String noBrackets = splitArg.substring(1, splitArg.length() - 1);
            // quest_id_1

            String matchingTemplate = null;

            for (String template : argsTemplates) {
                if (noBrackets.startsWith(template)) {
                    matchingTemplate = template;
                    break;
                }
            }

            if (matchingTemplate == null) {
                throw new MalformedParametersException("Invalid argument found in " + trigger + " actions. " + splitArg + " does not exist in " + String.join(", ", argsTemplates) + ".");
            }

            String value = extractArgument(noBrackets, matchingTemplate, statement);
            result.put(matchingTemplate, value);

        }

        if (result.size() != argsTemplates.size()) {
            throw new MalformedParametersException("Invalid argument found in " + trigger + " actions. Parsed arguments count differs from the actions template count.");
        }

        return result;
    }

    private static @NotNull String extractArgument(String noBrackets, String matchingTemplate, ScriptedStatement statement) {
        String remainder = noBrackets.replace(matchingTemplate + "_", "");

        if (remainder.isEmpty()) {
            throw new MalformedParametersException("Invalid argument found in " + statement.getTrigger() + " actions. Remainder is empty. Forgot to append integers?");
        }

        return remainder;

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
        for (T action : enumType.getEnumConstants()) {
            long argCount = input.chars().filter(c -> c == '{').count();
            if (input.startsWith(action.getTrigger()) && argCount == action.getArgsTemplates().size()) {
                return action;
            }
        }
        throw new MalformedParametersException("Invalid statement " + input + "! Searched in: " + enumType.getName());
    }
}

