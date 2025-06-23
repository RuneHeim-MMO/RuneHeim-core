package org.zuma.examplePlugin.domain.mob.base;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.domain.mob.meta.AbstractMobMeta;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public abstract class FriendlyImmortalMob extends AbstractMob {
    public FriendlyImmortalMob(FriendlyImmortalMobMeta meta) {
        super(new AbstractMobMeta(true, meta.silent(), meta.displayName(), meta.baseEntity()));
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, RunePlayer player) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, RunePlayer player) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityDamaged(EntityDamageEvent event) {
        event.getEntity().setFireTicks(0);
        event.setCancelled(true);
    }
}
