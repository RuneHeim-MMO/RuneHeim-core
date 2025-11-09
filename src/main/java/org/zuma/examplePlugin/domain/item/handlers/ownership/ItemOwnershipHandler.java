package org.zuma.examplePlugin.domain.item.handlers.ownership;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Objects;
import java.util.UUID;

public class ItemOwnershipHandler implements Listener {
    private static final Long privateDropTime = 1000L * 30L; // 30 seconds
    private static final PersistenceDataKey ownerUUIDKey = PersistenceDataKey.LOCKED_TO_OWNER_UUID;
    private static final PersistenceDataKey lockExpiry = PersistenceDataKey.LOCKED_TO_OWNER_UNTIL;

    public static void setDroppedItemPrivate(ItemStack item, RunePlayer player) {
        setDroppedItemPrivate(item, player.getUUID());
    }

    public static void setDroppedItemPrivate(ItemStack item, UUID uuid) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(ownerUUIDKey.key(RuneQuestPlugin.getInstance()), PersistentDataType.STRING, uuid.toString());
        pdc.set(lockExpiry.key(RuneQuestPlugin.getInstance()), PersistentDataType.LONG, (System.currentTimeMillis() / (10_000L) + privateDropTime / 10_000L));
        item.setItemMeta(meta);
    }

    public static void removeOwnershipKeys(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.remove(ownerUUIDKey.key(RuneQuestPlugin.getInstance()));
        pdc.remove(lockExpiry.key(RuneQuestPlugin.getInstance()));

        item.setItemMeta(meta);
    }

    public static boolean isPickupableBy(ItemStack item, RunePlayer player) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String ownerUUID = pdc.get(ownerUUIDKey.key(RuneQuestPlugin.getInstance()), PersistentDataType.STRING);
        Long lockExpiryMillis = pdc.get(lockExpiry.key(RuneQuestPlugin.getInstance()), PersistentDataType.LONG);

        if (ownerUUID == null || lockExpiryMillis == null) {
            return true;
        }

        if (System.currentTimeMillis() > lockExpiryMillis * 10_000L) {
            return true;
        }

        return Objects.equals(player.getUUID().toString(), ownerUUID);

    }


}
