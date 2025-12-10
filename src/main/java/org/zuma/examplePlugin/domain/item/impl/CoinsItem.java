package org.zuma.examplePlugin.domain.item.impl;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class CoinsItem extends MiscItem {
    private static final NamespacedKey COINS_AMOUNT_KEY = PersistenceDataKey.COINS_AMOUNT.key(RuneQuestPlugin.getInstance());

    public CoinsItem(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Coins", null);
    }

    @Override
    public ItemStack createItemStackOfType() {
        return this.createItemStackOfType(1);
    }

    @Override
    public ItemStack createItemStackOfType(int amount) {
        ItemStack item = super.createItemStackOfType(1);
        ItemMeta meta = item.getItemMeta();
        int customModelId = ItemId.DO_NOT_USE_RESERVED_CUSTOM_MODEL_COINS_ONE.asInt();

        if (amount >= 10_000) {
            customModelId = ItemId.DO_NOT_USE_RESERVED_CUSTOM_MODEL_COINS_PILE.asInt();
        } else if (amount >= 1_000) {
            customModelId = ItemId.DO_NOT_USE_RESERVED_CUSTOM_MODEL_COINS_HANDFUL.asInt();
        } else if (amount >= 100) {
            customModelId = ItemId.DO_NOT_USE_RESERVED_CUSTOM_MODEL_COINS_TWO.asInt();
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(COINS_AMOUNT_KEY, PersistentDataType.INTEGER, amount);

        meta.setCustomModelData(customModelId);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack createItemStackOfTypeIgnoreState(int amount) {
        return this.createItemStackOfType(amount);
    }

    @Override
    public void onPlayerPickupItem(PlayerAttemptPickupItemEvent event, RunePlayer player, StateManager itemStateManager) {
        event.setCancelled(true);
        ItemStack stack = event.getItem().getItemStack();

        ItemStack item = stack.clone();
        event.getItem().setItemStack(new ItemStack(Material.AIR));

        creditPlayer(item, player);
    }

    public void creditPlayer(ItemStack item, RunePlayer player) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (IllegalItemWatchdog.getItemStackStatus(item).equals(IllegalItemWatchdog.LegalityType.ILLEGAL)) {
            return;
        }

        Integer storedAmount = container.get(COINS_AMOUNT_KEY, PersistentDataType.INTEGER);
        int amount = ((storedAmount != null) ? storedAmount : 1) * item.getAmount(); // In case 2 same item stacks with same amounts stack together.;

        HudService.showCoinsChangeBubble(player, amount);
        player.setPouchCoins(player.getPouchCoins() + amount, false);
        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.COINS_PICKED_UP, 1);
    }

}
