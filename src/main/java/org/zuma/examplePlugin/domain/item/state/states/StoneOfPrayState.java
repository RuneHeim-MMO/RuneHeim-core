package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.StoneOfPrayMeta;
import org.zuma.examplePlugin.domain.prayer.Prayer;

import java.util.List;

public class StoneOfPrayState extends AbstractState {
    private List<Prayer> preset;

    public StoneOfPrayState(StoneOfPrayMeta meta) {
        super(StateType.STONE_OF_PRAY_STATE);
        this.preset = meta.preset();
    }

    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new StoneOfPrayMeta(this.preset)), 1));
    }

    @Override
    public AbstractState clone() {
        return new StoneOfPrayState(new StoneOfPrayMeta(this.preset));
    }

    public List<Prayer> getPreset() {
        return preset;
    }

    public void setPreset(List<Prayer> preset) {
        this.preset = preset;
    }
}
