package org.zuma.examplePlugin.core.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.dialog.DialogText;
import org.zuma.examplePlugin.domain.dialog.InteractiveDialogOption;

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

            // content
            out.name("content");
            out.beginArray();
            for (DialogText line : getContent(step)) {
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
                        // Serialize followup steps recursively
                        DialogBuilder followup = new DialogBuilder(getId(builder));
                        followup.steps.addAll(opt.subContent());
                        write(out, followup);
                    }
                    out.endObject();
                }
                out.endArray();
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
        List<DialogText> content = new ArrayList<>();
        List<OptionData> options = new ArrayList<>();

        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "type" -> type = in.nextString();
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
                        String action = null;
                        DialogBuilder followupBuilder = null;
                        while (in.hasNext()) {
                            String optName = in.nextName();
                            switch (optName) {
                                case "label" -> label = in.nextString();
                                case "actions" -> action = in.nextString();
                                case "followup" -> followupBuilder = read(in);
                                default -> in.skipValue();
                            }
                        }
                        in.endObject();
                        options.add(new OptionData(label, action, followupBuilder));
                    }
                    in.endArray();
                }
                default -> in.skipValue();
            }
        }
        in.endObject();

        if ("PassiveDialogStep".equals(type)) {
            builder.passiveStep(passive -> {
                List<String> lines = content.stream()
                        .map(DialogText::line)
                        .toList();
                passive.text(lines, true);
            });
        } else if ("InteractiveDialogStep".equals(type)) {
            builder.interactiveStep(interactive -> {
                for (DialogText dt : content) {
                    List<String> lines = content.stream()
                            .map(DialogText::line)
                            .toList();

                    interactive.text(lines, dt.typewriter());
                }
                for (OptionData opt : options) {
                    interactive.option(opt.label, option -> {
                        if (opt.action != null) {
                            option.action(opt.action);
                        }
                        if (opt.followup != null) {
                            option.followupSteps(fb -> fb.steps.addAll(opt.followup.steps));
                        }
                    });
                }
            });
        } else {
            throw new IOException("Unknown step type: " + type);
        }

    }

    // Helper to hold option data during parse
    private static class OptionData {
        String label;
        String action;
        DialogBuilder followup;

        OptionData(String label, String action, DialogBuilder followup) {
            this.label = label;
            this.action = action;
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
}