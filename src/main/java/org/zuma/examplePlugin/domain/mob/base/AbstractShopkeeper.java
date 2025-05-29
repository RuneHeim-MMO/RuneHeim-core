package org.zuma.examplePlugin.domain.mob.base;

import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyStaticImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public abstract class AbstractShopkeeper extends FriendlyStaticImmortalMob {
    private final int shopId;

    public AbstractShopkeeper(FriendlyStaticImmortalMobMeta meta, int shopId) {
        super(meta);
        this.shopId = shopId;
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        player.getPlayer().sendMessage("Open shop id " + shopId);
        super.handlePositiveInteraction(event, player);
        event.setCancelled(true);
    }

}
