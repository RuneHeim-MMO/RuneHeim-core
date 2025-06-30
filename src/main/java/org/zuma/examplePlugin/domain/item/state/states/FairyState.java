package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.FairyStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.GearGemSocketsStateMeta;

import java.util.List;

public class FairyState extends AbstractState {
    private static final int BASE_XP = 500;
    private static final double XP_POWER_AMOUNT = 1.25; // 500\times (1.25)^(20)
    private static final int LEVELS_PER_GENERATION = 20;

    private final int maxLevel, generation; // depends on generation
    private final List<Integer> upgradeIds;
    private int stamina; // hunger 0-100
    private double experience;
    private boolean canConsumeFruit;

    public FairyState(FairyStateMeta meta) {
        super(StateType.FAIRY_STATE);
        this.maxLevel = meta.generation() * LEVELS_PER_GENERATION;
        this.generation = meta.generation();
        this.upgradeIds = meta.upgradeIds();

        this.stamina = meta.stamina();
        this.experience = meta.experience();
        this.canConsumeFruit = meta.canConsumeFruit();
    }

    public void tick() {
        if (stamina < 0) {
            return;
        }

        stamina -= 1;

        // In lore should be smth if is max level, then should remove hte xp bar
        if (getLevel() >= maxLevel) {
            experience = 0;
            canConsumeFruit = false;
            return;
        }

        int nextLevelXP = getXPForLevel(getLevel() + 1);

        if (!(experience >= nextLevelXP)) {
            experience += 2.5;
            canConsumeFruit = false;
            return;
        }

        // Set XP max xp for this level.
        experience = nextLevelXP;
        canConsumeFruit = true;

    }

    public int getXPForLevel(int level) {
        if (level >= maxLevel) {
            return 0;
        }

        return Math.toIntExact(Math.round(BASE_XP * Math.pow(XP_POWER_AMOUNT, level)));
    }

    public int getLevel() {
        return Math.min(maxLevel, upgradeIds.size());
    }

    public List<Integer> getUpgradeIds() {
        return this.upgradeIds;
    }

    public boolean isMaxed() {
        return getLevel() >= maxLevel;
    }


    public boolean canConsumeFruit() {
        return canConsumeFruit;
    }

    public int getStamina() {
        return this.stamina;
    }

    public double getExperience() {
        return this.experience;
    }

    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new FairyStateMeta(this.upgradeIds, this.stamina, this.experience, this.canConsumeFruit, this.getGeneration())), 1));
    }

    public int getGeneration() {
        return generation;
    }
}
