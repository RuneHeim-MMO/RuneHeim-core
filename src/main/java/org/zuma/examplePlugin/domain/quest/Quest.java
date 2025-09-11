package org.zuma.examplePlugin.domain.quest;

import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.QuestCompletedEffect;
import org.zuma.examplePlugin.domain.effect.effects.QuestStartEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private final String id, name;
    private final List<AbstractQuestStep> steps;

    public Quest(String id, String name) {
        this.id = id;
        this.name = name;
        this.steps = new ArrayList<>();
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

    public void startForPlayer(RunePlayer player) throws IllegalStateException {
        if (player.getCompletedQuestIds().contains(id)) {
            throw new IllegalStateException("This quest is already completed.");
        }

        if (player.getQuestSteps().containsKey(id)) {
            throw new IllegalStateException("This quest is already started.");
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
}
