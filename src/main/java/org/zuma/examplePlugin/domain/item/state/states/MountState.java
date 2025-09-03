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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class MountState extends AbstractState {
    private int level;

    public MountState(MountStateMeta meta) {
        super(StateType.MOUNT_STATE);
        this.level = meta.level();
    }


    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new MountStateMeta(level)), 1));
    }

    @Override
    public AbstractState clone() {
        return new MountState(new MountStateMeta(level));
    }
}
