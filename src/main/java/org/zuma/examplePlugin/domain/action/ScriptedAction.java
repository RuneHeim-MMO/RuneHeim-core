package org.zuma.examplePlugin.domain.action;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum ScriptedAction {
    OPEN_INVENTORY("open_inventory", "id"), // open_inventory_{id_4}
    GIVE_ITEM("give_item", "id", "amount"),
    ROLL_DROP_TABLE("roll_drop_table", "id"),
    SPAWN_MOB("spawn_mob", "type", "world", "x", "y", "z"),
    GIVE_DISTRIBUTED_XP("give_distributed_xp", "skill", "xp"),
    GIVE_PERSONAL_XP("give_personal_xp", "skill", "xp"),
    TELEPORT_PLAYER("teleport_player", "world", "x", "y", "z"),
    DIALOG_HOLDER_SOUND("dialog_holder_sound","sound"),
    DIALOG_HOLDER_FORWARD_INTERACTION("dialog_holder_forward_interaction");

    private final String trigger;
    private final List<String> argsTemplates;

    ScriptedAction(String trigger, String... argsTemplates) {
        this.trigger = trigger;
        this.argsTemplates = new ArrayList<>(List.of(argsTemplates));
    }

    public static ScriptedAction toAction(String input) {
        for (ScriptedAction action : ScriptedAction.values()) {
            long argCount = input.chars().filter(c -> c == '{').count();

            if (input.startsWith(action.trigger) && argCount == action.argsTemplates.size()) {
                return action;
            }
        }

        throw new MalformedParametersException("Invalid actions " + input);
    }

    public boolean sameTypeAndArgs(ScriptedAction action) {
        if (!action.trigger.startsWith(this.trigger)) {
            return false;
        }

        if (action.argsTemplates.size() != this.argsTemplates.size()) {
            return false;
        }

        return true;
    }

    public HashMap<String, String> parseArgs(String input) {
        boolean hasArgs = !this.argsTemplates.isEmpty();
        // advance_quest_{quest_id_1}_(step_id_5}
        String cleanedInput = input.replace(this.trigger + (hasArgs ? "_" : ""), "");
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

        if (splitArgs.size() != this.argsTemplates.size()) {
            throw new MalformedParametersException("Invalid amount of arguments found in " + this.trigger + " actions. Expected " + String.join(", ", this.argsTemplates) + ", found " + String.join(", ", splitArgs) + " args.");
        }

        for (String splitArg : splitArgs) {
            String noBrackets = splitArg.substring(1, splitArg.length() - 1);
            // quest_id_1

            String matchingTemplate = null;

            for (String template : this.argsTemplates) {
                if (noBrackets.startsWith(template)) {
                    matchingTemplate = template;
                    break;
                }
            }

            if (matchingTemplate == null) {
                throw new MalformedParametersException("Invalid argument found in " + this.trigger + " actions. " + splitArg + " does not exist in " + String.join(", ", this.argsTemplates) + ".");
            }

            String value = extractArgument(noBrackets, matchingTemplate);
            result.put(matchingTemplate, value);

        }

        if (result.size() != this.argsTemplates.size()) {
            throw new MalformedParametersException("Invalid argument found in " + this.trigger + " actions. Parsed arguments count differs from the actions template count.");
        }

        return result;
    }

    private @NotNull String extractArgument(String noBrackets, String matchingTemplate) {
        String remainder = noBrackets.replace(matchingTemplate + "_", "");

        if (remainder.isEmpty()) {
            throw new MalformedParametersException("Invalid argument found in " + this.trigger + " actions. Remainder is empty. Forgot to append integers?");
        }

        return remainder;

    }

    public String getPreview() {
        StringBuilder sb = new StringBuilder(trigger);

        for (String argsTemplate : argsTemplates) {
            sb.append("_{")
                    .append(argsTemplate)
                    .append("_")
                    .append("X")
                    .append("}");
        }

        return sb.toString();
    }
}
