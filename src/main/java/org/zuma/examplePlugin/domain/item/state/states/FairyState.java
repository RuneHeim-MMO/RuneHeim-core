package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFruit;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.FairyStateMeta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/*

for(let lvl = 1; lvl <= 30; lvl++){
  let totalxp = 0;

for(let i = 1; i < lvl+1; i++){
  totalxp = totalxp + (500 * Math.pow(1.15,i))
}

console.log(Math.round(totalxp) + " for lvl "+  lvl +" hrs: " + totalxp / 500)
}

575 for lvl 1 hrs: 1.15
1236 for lvl 2 hrs: 2.4725
1997 for lvl 3 hrs: 3.9933749999999995
2871 for lvl 4 hrs: 5.742381249999998
3877 for lvl 5 hrs: 7.753738437499998
5033 for lvl 6 hrs: 10.066799203124997
6363 for lvl 7 hrs: 12.726819083593746
7893 for lvl 8 hrs: 15.785841946132807
9652 for lvl 9 hrs: 19.303718238052728
11675 for lvl 10 hrs: 23.349275973760633
14001 for lvl 11 hrs: 28.001667369824727
16676 for lvl 12 hrs: 33.351917475298436
19752 for lvl 13 hrs: 39.50470509659319
23290 for lvl 14 hrs: 46.58041086108217
27359 for lvl 15 hrs: 54.717472490244496
32038 for lvl 16 hrs: 64.07509336378116
37418 for lvl 17 hrs: 74.83635736834833
43606 for lvl 18 hrs: 87.21181097360058
50722 for lvl 19 hrs: 101.44358261964065
58905 for lvl 20 hrs: 117.81012001258675
68316 for lvl 21 hrs: 136.63163801447473
79138 for lvl 22 hrs: 158.27638371664594
91584 for lvl 23 hrs: 183.16784127414283
105897 for lvl 24 hrs: 211.79301746526423
122356 for lvl 25 hrs: 244.71197008505382
141284 for lvl 26 hrs: 282.5687655978119
163052 for lvl 27 hrs: 326.1040804374837
188085 for lvl 28 hrs: 376.1696925031062
216873 for lvl 29 hrs: 433.7451463785721
249978 for lvl 30 hrs: 499.95691833535784

 */

public class FairyState extends AbstractState {
    private static final int BASE_XP = 500;
    private static final double XP_POWER_AMOUNT = 1.15; // 500\times (1.25)^(20)
    private static final int MAX_LVL_PER_GENERATION = 10;

    // Every 10 sec
    private static final double STAMINA_DECREASE_PER_TICK = 100.0 / 360.0; // -100 stamina per hour;
    private static final double EXPERIENCE_GAIN_PER_TICK = 500.0 / 360.0; // +500 per hour

    private final int maxLevel, generation; // depends on generation
    private final List<Integer> upgradeIds;
    private double stamina; // hunger 0-100
    private double experience;
    private boolean canConsumeFruit;

    public FairyState(FairyStateMeta meta) {
        super(StateType.FAIRY_STATE);
        this.maxLevel = meta.generation() * MAX_LVL_PER_GENERATION;
        this.generation = meta.generation();
        this.upgradeIds = meta.upgradeIds();

        this.stamina = meta.stamina();
        this.experience = meta.experience();
        this.canConsumeFruit = meta.canConsumeFruit();
    }


    // Every 10 sec
    public void tick() {
        if (stamina < STAMINA_DECREASE_PER_TICK) {
            stamina = 0;
            return;
        }

        stamina -= STAMINA_DECREASE_PER_TICK;

        if (this.canConsumeFruit) {
            return;
        }

        if (getLevel() >= maxLevel) {
            experience = 0;
            canConsumeFruit = false;
            return;
        }

        int nextLevelXP = getXPForLevel(getLevel() + 1);

        if (!(experience >= nextLevelXP)) {
            experience += EXPERIENCE_GAIN_PER_TICK;
            canConsumeFruit = false;

            if (experience >= nextLevelXP) {
                // Set XP max xp for this level.
                experience = nextLevelXP;
                canConsumeFruit = true;
            }
        }
    }

    public void consumeFruit(FairyFruit fruit) {
        if (!canConsumeFruit) {
            throw new IllegalStateException("Fairy is not ready to consume upgrades.");
        }

        this.experience = 0;
        this.canConsumeFruit = false;
        this.upgradeIds.add(fruit.id);
    }

    public void feedFairy(double amount) {
        if (stamina + amount > 100) {
            stamina = 100;
            return;
        }

        stamina += amount;
    }

    public int getXPForLevel(int level) {
        if (level > maxLevel) {
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

    public double getStamina() {
        return new BigDecimal(this.stamina).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    public double getExperience() {
        return Math.toIntExact(Math.round(this.experience));
    }

    public int getGeneration() {
        return generation;
    }

    public List<FairyFruit> getInsertedUpgrades() {
        List<FairyFruit> list = new ArrayList<>();

        upgradeIds.forEach(id -> {
            AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(id);

            if (item == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot retrieve fairy upgrade item: State contains invalid item id " + id + "!");
                return;
            }

            if (!(item instanceof FairyFruit fruit)) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot retrieve fairy upgrade item: State inserted upgrade ids contains id " + id + ", but " + item.displayName + " is not a fairy fruit!");
                return;
            }

            list.add(fruit);
        });

        return list;
    }

    public void setMaxExp() {
        this.experience = getXPForLevel(getLevel() + 1);
        this.canConsumeFruit = true;
    }

    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new FairyStateMeta(this.upgradeIds, this.stamina, this.experience, this.canConsumeFruit, this.getGeneration())), 1));
    }

    @Override
    public AbstractState clone() {
        return new FairyState(new FairyStateMeta(this.upgradeIds, this.stamina, this.experience, this.canConsumeFruit, this.generation));
    }
}
