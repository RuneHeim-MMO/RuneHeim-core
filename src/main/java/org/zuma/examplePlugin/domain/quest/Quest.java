package org.zuma.examplePlugin.domain.quest;

import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.QuestCompletedEffect;
import org.zuma.examplePlugin.domain.effect.effects.QuestStartEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.constants.QuestDifficulty;
import org.zuma.examplePlugin.domain.quest.constants.QuestStartFailReason;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Quest implements SkillRestrictable {
    private final String id, name;
    private final List<AbstractQuestStep> steps;
    private final QuestDifficulty difficulty;

    // Starting conditions
    private List<SkillRequirement> requirements;
    private List<String> requiredCompletedQuests;

    public Quest(String id, String name, QuestDifficulty difficulty) {
        this.id = id;
        this.name = name;
        this.steps = new ArrayList<>();
        this.difficulty = difficulty;
        this.requirements = new ArrayList<>();
        this.requiredCompletedQuests = new ArrayList<>();
    }

    public Quest setRequiredCompletedQuests(List<String> questIds) {
        this.requiredCompletedQuests = questIds;
        return this;
    }

    public Quest setRequirements(List<SkillRequirement> requirements) {
        this.requirements = requirements;
        return this;
    }

    public Quest addSteps(List<AbstractQuestStep> steps) {
        for (AbstractQuestStep step : steps) {
            int stepIndex = this.steps.size();
            step.setId(stepIndex);
            this.steps.add(step);
        }
        return this;
    }

    public String getId() {
        return id;
    }

    public List<AbstractQuestStep> getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public QuestStartFailReason getQuestStartFailReason(RunePlayer player) {
        if (!player.meetsRequirements(this)) {
            return QuestStartFailReason.DOES_NOT_MEET_SKILL_REQUIREMENTS;
        }

        // O(1) lookup in hashset
        if (!new HashSet<>(player.getCompletedQuestIds()).containsAll(this.requiredCompletedQuests)) {
            return QuestStartFailReason.DOES_NOT_MEET_QUEST_REQUIREMENTS;
        }


        if (player.getCompletedQuestIds().contains(id)) {
            return QuestStartFailReason.ALREADY_COMPLETED;
        }

        if (player.getQuestSteps().containsKey(id)) {
            return QuestStartFailReason.ALREADY_STARTED;
        }

        return null;
    }

    public void startForPlayer(RunePlayer player) throws IllegalStateException {
        QuestStartFailReason failReason = getQuestStartFailReason(player);

        if (failReason != null) {
            throw new IllegalStateException(failReason.toString());
        }

        if (steps.getFirst() == null) {
            throw new IllegalStateException("Cannot start quest. First step is null.");
        }

        EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new QuestStartEffect(player, this));
        player.getQuestSteps().put(id, steps.getFirst().cloneForPlayer(player.getUUID()));
    }

    public void handleCompletion(RunePlayer player) {
        player.getCompletedQuestIds().add(id);
        player.getQuestSteps().remove(id);
        EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new QuestCompletedEffect(player, this));
    }

    public QuestDifficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public List<SkillRequirement> getRequirements() {
        return requirements;
    }
}
