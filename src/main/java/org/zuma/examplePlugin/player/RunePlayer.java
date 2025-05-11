package org.zuma.examplePlugin.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.modifier.Modifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RunePlayer {
    private final UUID uuid;
    private final List<Modifier> gearModifiers = new ArrayList<>();
    private final List<Modifier> modifiers = new ArrayList<>();

    public RunePlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void setGearModifiers(List<Modifier> newModifiers) {
        gearModifiers.clear();
        gearModifiers.addAll(newModifiers);

        System.out.println("Recalculated modifiers " + newModifiers);
    }

    public List<Modifier> getActiveModifiers() {
        return modifiers;
    }

}
