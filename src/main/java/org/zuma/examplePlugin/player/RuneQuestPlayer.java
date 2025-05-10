package org.zuma.examplePlugin.player;

import java.util.UUID;

public class RuneQuestPlayer {
    private final UUID uuid;

    public RuneQuestPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

}
