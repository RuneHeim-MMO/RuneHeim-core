package org.zuma.examplePlugin.domain.quest;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.quest.constants.QuestDifficulty;
import org.zuma.examplePlugin.domain.quest.impl.CollectionStep;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class QuestRegistry {
    private final HashMap<String, Quest> quests;

    public QuestRegistry() {
        this.quests = new HashMap<>();

        Quest newQuest = new Quest("sheep-shearer", "Sheep Shearer", QuestDifficulty.HARD).setRequirements(List.of(new SkillRequirement(SkillType.PRAYER, 5))).setRequiredCompletedQuests(List.of("invalid-quest"));
        registerQuest(newQuest.addSteps(List.of(
                new CollectionStep(RuneQuestPlugin.getInstance(), newQuest, null, List.of(new ItemRecord(ItemId.IRON_ORE, 5))),
                new CollectionStep(RuneQuestPlugin.getInstance(), newQuest, null, List.of(new ItemRecord(ItemId.COPPER_ORE, 5))),
                new CollectionStep(RuneQuestPlugin.getInstance(), newQuest, null, List.of(new ItemRecord(ItemId.LOG, 5)))
        )));
    }

    @Nullable
    public Quest getQuestByName(String name) {
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
