package org.zuma.examplePlugin.domain.mob;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class MobHandler {
    private final MobRegistry registry;

    public MobHandler(MobRegistry registry) {
        this.registry = registry;
    }

    // Clears all mobs with MOB_ID data keys.
    public void cleanup() {
        for (World world : Bukkit.getWorlds()) {
            world.getEntities().forEach(entity -> {
                NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.MOB_ID.asString());
                Integer value = entity.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);

                if (value != null) {
                    entity.remove();
                }

            });
        }
    }

    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onPlayerInteractWithEntity(event, player);
    }

    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onPlayerAttackEntity(event, player);
    }

    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, AbstractMob mob, RunePlayer player) {
        mob.onEntityAttackPlayer(event, player);
    }

    public void onEntityDamaged(EntityDamageEvent event, AbstractMob mob) {
        mob.onEntityDamaged(event);
    }
}
