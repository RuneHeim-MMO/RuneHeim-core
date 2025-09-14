package org.zuma.examplePlugin.domain.quest;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.quest.constants.QuestDifficulty;
import org.zuma.examplePlugin.domain.quest.impl.CollectionStep;
import org.zuma.examplePlugin.domain.quest.impl.DialogStep;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class QuestRegistry {
    private final HashMap<String, Quest> quests; // id:quest

    public QuestRegistry() {
        this.quests = new HashMap<>();

        Quest sheepShearer = new Quest("sheep-shearer", "Sheep Shearer", QuestDifficulty.HARD);
        registerQuest(sheepShearer.addSteps(List.of(
                new CollectionStep(RuneQuestPlugin.getInstance(), sheepShearer, null, List.of(new ItemRecord(ItemId.BALL_OF_WOOL, 20))),
                new DialogStep(RuneQuestPlugin.getInstance(),sheepShearer, null, "sheep-shearer.wool-collected")
        )));
    }

    @Nullable
    public Quest getQuestById(String name) {
        return quests.get(name);
    }

    public void registerQuest(Quest quest) {
        if (quests.containsKey(quest.getId())) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot register quest, quest with name " + quest.getId() + " is already in registry.");
            return;
        }

        quests.put(quest.getId(), quest);
    }

}
