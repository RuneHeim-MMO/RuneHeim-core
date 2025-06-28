package org.zuma.examplePlugin.domain.mob.base;

import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;

public abstract class AbstractInventoryOpener extends FriendlyImmortalMob {
    private final int inventoryId;

    public AbstractInventoryOpener(FriendlyImmortalMobMeta meta, int inventoryId, @Nullable String skin, boolean canWalk) {
        super(meta);
        this.inventoryId = inventoryId;

        if (skin != null) setPlayerDisguise(skin);
        setCanWalk(canWalk);
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        if (PlayerUtils.hasCustomInventoryOpen(player)) {
            return;
        }

        RuneQuestPlugin.getGUIHandler().openInventory(inventoryId, player, () -> {
            onInventoryClose(event, player);
        });
        super.handlePositiveInteraction(event);
        event.setCancelled(true);
    }

    public abstract void onInventoryClose(PlayerInteractAtEntityEvent event, RunePlayer player);
}
