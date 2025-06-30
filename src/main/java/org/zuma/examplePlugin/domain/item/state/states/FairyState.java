package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;

import java.util.List;

public class FairyState extends AbstractState {
    private static final int LEVEL_CAP = 20;
    private static final int BASE_XP = 500;
    private static final double XP_POWER_AMOUNT = 1.25; // 500\times (1.25)^(20)

    private final List<Integer> upgradeIds;
    private int stamina; // hunger 0-100
    private double experience;
    private boolean canConsumeFruit;

    public FairyState(List<Integer> upgradeIds, int stamina, double experience, boolean canConsumeFruit) {
        super(StateType.FAIRY_STATE);
        this.upgradeIds = upgradeIds;

        this.stamina = stamina;
        this.experience = experience;
        this.canConsumeFruit = canConsumeFruit;
    }

    public void tick() {
        if (stamina < 0) {
            return;
        }

        stamina -= 1;

        // In lore should be smth if is max level, then should remove hte xp bar
        if (getLevel() >= LEVEL_CAP) {
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
        if (level >= LEVEL_CAP) {
            return 0;
        }

        return Math.toIntExact(Math.round(BASE_XP * Math.pow(XP_POWER_AMOUNT, level)));
    }

    public int getLevel() {
        return Math.min(LEVEL_CAP, upgradeIds.size());
    }

    public boolean isMaxed() {
        return getLevel() >= LEVEL_CAP;
    }


    public boolean canConsumeFruit() {
        return canConsumeFruit;
    }

    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return null;
    }
}
