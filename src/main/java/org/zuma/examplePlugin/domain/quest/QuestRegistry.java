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

        Quest sheepShearer = new Quest("sheep-shearer", "Sheep Shearer", QuestDifficulty.EASY);
        registerQuest(sheepShearer.addSteps(List.of(
                new CollectionStep(RuneQuestPlugin.getInstance(), sheepShearer, "Collect 20 wool and spin them into balls of wool.", List.of(new ItemRecord(ItemId.BALL_OF_WOOL, 20)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), sheepShearer, "Bring 20 balls of wool to Shepherd.", "sheep-shearer.wool-collected", null)
        )));


        Quest tutorialQuest = new Quest("tutorial", "Tutorial", QuestDifficulty.EASY);
        registerQuest(tutorialQuest.addSteps(List.of(
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Talk to the Blacksmith.", "tutorial.blacksmith-talk-to", null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Collect and smelt ores, then bring 1 Bronze Dagger to Blacksmith.", "tutorial.blacksmith-craft-items", null),

                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Talk to the Lumberjack.", "tutorial.lumberjack-talk-to", null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Cut trees and bring 3 Logs to Lumberjack.", "tutorial.lumberjack-cut-trees", null),

                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Talk to the Survival Guide.", "tutorial.survival-talk-to", null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Kill 3 goblins and bring 3 bones back to the Survival Guide.", "tutorial.survival-kill-mobs", null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Use fishing net to catch fish and bring 3 Raw Herrings to the Survival Guide.", "tutorial.survival-catch-fish", null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Successfully cook 1 Raw Herring and bring 1 Cooked Herring to the Survival Guide.", "tutorial.survival-cook-fish", null)
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
