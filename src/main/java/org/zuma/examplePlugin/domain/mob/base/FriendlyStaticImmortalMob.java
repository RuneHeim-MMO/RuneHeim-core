package org.zuma.examplePlugin.domain.mob.base;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.domain.mob.meta.AbstractMobMeta;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyStaticImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public abstract class FriendlyStaticImmortalMob extends AbstractMob {
    public FriendlyStaticImmortalMob(FriendlyStaticImmortalMobMeta meta) {
        super(new AbstractMobMeta(meta.id(), meta.typeId(), false, meta.silent(), meta.displayName(), meta.baseEntity()));
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
