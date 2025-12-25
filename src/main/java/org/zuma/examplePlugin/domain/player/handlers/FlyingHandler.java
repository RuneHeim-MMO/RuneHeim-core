package org.zuma.examplePlugin.domain.player.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import java.util.*;

public class FlyingHandler implements Listener {
    private static final long COOLDOWN_MS = 500;
    private final RunePlayerManager manager;
    private final Map<UUID, Long> glideCooldowns = new HashMap<>();
    private final Set<UUID> recentlyDamaged = new HashSet<>();


    public FlyingHandler(RunePlayerManager manager) {
        this.manager = manager;
        RuneQuestPlugin.getInstance().getServer().getPluginManager().registerEvents(this, RuneQuestPlugin.getInstance());
    }


    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onFlyIntoWallDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
            event.setCancelled(true);
            recentlyDamaged.add(player.getUniqueId());
            player.setGliding(false);
        }
    }

    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.isGliding()) return;

        if (recentlyDamaged.contains(player.getUniqueId())) {
            recentlyDamaged.remove(player.getUniqueId());
            return;
        }

        RunePlayer runePlayer = manager.getRunePlayer(event.getEntity().getUniqueId());
        if (runePlayer == null) return;

        Modifier flyingModifier = runePlayer.getModifiers().get(ModifierType.FLYING_SPEED);
        if (flyingModifier == null) return;

        event.setCancelled(true);
    }

    public void startLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {

            for (RunePlayer runePlayer : manager.getOnlinePlayers()) {
                Player p = runePlayer.asBukkitPlayer();
                Modifier flyingModifier = runePlayer.getModifiers().get(ModifierType.FLYING_SPEED);

                if (flyingModifier == null) continue;

                if (!p.isGliding() && p.getVelocity().getY() < -0.1 && p.isSneaking()) {
                    long now = System.currentTimeMillis();
                    long lastToggle = glideCooldowns.getOrDefault(p.getUniqueId(), 0L);

                    if (now - lastToggle < COOLDOWN_MS) continue;
                    p.setGliding(true);
                    glideCooldowns.put(p.getUniqueId(), now);
                    continue;
                }

                if (!p.isGliding()) continue;


                Vector velocity = p.getVelocity();
                Vector boost = p.getLocation().getDirection().multiply(0.5);

                velocity.add(boost);

                double maxSpeed = flyingModifier.amount() / 100;
                if (velocity.length() > maxSpeed) {
                    velocity = velocity.normalize().multiply(maxSpeed); // clamp total speed
                }

                if (velocity.getY() > 0.15) {
                    velocity.setY(0.15);
                }

                p.setVelocity(velocity);
            }

        }, 20L, 10L);


    }
}
