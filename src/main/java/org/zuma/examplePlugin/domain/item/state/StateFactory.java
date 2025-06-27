package org.zuma.examplePlugin.domain.item.state;

import com.google.gson.Gson;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.item.state.states.MultiConsumableState;

import javax.annotation.Nullable;

public class StateFactory {

    @Nullable
    public AbstractState createState(AbstractStateMeta abstractStateMeta) {
        Gson gson = new Gson();

        return switch (abstractStateMeta.type()) {
            case MULTI_CONSUMABLE -> gson.fromJson(abstractStateMeta.json(), MultiConsumableState.class);
            case GEAR_GEM_SOCKETS -> gson.fromJson(abstractStateMeta.json(), GearGemSocketsState.class);
            default -> null;
        };
    }
}
