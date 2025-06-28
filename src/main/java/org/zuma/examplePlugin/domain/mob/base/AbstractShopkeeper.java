package org.zuma.examplePlugin.domain.mob.base;

import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;

public abstract class AbstractShopkeeper extends FriendlyImmortalMob {
    private final int shopId;

    public AbstractShopkeeper(FriendlyImmortalMobMeta meta, int shopId, @Nullable String skin, boolean canWalk) {
        super(meta);
        this.shopId = shopId;
        if (skin != null) setPlayerDisguise(skin);
        setCanWalk(canWalk);
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        if (PlayerUtils.hasCustomInventoryOpen(player)) {
            return;
        }

        player.asBukkitPlayer().sendMessage("Open shop id " + shopId);
        super.handlePositiveInteraction(event);
        event.setCancelled(true);
    }

}
