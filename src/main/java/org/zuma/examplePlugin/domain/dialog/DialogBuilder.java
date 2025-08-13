package org.zuma.examplePlugin.domain.dialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.zuma.examplePlugin.core.adapters.DialogBuilderAdapter;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.InteractiveDialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.PassiveDialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.ConditionStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

public class DialogBuilder {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DialogBuilder.class, new DialogBuilderAdapter())
            .create();

    private final String id;
    public final List<DialogStep> steps = new ArrayList<>();

    public String serialize() {
        return gson.toJson(this);
    }

    public static DialogBuilder deserialize(String json) {
        return gson.fromJson(json, DialogBuilder.class);
    }

    public DialogBuilder(String id) {
        this.id = id;
    }

    public static DialogBuilder create(String id) {
        return new DialogBuilder(id);
    }

    public DialogBuilder passiveStep(Consumer<PassiveStepBuilder> stepConsumer) {
        PassiveStepBuilder builder = new PassiveStepBuilder();
        stepConsumer.accept(builder);
        steps.add(builder.build());
        return this;
    }

    public DialogBuilder interactiveStep(Consumer<InteractiveStepBuilder> stepConsumer) {
        InteractiveStepBuilder builder = new InteractiveStepBuilder(id);
        stepConsumer.accept(builder);
        steps.add(builder.build());
        return this;
    }

    // New: condition step
    public DialogBuilder conditionStep(Consumer<ConditionStepBuilder> stepConsumer) {
        ConditionStepBuilder builder = new ConditionStepBuilder(id);
        stepConsumer.accept(builder);
        steps.add(builder.build());
        return this;
    }

    public Dialog build(UUID dialogHolderUUID) {
        return new Dialog(id, dialogHolderUUID, steps);
    }

    // Inner builder for passive steps
    public static class PassiveStepBuilder {
        private final List<DialogText> texts = new ArrayList<>();
        private List<String> actions = new ArrayList<>();
        private Runnable runnable = null;

        public PassiveStepBuilder text(List<String> content, boolean flag) {
            content.forEach(c -> texts.add(new DialogText(c, flag)));
            return this;
        }

        public PassiveStepBuilder text(List<String> content) {
            return text(content, true);
        }

        public PassiveStepBuilder text(String content) {
            return text(List.of(content), true);
        }

        public PassiveStepBuilder randomTextMultiline(List<List<String>> content) {
            return text(content.get(new Random().nextInt(content.size())), true);
        }

        public PassiveStepBuilder randomText(List<String> content) {
            String randomLine = content.get(new Random().nextInt(content.size()));
            return text(List.of(randomLine), true);
        }

        public PassiveStepBuilder action(String action) {
            this.actions.add(action);
            return this;
        }

        public PassiveStepBuilder runnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public PassiveDialogStep build() {
            return new PassiveDialogStep(texts, actions, runnable);
        }
    }

    // Inner builder for interactive steps
    public static class InteractiveStepBuilder {
        private final List<DialogText> texts = new ArrayList<>();
        private final List<InteractiveDialogOption> options = new ArrayList<>();
        private final String parentId;

        public InteractiveStepBuilder(String parentId) {
            this.parentId = parentId;
        }

        public InteractiveStepBuilder text(List<String> content, boolean flag) {
            content.forEach(c -> texts.add(new DialogText(c, flag)));
            return this;
        }

        public InteractiveStepBuilder text(List<String> content) {
            return text(content, true);
        }

        public InteractiveStepBuilder text(String content) {
            return text(List.of(content), true);
        }

        public InteractiveStepBuilder randomTextMultiline(List<List<String>> content) {
            return text(content.get(new Random().nextInt(content.size())), true);
        }

        public InteractiveStepBuilder randomText(List<String> content) {
            String randomLine = content.get(new Random().nextInt(content.size()));
            return text(List.of(randomLine), true);
        }

        public InteractiveStepBuilder option(String label, Consumer<OptionBuilder> optionConsumer) {
            OptionBuilder builder = new OptionBuilder(label, parentId);
            optionConsumer.accept(builder);
            options.add(builder.build());
            return this;
        }

        public InteractiveStepBuilder option(String label) {
            options.add(new InteractiveDialogOption(label, null, null, null));
            return this;
        }

        public InteractiveDialogStep build() {
            return new InteractiveDialogStep(texts, options);
        }
    }

    // Inner builder for condition steps
    public static class ConditionStepBuilder {
        private final String parentId;
        private String conditionRaw = null;
        private List<DialogStep> truthyFollowUp = null;
        private List<DialogStep> falsyFollowUp = null;

        public ConditionStepBuilder(String parentId) {
            this.parentId = parentId;
        }

        public ConditionStepBuilder condition(String rawCondition) {
            this.conditionRaw = rawCondition;
            return this;
        }

        public ConditionStepBuilder truthyFollowUp(Consumer<DialogBuilder> followupBuilderConsumer) {
            DialogBuilder followup = new DialogBuilder(parentId);
            followupBuilderConsumer.accept(followup);
            this.truthyFollowUp = followup.steps;
            return this;
        }

        public ConditionStepBuilder falsyFollowUp(Consumer<DialogBuilder> followupBuilderConsumer) {
            DialogBuilder followup = new DialogBuilder(parentId);
            followupBuilderConsumer.accept(followup);
            this.falsyFollowUp = followup.steps;
            return this;
        }

        public ConditionStep build() {
            return new ConditionStep(new DialogCondition(conditionRaw, truthyFollowUp, falsyFollowUp));
        }
    }

    // Inner builder for options
    public static class OptionBuilder {
        private final String label;
        private List<DialogStep> followupSteps = null;
        private List<String> actions = new ArrayList<>();
        private Runnable runnable = null;
        private final String parentId;

        public OptionBuilder(String label, String parentId) {
            this.label = label;
            this.parentId = parentId;
        }

        public OptionBuilder followupSteps(Consumer<DialogBuilder> dialogBuilderConsumer) {
            DialogBuilder followupBuilder = new DialogBuilder(parentId);
            dialogBuilderConsumer.accept(followupBuilder);
            this.followupSteps = followupBuilder.steps;
            return this;
        }

        public OptionBuilder action(String action, Consumer<DialogBuilder> dialogBuilderConsumer) {
            this.actions.add(action);
            this.followupSteps(dialogBuilderConsumer);
            return this;
        }

        public OptionBuilder action(String action) {
            this.actions.add(action);
            return this;
        }

        public OptionBuilder runnable(Runnable runnable, Consumer<DialogBuilder> dialogBuilderConsumer) {
            this.runnable = runnable;
            this.followupSteps(dialogBuilderConsumer);
            return this;
        }

        public OptionBuilder runnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public InteractiveDialogOption build() {
            return new InteractiveDialogOption(label, followupSteps, actions, runnable);
        }
    }
}
