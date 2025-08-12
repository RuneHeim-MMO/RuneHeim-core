package org.zuma.examplePlugin.domain.dialog;

import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.InteractiveDialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.PassiveDialogStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

public class DialogBuilder {
    private final String id;
    private final List<DialogStep> steps = new ArrayList<>();

    private DialogBuilder(String id) {
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

    public Dialog build(UUID dialogHolderUUID) {
        return new Dialog(id, dialogHolderUUID, steps);
    }

    // Inner builder for passive steps
    public static class PassiveStepBuilder {
        private final List<DialogText> texts = new ArrayList<>();
        private String action = null;
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
            this.action = action;
            return this;
        }

        public PassiveStepBuilder runnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public PassiveDialogStep build() {
            return new PassiveDialogStep(texts, action, runnable);
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

        public InteractiveStepBuilder text(String content, boolean flag) {
            texts.add(new DialogText(content, flag));
            return this;
        }

        public InteractiveStepBuilder text(String content) {
            return text(content, true);
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

    // Inner builder for options
    public static class OptionBuilder {
        private final String label;
        private List<DialogStep> followupSteps = null;
        private String action = null;
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
            this.action = action;
            this.followupSteps(dialogBuilderConsumer);
            return this;
        }

        public OptionBuilder action(String action) {
            this.action = action;
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
            return new InteractiveDialogOption(label, followupSteps, action, runnable);
        }
    }
}
