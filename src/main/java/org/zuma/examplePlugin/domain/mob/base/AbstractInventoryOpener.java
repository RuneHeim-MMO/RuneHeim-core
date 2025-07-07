package org.zuma.examplePlugin.domain.mob.base;

import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;

public abstract class AbstractInventoryOpener extends FriendlyImmortalMob {
    private final int inventoryId;

    public AbstractInventoryOpener(FriendlyImmortalMobMeta meta, int inventoryId, @Nullable String skin, int maxWalkDistance) {
        super(meta);
        this.inventoryId = inventoryId;

        if (skin != null) setPlayerDisguise(skin);
        setMaxWalkDistance(maxWalkDistance);
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        if (PlayerUtils.hasCustomInventoryOpen(player)) {
            return;
        }

        RuneQuestPlugin.getGUIHandler().openInventory(inventoryId, player, () -> {
            onInventoryClose(event, player);
        }, this, null);
        super.playSound(MobBehavior.MobSoundType.IDLE);
        event.setCancelled(true);
    }

    public int getInventoryId() {
        return this.inventoryId;
    }

    public abstract void onInventoryClose(PlayerInteractAtEntityEvent event, RunePlayer player);
}
