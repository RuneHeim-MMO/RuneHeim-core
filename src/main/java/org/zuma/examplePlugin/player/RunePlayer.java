package org.zuma.examplePlugin.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RunePlayer {
    private final UUID uuid;

    public RunePlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}
