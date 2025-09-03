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
import org.zuma.examplePlugin.domain.item.state.meta.v1.MountStateMeta;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class MountState extends AbstractState {
    private static final int BASE_XP = 500;
    private static final double XP_POWER_AMOUNT = 1.15;
    private static final int MAX_LEVEL = 10;

    @Nullable
    private String name;
    private int level;
    private double xp;

    public MountState(MountStateMeta meta) {
        super(StateType.MOUNT_STATE);
    }

    public int getLevel() {
        return Math.min(MAX_LEVEL, level);
    }

    public boolean isMaxed() {
        return getLevel() >= MAX_LEVEL;
    }

    public int getXPForLevel(int level) {
        if (level > MAX_LEVEL) {
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
}
