package org.zuma.examplePlugin.domain.cooldown;

import org.bukkit.entity.Player;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownHandler {
    private final Map<UUID, Long> cooldownDelays = new HashMap<>();

    public boolean isCooledDown(RunePlayer player) {
        return this.isCooledDown(player.asBukkitPlayer());
    }

    public boolean isCooledDown(Player player) {
        UUID uuid = player.getUniqueId();
        Long cooldownExpiryTime = cooldownDelays.get(uuid);

        if (cooldownExpiryTime == null) {
            return false;
        }

        return cooldownExpiryTime > System.currentTimeMillis();
    }

    public void startCooldown(RunePlayer player, Long delay) {
        this.startCooldown(player.asBukkitPlayer(), delay);
    }

    public void startCooldown(Player player, Long delay) {
        cooldownDelays.put(player.getUniqueId(), System.currentTimeMillis() + delay);
    }

}
