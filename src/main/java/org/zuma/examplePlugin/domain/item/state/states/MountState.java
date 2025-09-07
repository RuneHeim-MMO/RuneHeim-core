package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.Mount;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFruit;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.FairyStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.MountStateMeta;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/*
0) 500xp xp per hour of running: 1.3888888888888888 hrs
1) 575xp xp per hour of running: 1.5972222222222223 hrs
2) 661.2499999999999xp xp per hour of running: 1.8368055555555551 hrs
3) 760.4374999999998xp xp per hour of running: 2.112326388888888 hrs
4) 874.5031249999997xp xp per hour of running: 2.4291753472222215 hrs
5) 1005.6785937499997xp xp per hour of running: 2.7935516493055546 hrs
6) 1156.5303828124995xp xp per hour of running: 3.2125843967013874 hrs
7) 1330.0099402343744xp xp per hour of running: 3.6944720562065956 hrs
8) 1529.5114312695305xp xp per hour of running: 4.248642864637585 hrs
9) 1758.9381459599597xp xp per hour of running: 4.885939294333221 hrs
10) 2022.7788678539534xp xp per hour of running: 5.618830188483204 hrs
11) 2326.195698032046xp xp per hour of running: 6.4616547167556835 hrs
12) 2675.125052736853xp xp per hour of running: 7.430902924269036 hrs
13) 3076.3938106473806xp xp per hour of running: 8.54553836290939 hrs
14) 3537.852882244488xp xp per hour of running: 9.8273691173458 hrs
15) 4068.530814581161xp xp per hour of running: 11.301474484947668 hrs
16) 4678.810436768334xp xp per hour of running: 12.996695657689816 hrs
17) 5380.632002283584xp xp per hour of running: 14.94620000634329 hrs
18) 6187.726802626121xp xp per hour of running: 17.18813000729478 hrs
19) 7115.8858230200385xp xp per hour of running: 19.766349508388995 hrs
142.28275363838978total hrs


const base = 500;
const pertick = 0.1;
  const xpPerHr= pertick * 60 * 60;
  let totalhrs = 0;

for(i = 0; i< 20; i++){
  const xp = base * Math.pow(1.15,i);
  console.log(i + ") " + xp + "xp xp per hour of running: " + xp/xpPerHr + " hrs")
  totalhrs+=xp/xpPerHr;

}

console.log(totalhrs + "total hrs")

 */

public class MountState extends AbstractState {
    private static final int BASE_XP = 500;
    private static final double XP_POWER_AMOUNT = 1.15;
    private static final double EXPERIENCE_GAIN_PER_TICK = 0.1;

    @Nullable
    private String name;
    private int level;
    private double experience, distance;
    private Function<Integer, Void> onLevelUp;

    public MountState(MountStateMeta meta) {
        super(StateType.MOUNT_STATE);
        this.level = meta.level();
        this.experience = meta.xp();
        this.distance = meta.distance();
        this.name = meta.name();
    }

    // If entity was ridden within last tick (tick happens every 1 sec)
    public void tick(Mount mount, double distance) {
        this.distance += distance;

        if (getLevel(mount) >= getMaxLevel(mount)) {
            experience = 0;
            return;
        }

        int nextLevel = level + 1;
        int nextLevelXP = getXPForLevel(mount, nextLevel);

        if (!(experience >= nextLevelXP)) {
            this.experience += EXPERIENCE_GAIN_PER_TICK;

            if (experience >= nextLevelXP) {
                this.level = nextLevel;
                this.experience = 0;

                if (this.onLevelUp != null)
                    this.onLevelUp.apply(nextLevel);
            }
        }

    }

    public double getExperience() {
        return this.experience;
    }

    public int getMaxLevel(Mount mount) {
        return mount.getMaxLevel();
    }

    public int getLevel(Mount mount) {
        return Math.min(getMaxLevel(mount), level);
    }

    public boolean isMaxed(Mount mount) {
        return getLevel(mount) >= getMaxLevel(mount);
    }

    public int getXPForLevel(Mount mount, int level) {
        if (level > getMaxLevel(mount)) {
            return 0;
        }

        return Math.toIntExact(Math.round(BASE_XP * Math.pow(XP_POWER_AMOUNT, level)));
    }


    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new MountStateMeta(this.name, level, experience, distance)), 1));
    }

    @Override
    public AbstractState clone() {
        return new MountState(new MountStateMeta(this.name, level, experience, distance));
    }

    public void setOnLevelUp(Function<Integer, Void> onLevelUp) {
        this.onLevelUp = onLevelUp;
    }

    public double getDistanceCovered() {
        return this.distance;
    }

    public @org.jetbrains.annotations.Nullable String getName() {
        return this.name;
    }

    public void setName(String name, boolean validate) throws IllegalStateException {
        if (validate) {
            if (name.length() < 3 || name.length() > 16) {
                throw new IllegalStateException("Name must be between 3-16 characters long.");
            }

            if (!name.matches("[a-zA-Z0-9]+")) {
                throw new IllegalStateException("Name cannot contain symbols. (a-z, A-Z, 0-9 permitted).");
            }
        }

        this.name = name;
    }
}
