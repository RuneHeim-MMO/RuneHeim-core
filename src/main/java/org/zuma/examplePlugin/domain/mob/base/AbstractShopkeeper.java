package org.zuma.examplePlugin.domain.mob.base;

import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public abstract class AbstractShopkeeper extends FriendlyImmortalMob {
    private final int shopId;

    public AbstractShopkeeper(FriendlyImmortalMobMeta meta, int shopId, String skin, boolean canWalk) {
        super(meta);
        this.shopId = shopId;
        setPlayerDisguise(skin);
        setCanWalk(canWalk);
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        player.asBukkitPlayer().sendMessage("Open shop id " + shopId);
        super.handlePositiveInteraction(event, player);
        event.setCancelled(true);
    }

}
