package org.zuma.examplePlugin.domain.quest;

import com.google.gson.Gson;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.quest.constants.QuestDifficulty;
import org.zuma.examplePlugin.domain.quest.impl.CollectionStep;
import org.zuma.examplePlugin.domain.quest.impl.DialogStep;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestRegistry {
    private final HashMap<String, Quest> quests; // id:quest

    public QuestRegistry() {
        this.quests = new HashMap<>();

        Quest sheepShearer = new Quest("sheep-shearer", "Sheep Shearer", QuestDifficulty.EASY);
        registerQuest(sheepShearer.addSteps(List.of(
                new CollectionStep(RuneQuestPlugin.getInstance(), sheepShearer, "Collect §620 wool§f and spin them into balls of wool.", "sheep-shearer.wool-collect", List.of(new ItemRecord(ItemId.BALL_OF_WOOL, 20)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), sheepShearer, "Bring §620 balls of wool§f to §6Shepherd.", "sheep-shearer.wool-collected", null)
        )));


        Quest tutorialQuest = new Quest("tutorial", "Tutorial", QuestDifficulty.EASY)
                .setRewards(new QuestReward(List.of(
                        new ItemRecord(ItemId.BRONZE_SWORD, 1),
                        new ItemRecord(ItemId.BRONZE_PICKAXE, 1),
                        new ItemRecord(ItemId.SHORT_BOW, 1),
                        new ItemRecord(ItemId.BRONZE_ARROW, 128),
                        new ItemRecord(ItemId.COIF, 1),
                        new ItemRecord(ItemId.FISHING_NET, 1),
                        new ItemRecord(ItemId.COOKED_HERRING, 20),
                        new ItemRecord(ItemId.GENIE_XP_LAMP, 1)
                ), null, 500));

        registerQuest(tutorialQuest.addSteps(List.of(
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Talk to the §6Blacksmith.", "tutorial.blacksmith-talk-to", null),
                new CollectionStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Collect §61 Copper Ore§f and §61 Tin Ore.", "tutorial.blacksmith-collect-ores", List.of(new ItemRecord(ItemId.COPPER_ORE, 1), new ItemRecord(ItemId.TIN_ORE, 1)), null),
                new CollectionStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Smelt ores into §61 Bronze Bar.", "tutorial.blacksmith-smelt-bar", List.of(new ItemRecord(ItemId.BRONZE_BAR, 1)), null),
                new CollectionStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Use your hammer on an anvil and craft §61 Bronze Dagger.", "tutorial.blacksmith-smith-dagger", List.of(new ItemRecord(ItemId.BRONZE_DAGGER, 1)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Bring §61 Bronze Dagger§f to the §6Blacksmith.", "tutorial.blacksmith-bring-dagger", null),

                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Talk to the §6Lumberjack.", "tutorial.lumberjack-talk-to", null),
                new CollectionStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Cut trees and collect §63 Logs.", "tutorial.lumberjack-collect-logs", List.of(new ItemRecord(ItemId.LOG, 3)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Bring §63 Logs§f to the §6Lumberjack.", "tutorial.lumberjack-bring-logs", null),

                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Talk to the §6Survival Guide.", "tutorial.survival-talk-to", null),
                new CollectionStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Kill §63 Goblins§f and collect §63 Bones.", "tutorial.survival-collect-bones", List.of(new ItemRecord(ItemId.BONES, 3)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Bring §63 bones§f back to the §6Survival Guide.", "tutorial.survival-bring-bones", null),

                new CollectionStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Use fishing net to catch §63 Raw Herrings.", "tutorial.survival-collect-herrings", List.of(new ItemRecord(ItemId.RAW_HERRING, 3)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Bring §63 Raw Herrings§f to the §6Survival Guide.", "tutorial.survival-bring-herrings", null),
                new DialogStep(RuneQuestPlugin.getInstance(), tutorialQuest, "Bring §61 Cooked Herring§f to the §6Survival Guide.", "tutorial.survival-cook-fish", null)
        )));


        Quest blacksmithsRightHandI = new Quest("blacksmiths-right-hand-i", "Blacksmith's Right Hand", QuestDifficulty.EASY)
                .setRewards(new QuestReward(null, Map.of(SkillType.SMITHING, 1000.0), 800));

        registerQuest(blacksmithsRightHandI.addSteps(List.of(
                new CollectionStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Collect §616x Copper Ore§f and §616x Tin Ore.", "blacksmith-right-hand-i.collect-ores", List.of(new ItemRecord(ItemId.COPPER_ORE, 16), new ItemRecord(ItemId.TIN_ORE, 16)), null),
                new CollectionStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Smelt ores into §616x Bronze Bars.", "blacksmith-right-hand-i.smelt-ores", List.of(new ItemRecord(ItemId.BRONZE_BAR, 16)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Bring §616x Bronze Bars§f to the §6Blacksmith.", "blacksmith-right-hand-i.bring-ores", null),
                new DialogStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, null, "blacksmith-right-hand-i.gave-in-res", null),
                new CollectionStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Acquire §61x Uncut Sapphire.", "blacksmith-right-hand-i.acquire-sapphire", List.of(new ItemRecord(ItemId.UNCUT_SAPPHIRE, 1)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Bring §61x Uncut Sapphire§f to the §6Blacksmith.", "blacksmith-right-hand-i.bring-sapphire", null)
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
