package org.zuma.examplePlugin.domain.quest;

import org.bukkit.Location;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.quest.constants.QuestDifficulty;
import org.zuma.examplePlugin.domain.quest.constants.QuestId;
import org.zuma.examplePlugin.domain.quest.impl.*;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestRegistry {
    private final HashMap<String, Quest> quests; // id:quest

    public QuestRegistry() {
        this.quests = new HashMap<>();

        Quest sheepShearer = new Quest(QuestId.SHEEP_SHEARER.getId(), "Sheep Shearer", QuestDifficulty.EASY, false, new Quest.Coordinates(45, 68, -162));
        registerQuest(sheepShearer.addSteps(List.of(
                new CollectionStep(RuneQuestPlugin.getInstance(), sheepShearer, "Collect §620 wool§f and spin them into balls of wool.", "sheep-shearer.wool-collect", List.of(new ItemRecord(ItemId.BALL_OF_WOOL, 20)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), sheepShearer, "Bring §620 balls of wool§f to §6Shepherd.", "sheep-shearer.wool-collected", null)
        )));


        Quest tutorialQuest = new Quest(QuestId.TUTORIAL.getId(), "Tutorial", QuestDifficulty.EASY, false, null)
                .setRewards(new QuestReward(List.of(
                        new ItemRecord(ItemId.BRONZE_SWORD, 1),
                        new ItemRecord(ItemId.BRONZE_PICKAXE, 1),
                        new ItemRecord(ItemId.BRONZE_AXE, 1),
                        new ItemRecord(ItemId.SHORT_BOW, 1),
                        new ItemRecord(ItemId.BRONZE_ARROW, 128),
                        new ItemRecord(ItemId.COIF, 1),
                        new ItemRecord(ItemId.FISHING_NET, 1),
                        new ItemRecord(ItemId.COOKED_HERRING, 20),
                        new ItemRecord(ItemId.GENIE_XP_LAMP, 1)
                ), null, 500))
                .setCountinationQuestId(QuestId.WELCOME_TO_DRAKKAR_I.getId());

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


        Quest blacksmithsRightHandI = new Quest(QuestId.BLACKSMITHS_RIGHT_HAND_I.getId(), "Blacksmith's Right Hand", QuestDifficulty.EASY, false, new Quest.Coordinates(209, 73, -100))
                .setRewards(new QuestReward(null, Map.of(SkillType.SMITHING, 1000.0), 800));

        registerQuest(blacksmithsRightHandI.addSteps(List.of(
                new CollectionStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Collect §616x Copper Ore§f and §616x Tin Ore.", "blacksmith-right-hand-i.collect-ores", List.of(new ItemRecord(ItemId.COPPER_ORE, 16), new ItemRecord(ItemId.TIN_ORE, 16)), null),
                new CollectionStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Smelt ores into §616x Bronze Bars.", "blacksmith-right-hand-i.smelt-ores", List.of(new ItemRecord(ItemId.BRONZE_BAR, 16)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Bring §616x Bronze Bars§f to the §6Blacksmith.", "blacksmith-right-hand-i.bring-ores", null),
                new DialogStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, null, "blacksmith-right-hand-i.gave-in-res", null),
                new CollectionStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Acquire §61x Uncut Sapphire.", "blacksmith-right-hand-i.acquire-sapphire", List.of(new ItemRecord(ItemId.UNCUT_SAPPHIRE, 1)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), blacksmithsRightHandI, "Bring §61x Uncut Sapphire§f to the §6Blacksmith.", "blacksmith-right-hand-i.bring-sapphire", null)
        )));

        Quest saharijinPVPArenaIntroduction = new Quest(QuestId.SAHARIJIN_ARENA_INTRO.getId(), "Saharijin Arena Intro", QuestDifficulty.EASY, true, null);
        registerQuest(saharijinPVPArenaIntroduction); // No steps, just used for tracking if player has talked to the NPC.


        Quest theSickCamels = new Quest(QuestId.THE_SICK_CAMELS.getId(), "The Sick Camels", QuestDifficulty.MEDIUM, false, new Quest.Coordinates(-1000, 73, -560))
                .setRewards(new QuestReward(null, null, 2500))
                .setRequirements(List.of(new SkillRequirement(SkillType.ATTACK, 50), new SkillRequirement(SkillType.STRENGTH, 50), new SkillRequirement(SkillType.DEFENCE, 50)));

        registerQuest(theSickCamels.addSteps(List.of(
                new DialogStep(RuneQuestPlugin.getInstance(), theSickCamels, "Talk to the §6Shamira§f in Zarali", "the-sick-camels.talk-to-shamira", null),
                new CollectionStep(RuneQuestPlugin.getInstance(), theSickCamels, "Collect §615x Frog eyes.", "the-sick-camels.collect-frog-eyes", List.of(new ItemRecord(ItemId.FROG_EYE_THE_SICK_CAMELS_QUEST, 15)), null),
                new CollectionStep(RuneQuestPlugin.getInstance(), theSickCamels, "Collect §68x Wolf fangs.", "the-sick-camels.collect-wolf-fangs", List.of(new ItemRecord(ItemId.WOLF_FANG_THE_SICK_CAMELS_QUEST, 8)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), theSickCamels, "Bring ingredients back to §6Shamira§f in Zarali", "the-sick-camels.bring-ingredients", null),
                new DialogStep(RuneQuestPlugin.getInstance(), theSickCamels, "Bring camels cure to §6Rafiq§f in Saharijin", "the-sick-camels.bring-cure", null)
        )));


        Quest promiseInABox = new Quest(QuestId.PROMISE_IN_A_BOX.getId(), "Promise in a box", QuestDifficulty.EASY, false, new Quest.Coordinates(155, 73, -50))
                .setRewards(new QuestReward(null, null, 750));

        registerQuest(promiseInABox.addSteps(List.of(
                new DialogStep(RuneQuestPlugin.getInstance(), promiseInABox, "Deliver §61x Engagement Ring§f to the §6Fishing Merchant§f in §6Brineport.", "promise-in-a-box.deliver", null)
        )));

        /* Talk to the town major, welcome the player
            Tell him to talk to shopkeeper, he will explain that player can sell stuff to him or buy
            Tell him to talk to the blacksmith, explain that he can make weapons and armor
            Tell him to go to the bank, explain how bank works, but also tell him to go to the back office to help with a rat problem
            Kill rats, report back to the banker, then report back to the mayor
         */

        Quest welcomeToDrakkarI = new Quest(QuestId.WELCOME_TO_DRAKKAR_I.getId(), "Welcome to Drakkar I", QuestDifficulty.EASY, false, null)
                .setRewards(new QuestReward(List.of(new ItemRecord(ItemId.IRON_BATTLEAXE, 1), new ItemRecord(ItemId.BRONZE_CHAINBODY, 1)), null, 500))
                .setCountinationQuestId(QuestId.WELCOME_TO_DRAKKAR_II.getId());

        registerQuest(welcomeToDrakkarI.addSteps(List.of(
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarI, "Talk to the §6Town Mayor §fin §6Drakkar.", "welcome-to-drakkar.talk.to.mayor", null),
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarI, "Talk to the §6Shopkeeper §fin §6Drakkar.", "welcome-to-drakkar.talk.to.shopkeeper", null),
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarI, "Talk to the §6Blacksmith §fin §6Drakkar.", "welcome-to-drakkar.talk.to.blacksmith", null),
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarI, "Talk to the §6Banker §fin §6Drakkar.", "welcome-to-drakkar.talk.to.banker", null),
                new EnterTeleportDoorStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarI, "Enter §6Back Office§f in the §6Drakkar's Bank.", "welcome-to-drakkar.enter.back-office",
                        "drakkar-bank-back-office", null),
                new KillMobStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarI, "Kill §615x Rats§f in the §6Drakkar's Bank Vault.", "welcome-to-drakkar.kill.rats",
                        List.of(new KillMobStep.KillMobStepEntry(List.of(MobType.RAT_WELCOME_TO_DRAKKAR_BASE_1.getId()), 15, "Drakkar Vault Rats")), null),
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarI, "Report back to the §6Banker §fin §6Drakkar.", "welcome-to-drakkar.report.to.banker", null),
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarI, "Talk to the §6Town Mayor §fin §6Drakkar.", "welcome-to-drakkar.talk.to.mayor.finish", null)
        )));

        Quest welcomeToDrakkarII = new Quest(QuestId.WELCOME_TO_DRAKKAR_II.getId(), "Welcome to Drakkar II", QuestDifficulty.EASY, false, null)
                .setRewards(new QuestReward(List.of(new ItemRecord(ItemId.COOKED_CHICKEN, 20), new ItemRecord(ItemId.GENIE_XP_LAMP, 1)), null, 500));

        registerQuest(welcomeToDrakkarII.addSteps(List.of(
                new VisitStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarII, "Visit §6Amber Farm§f at §6(X:244, Z:-270).", "welcome-to-drakkarii.visit.amber_farm", new VisitStep.VisitStepEntry(new Quest.Coordinates(244, 70, -270), 50), null),
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarII, "Talk to the §6Farm Horse Trader.", "welcome-to-drakkarii.talk.to.farmer", null),
                new KillMobStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarII, "Kill §610x Goblins§f.", "welcome-to-drakkarii.kill.goblins",
                        List.of(new KillMobStep.KillMobStepEntry(List.of(MobType.GOBLIN_1_BASE_1.getId(), MobType.GOBLIN_2_BASE_1.getId(), MobType.GOBLIN_3_BASE_1.getId(), MobType.GOBLIN_4_BASE_1.getId(),
                                MobType.GOBLIN_5_BASE_1.getId(), MobType.GOBLIN_1_BASE_4.getId(), MobType.GOBLIN_2_BASE_4.getId(), MobType.GOBLIN_3_BASE_4.getId(), MobType.GOBLIN_4_BASE_4.getId(), MobType.GOBLIN_5_BASE_4.getId()), 10, "Goblins")), null),
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarII, "Report back to the §6Farm Horse Trader.", "welcome-to-drakkarii.report.to.farmer", null),
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarII, "Buy a §6Brown Farm Horse§f from §6Farm Horse Trader.", "welcome-to-drakkarii.buy.horse", null),
                new DialogStep(RuneQuestPlugin.getInstance(), welcomeToDrakkarII, "Talk to the §6Town Mayor §fin §6Drakkar.", "welcome-to-drakkarii.talk.to.mayor.finish", null)
        )));

        Quest smeltersAssistant = new Quest(QuestId.HOT_MESS_AT_THE_SMELTERY.getId(), "Hot Mess At The Smeltery", QuestDifficulty.MEDIUM, false, null)
                .setRequirements(List.of(new SkillRequirement(SkillType.MINING, 30)))
                .setRewards(new QuestReward(null, Map.of(SkillType.SMITHING, 840.0, SkillType.MINING, 1500.0), 1000));

        registerQuest(smeltersAssistant.addSteps(List.of(
                new CollectionStep(RuneQuestPlugin.getInstance(), smeltersAssistant, "Obtain §620x Purified Coal Ore.", "smeltery-assistant.obtain-coal", List.of(new ItemRecord(ItemId.PURE_COAL_ORE, 20)), null),
                new DialogStep(RuneQuestPlugin.getInstance(), smeltersAssistant, "Bring §620x Purified Coal Ore §fto §6Smeltery Worker §fin §6Drakkar.", "smeltery-assistant.bring-coal", null)
        )));


    }

    @Nullable
    public Quest getQuestById(String name) {
        return quests.get(name);
    }

    public Collection<Quest> getAllQuests() {
        return this.quests.values();
    }

    public void registerQuest(Quest quest) {
        if (quests.containsKey(quest.getId())) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot register quest, quest with name " + quest.getId() + " is already in registry.");
            return;
        }

        quests.put(quest.getId(), quest);
    }

}
