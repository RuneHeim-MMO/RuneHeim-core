package org.zuma.examplePlugin.domain.item.handlers.ownership;

import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.CoinsItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.UUID;

public class ItemPickupHandler {
    private final CooldownHandler messageCooldowns = new CooldownHandler();


    public boolean canPickupItem(RunePlayer player, AbstractItemType type, ItemStack stack) {
        // Check private-instance ownership
        if (!ItemOwnershipHandler.isPickupableBy(stack, player)) {
            return false;
        }

        if (type.isTradeable) {
            return true;
        }

        UUID untradeableOwnerUUID = type.getUntradeableOwnerUUID(stack);

        if (untradeableOwnerUUID == null) {
            type.setUntradeableOwnerUUID(stack, player.getUUID());
            return true;
        }

        if (!player.getUUID().equals(untradeableOwnerUUID)) {

            if (!messageCooldowns.isCooldownInProgress(player)) {
                messageCooldowns.startCooldown(player, 3000L);
                HudService.sendFailMessage(player, Component.text("Cannot pick up item. It is untradeable and not owned by you."));
            }

            return false;
        }

        return true;

    }

    public void onPlayerPickupItem(PlayerAttemptPickupItemEvent event, AbstractItemType item, RunePlayer player) {
        ItemStack itemStack = event.getItem().getItemStack();

        if (!canPickupItem(player, item, itemStack)) {
            event.setCancelled(true);
            return;
        }

        ItemOwnershipHandler.removeOwnershipKeys(itemStack);

        if (!(item instanceof CoinsItem)) {
            AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.ITEMS_PICKED_UP, event.getItem().getItemStack().getAmount());
        }

    }
}
