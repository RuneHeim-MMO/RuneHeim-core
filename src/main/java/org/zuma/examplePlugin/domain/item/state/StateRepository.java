package org.zuma.examplePlugin.domain.item.state;

import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;

import java.util.UUID;

public class StateRepository {
    public AbstractStateMeta getByUUID(UUID uuid) {
        System.out.println(uuid.toString() + " - FETCH FROM DB");

        return null;
        // return new AbstractStateMeta(StateType.MULTI_CONSUMABLE, new Gson().toJson(new MultiConsumableState(3, 3)));
    }

    public void create(UUID uuid, StateType type, String json) {
        System.out.println(uuid.toString() + " - NEW ITEM STATE - " + json);
    }

    public void update(UUID uuid, String json) {
        System.out.println(uuid.toString() + " - UPDATED JSON! - " + json);
    }

    public void deleteState(UUID uuid) {
        System.out.println(uuid.toString() + " - DELETED STATE");
    }
}
