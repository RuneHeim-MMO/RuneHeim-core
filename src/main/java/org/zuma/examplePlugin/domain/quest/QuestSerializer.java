package org.zuma.examplePlugin.domain.quest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.adapters.RuntimeTypeAdapterFactory;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
import org.zuma.examplePlugin.domain.quest.impl.CollectionStep;
import org.zuma.examplePlugin.domain.quest.impl.DialogStep;

public class QuestSerializer {
    private static final RuntimeTypeAdapterFactory<AbstractQuestStep> stepAdapter = RuntimeTypeAdapterFactory.of(AbstractQuestStep.class, "type") // JSON field "type" determines subclass
            .registerSubtype(CollectionStep.class, StepType.COLLECTION.toString())
            .registerSubtype(DialogStep.class, StepType.DIALOG.toString());

    private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(stepAdapter).create();

    public static String serializeQuest(Quest quest) {
        return gson.toJson(quest);
    }

    public static Quest deserializeQuest(String questStr) {
        Quest quest = gson.fromJson(questStr, Quest.class);

        quest.getSteps().forEach(step -> {
            step.setPlugin(RuneQuestPlugin.getInstance());
            step.setParentQuest(quest);
        });

        return quest;
    }
}
