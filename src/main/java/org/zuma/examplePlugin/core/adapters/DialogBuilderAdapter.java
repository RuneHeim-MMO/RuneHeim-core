package org.zuma.examplePlugin.core.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.dialog.DialogText;
import org.zuma.examplePlugin.domain.dialog.InteractiveDialogOption;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.ConditionStep;
import org.zuma.examplePlugin.domain.dialog.impl.InteractiveDialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.PassiveDialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.TeleportBranchStep;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogBuilderAdapter extends TypeAdapter<DialogBuilder> {

    @Override
    public void write(JsonWriter out, DialogBuilder builder) throws IOException {
        if (builder == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("id").value(getId(builder));
        out.name("steps");
        out.beginArray();
        for (var step : getSteps(builder)) {
            out.beginObject();
            out.name("type").value(step.getClass().getSimpleName());
            out.name("uniqueIdentifier").value(getUniqueIdentifier(step));

            if (step instanceof TeleportBranchStep) {
                out.name("teleportToUniqueIdentifier").value(getTeleportToUniqueIdentifier(step));

            }

            if (step instanceof InteractiveDialogStep interactiveStep) {
                Boolean loop = getLoop(interactiveStep);
                if (loop != null && loop) {
                    out.name("loop").value(loop);
                }
            }

            // content
            out.name("content");
            out.beginArray();
            for (DialogText line : getContent(step)) {
                if (step instanceof InteractiveDialogStep && !line.typewriter()) {
                    // Skip artificially added interactive lines
                    continue;
                }
                out.beginObject();
                out.name("line").value(line.line());
                out.name("typewriter").value(line.typewriter());
                out.endObject();
            }
            out.endArray();

            // interactive options if present
            List<InteractiveDialogOption> options = getOptions(step);
            if (options != null) {
                out.name("options");
                out.beginArray();
                for (InteractiveDialogOption opt : options) {
                    out.beginObject();
                    out.name("label").value(opt.content());
                    if (opt.actions() != null) {
                        out.name("actions");
                        out.beginArray();
                        for (String action : opt.actions()) {
                            out.value(action);
                        }
                        out.endArray();
                    }
                    if (opt.subContent() != null && !opt.subContent().isEmpty()) {
                        out.name("followup");
                        DialogBuilder followup = new DialogBuilder(getId(builder));
                        followup.steps.addAll(opt.subContent());
                        write(out, followup);
                    }
                    out.endObject();
                }
                out.endArray();
            }

            // PassiveDialogStep actions
            if (step instanceof PassiveDialogStep passiveStep) {
                List<String> passiveActions = getActions(passiveStep); // get actions
                if (passiveActions != null && !passiveActions.isEmpty()) {
                    out.name("actions");
                    out.beginArray();
                    for (String action : passiveActions) {
                        out.value(action);
                    }
                    out.endArray();
                }
            }

            // condition step specific serialization
            if (ConditionStep.class.isAssignableFrom(step.getClass())) {
                List<String> conditions = getConditions(step);
                List<DialogStep> truthyFollowUp = getTruthyFollowUp(step);
                List<DialogStep> falsyFollowUp = getFalsyFollowUp(step);
                ConditionStep.LogicalOperator logicalOperator = getLogicalOperator(step);

                if (conditions != null) {
                    out.name("conditions");
                    out.beginArray();
                    for (String cond : conditions) {
                        out.value(cond);
                    }
                    out.endArray();
                }

                if (logicalOperator != null) {
                    out.name("logicalOperator").value(logicalOperator.toString());
                }

                if (truthyFollowUp != null && !truthyFollowUp.isEmpty()) {
                    out.name("truthyFollowup");
                    DialogBuilder truthyBuilder = new DialogBuilder(getId(builder));
                    truthyBuilder.steps.addAll(truthyFollowUp);
                    write(out, truthyBuilder);
                }

                if (falsyFollowUp != null && !falsyFollowUp.isEmpty()) {
                    out.name("falsyFollowup");
                    DialogBuilder falsyBuilder = new DialogBuilder(getId(builder));
                    falsyBuilder.steps.addAll(falsyFollowUp);
                    write(out, falsyBuilder);
                }
            }

            out.endObject();
        }
        out.endArray();
        out.endObject();
    }


    @Override
    public DialogBuilder read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        in.beginObject();
        String id = null;
        DialogBuilder builder = null;

        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "id" -> {
                    id = in.nextString();
                    builder = DialogBuilder.create(id);
                }
                case "steps" -> {
                    if (builder == null) {
                        throw new IOException("'id' must be before 'steps' in JSON");
                    }
                    in.beginArray();
                    while (in.hasNext()) {
                        readStep(in, builder, id);
                    }
                    in.endArray();
                }
                default -> in.skipValue();
            }
        }

        in.endObject();
        return builder;
    }

    private void readStep(JsonReader in, DialogBuilder builder, String parentId) throws IOException {
        in.beginObject();
        String type = null;
        String uniqueIdentifier = null;
        String teleportToUniqueIdentifier = null;
        boolean loop = false;
        List<DialogText> content = new ArrayList<>();
        List<OptionData> options = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        ConditionStep.LogicalOperator logicalOperator = ConditionStep.LogicalOperator.AND;

        // Condition-specific temporary holders
        DialogBuilder truthyFollowupBuilder = null;
        DialogBuilder falsyFollowupBuilder = null;

        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "type" -> type = in.nextString();
                case "loop" -> loop = in.nextBoolean();
                case "uniqueIdentifier" -> uniqueIdentifier = in.nextString();
                case "teleportToUniqueIdentifier" -> teleportToUniqueIdentifier = in.nextString();
                case "logicalOperator" -> logicalOperator = ConditionStep.LogicalOperator.valueOf(in.nextString());
                case "content" -> {
                    in.beginArray();
                    while (in.hasNext()) {
                        in.beginObject();
                        String line = null;
                        boolean typewriter = true;
                        while (in.hasNext()) {
                            String ln = in.nextName();
                            if ("line".equals(ln)) {
                                line = in.nextString();
                            } else if ("typewriter".equals(ln)) {
                                typewriter = in.nextBoolean();
                            } else {
                                in.skipValue();
                            }
                        }
                        in.endObject();
                        if (line != null) {
                            content.add(new DialogText(line, typewriter));
                        }
                    }
                    in.endArray();
                }
                case "options" -> {
                    in.beginArray();
                    while (in.hasNext()) {
                        in.beginObject();
                        String label = null;
                        List<String> optActions = new ArrayList<>();
                        DialogBuilder followupBuilder = null;
                        while (in.hasNext()) {
                            String optName = in.nextName();
                            switch (optName) {
                                case "label" -> label = in.nextString();
                                case "actions" -> {
                                    in.beginArray();
                                    while (in.hasNext()) {
                                        optActions.add(in.nextString());
                                    }
                                    in.endArray();
                                    // then store `actions` instead of `action`
                                }
                                case "followup" -> followupBuilder = read(in);
                                default -> in.skipValue();
                            }
                        }
                        in.endObject();
                        options.add(new OptionData(label, optActions, followupBuilder));
                    }
                    in.endArray();
                }
                case "actions" -> {
                    in.beginArray();
                    while (in.hasNext()) {
                        actions.add(in.nextString());
                    }
                    in.endArray();
                }
                case "conditions" -> {
                    in.beginArray();
                    while (in.hasNext()) {
                        conditions.add(in.nextString());
                    }
                    in.endArray();
                }
                case "truthyFollowup" -> truthyFollowupBuilder = read(in);
                case "falsyFollowup" -> falsyFollowupBuilder = read(in);
                default -> in.skipValue();
            }
        }
        in.endObject();

        switch (type) {
            case "TeleportBranchStep" -> {
                String finalTeleportToUniqueIdentifier = teleportToUniqueIdentifier;
                builder.teleportStep(passive -> {
                    passive.teleportTo(finalTeleportToUniqueIdentifier);
                }, uniqueIdentifier);
            }
            case "PassiveDialogStep" -> builder.passiveStep(passive -> {
                for (DialogText dt : content) {
                    passive.text(List.of(dt.line()), dt.typewriter());
                }
                actions.forEach(passive::action);
            }, uniqueIdentifier);
            case "InteractiveDialogStep" -> {
                Boolean finalLoop = loop;
                builder.interactiveStep(interactive -> {
                    for (DialogText dt : content) {
                        List<String> lines = content.stream()
                                .map(DialogText::line)
                                .toList();

                        if (finalLoop) {
                            interactive.loop();
                        }
                        interactive.text(lines, dt.typewriter());
                    }
                    for (OptionData opt : options) {
                        interactive.option(opt.label, option -> {
                            if (opt.actions != null) {
                                option.actions(opt.actions);
                            }
                            if (opt.followup != null) {
                                option.followupSteps(fb -> fb.steps.addAll(opt.followup.steps));
                            }
                        });
                    }
                }, uniqueIdentifier);
            }
            case "ConditionStep" -> {
                DialogBuilder finalTruthyFollowupBuilder = truthyFollowupBuilder;
                DialogBuilder finalFalsyFollowupBuilder = falsyFollowupBuilder;
                ConditionStep.LogicalOperator finalLogicalOperator = logicalOperator;
                builder.conditionStep(cond -> {
                    conditions.forEach(cond::condition);

                    if (finalTruthyFollowupBuilder != null) {
                        cond.truthyFollowUp(fb -> fb.steps.addAll(finalTruthyFollowupBuilder.steps));
                    }
                    if (finalFalsyFollowupBuilder != null) {
                        cond.falsyFollowUp(fb -> fb.steps.addAll(finalFalsyFollowupBuilder.steps));
                    }

                    cond.logicalOperator(finalLogicalOperator);
                }, uniqueIdentifier);
            }
            case null, default -> throw new IOException("Unknown step type: " + type);
        }

    }

    // Helper to hold option data during parse
    private static class OptionData {
        String label;
        List<String> actions;
        DialogBuilder followup;

        OptionData(String label, List<String> actions, DialogBuilder followup) {
            this.label = label;
            this.actions = actions;
            this.followup = followup;
        }
    }

    // ====== Reflection Helpers ======
    private String getId(DialogBuilder builder) {
        try {
            var f = DialogBuilder.class.getDeclaredField("id");
            f.setAccessible(true);
            return (String) f.get(builder);
        } catch (Exception e) {
            return null;
        }
    }

    private List<?> getSteps(DialogBuilder builder) {
        try {
            var f = DialogBuilder.class.getDeclaredField("steps");
            f.setAccessible(true);
            return (List<?>) f.get(builder);
        } catch (Exception e) {
            return List.of();
        }
    }

    @SuppressWarnings("unchecked")
    private List<DialogText> getContent(Object step) {
        try {
            var f = step.getClass().getSuperclass().getDeclaredField("content");
            f.setAccessible(true);
            return (List<DialogText>) f.get(step);
        } catch (Exception e) {
            return List.of();
        }
    }

    @SuppressWarnings("unchecked")
    private List<InteractiveDialogOption> getOptions(Object step) {
        try {
            var f = step.getClass().getDeclaredField("interactiveContent");
            f.setAccessible(true);
            return (List<InteractiveDialogOption>) f.get(step);
        } catch (Exception e) {
            return null;
        }
    }

    private String getUniqueIdentifier(Object step) {
        Class<?> clazz = step.getClass();
        while (clazz != null) {
            try {
                var f = clazz.getDeclaredField("uniqueIdentifier");
                f.setAccessible(true);
                return String.valueOf(f.get(step));
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private String getTeleportToUniqueIdentifier(Object step) {
        try {
            var f = step.getClass().getDeclaredField("teleportToUniqueIdentifier");
            f.setAccessible(true);
            return (String) f.get(step);
        } catch (Exception e) {
            return null;
        }
    }

    private Boolean getLoop(Object step) {
        try {
            var f = step.getClass().getDeclaredField("loop");
            f.setAccessible(true);
            return (Boolean) f.get(step);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> getActions(Object step) {
        try {
            var f = step.getClass().getDeclaredField("actions");
            f.setAccessible(true);
            return (List<String>) f.get(step);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> getConditions(Object step) {
        try {
            var f = step.getClass().getDeclaredField("conditions");
            f.setAccessible(true);
            return (List<String>) f.get(step);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private List<DialogStep> getTruthyFollowUp(Object step) {
        try {
            var f = step.getClass().getDeclaredField("truthyFollowUp");
            f.setAccessible(true);
            return (List<DialogStep>) f.get(step);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private List<DialogStep> getFalsyFollowUp(Object step) {
        try {
            var f = step.getClass().getDeclaredField("falsyFollowUp");
            f.setAccessible(true);
            return (List<DialogStep>) f.get(step);
        } catch (Exception e) {
            return null;
        }
    }

    private ConditionStep.LogicalOperator getLogicalOperator(Object step) {
        try {
            var f = step.getClass().getDeclaredField("logicalOperator");
            f.setAccessible(true);
            return (ConditionStep.LogicalOperator) f.get(step);
        } catch (Exception e) {
            return null;
        }
    }

}

