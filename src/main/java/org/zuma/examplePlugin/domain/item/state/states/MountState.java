package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
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


public class MountState extends AbstractState {
    private static final int BASE_XP = 500;
    private static final double XP_POWER_AMOUNT = 1.15;
    @Nullable
    private String name;
    private int level;
    private double xp;
    private Function<Integer, Void> onLevelUp;

    public MountState(MountStateMeta meta) {
        super(StateType.MOUNT_STATE);
    }

    // If entity was ridden within last tick (tick happens every 1 sec)
    public void tick() {
        System.out.println("state tick");

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
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new MountStateMeta(level, xp)), 1));
    }

    @Override
    public AbstractState clone() {
        return new MountState(new MountStateMeta(level, xp));
    }

    public void setOnLevelUp(Function<Integer, Void> onLevelUp) {
        this.onLevelUp = onLevelUp;
    }
}
