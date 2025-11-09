package org.zuma.examplePlugin.domain.gui.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.StorageUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.StorageItem;
import org.zuma.examplePlugin.domain.item.state.states.StorageItemState;
import org.zuma.examplePlugin.domain.storage.RepositoryQueryLockController;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.serializer.ItemContainerContentSerializer;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.storage.constants.StorageType;
import org.zuma.examplePlugin.events.inventory.RuneStorageInventoryOpenEvent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public abstract class StorageInventory extends PaginatedStorageGUI {
    private final RepositoryQueryLockController repositoryQueryLockController;
    private final StorageType type;
    private boolean isLoaded;

    @Nullable
    private UUID playerUUID, storageContainerUUID;

    public StorageInventory(String title, int id, int rows, int maxPages, StorageType type, RepositoryQueryLockController repositoryQueryLockController, Set<Integer> ignoredSlots) {
        super(title, id, rows, maxPages, ignoredSlots);
        this.repositoryQueryLockController = repositoryQueryLockController;
        this.type = type;
        this.isLoaded = false;
        this.playerUUID = null;
        this.storageContainerUUID = null;
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        final String[] storageContent = {null};
        ItemStack inHand = PlayerUtils.getItemStackInHand(player.asBukkitPlayer());
        AbstractItemType itemTypeInHand = RuneQuestPlugin.getItemRegistry().getType(inHand);

        // Determine container UUID based on storage type
        switch (type) {
            case BANK -> storageContainerUUID = player.getUUID(); // Each player has their own bank
            case ITEM_STORAGE -> {

                if (!(itemTypeInHand instanceof StorageItem storageItem)) {
                    HudService.sendFailMessage(player, Component.text("Invalid item in hand."));
                    onFail.run();
                    return false;
                }

                StorageItemState state = storageItem.getState(inHand);

                if (state == null) {
                    HudService.sendFailMessage(player, Component.text("Invalid item in hand."));
                    onFail.run();
                    return false;
                }

                storageContainerUUID = state.getUUID();
            }
            default -> {
                HudService.sendFailMessage(player, Component.text("Invalid storage type."));
                onFail.run();
                return false;
            }
        }


        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                storageContent[0] = repositoryQueryLockController.getByUUID(storageContainerUUID);
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(player, Component.text(e.getMessage()));
                    onFail.run();
                });
                return;
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    long incidentId = System.currentTimeMillis();
                    HudService.sendFailMessage(player, Component.text("[Type: " + this.type + " ]" + " Storage content retrieval failed. Please submit a bug report. Incident #" + incidentId));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[StorageRetrievalError #" + incidentId + "] " + "[Type: " + this.type + "]" + " Failed to retrieve serialized storage contents of " + storageContainerUUID + "! " + e);
                    onFail.run();
                });
                return;
            }

            List<ItemStackSlotPair> loadedPairs = List.of();

            if (storageContent[0] != null) {
                try {
                    loadedPairs = ItemContainerContentSerializer.deserializeItemContainerContentSync(storageContent[0]);
                } catch (Exception e) {
                    Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                        long incidentId = System.currentTimeMillis();
                        HudService.sendFailMessage(player, Component.text("[Type: " + this.type + "]" + " Storage content deserialization failed. Please submit a bug report. Incident #" + incidentId));
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[StorageDeserializationError #" + incidentId + "] [Type: " + this.type + "]" + " Failed to deserialize storage of " + storageContainerUUID + "! " + e);
                        onFail.run();
                    });
                    return;
                }
            }

            List<ItemStackSlotPair> finalLoadedPairs = loadedPairs;
            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                if (!player.asBukkitPlayer().isOnline()) {
                    onFail.run();
                    return;
                }

                setPairsContent(finalLoadedPairs, false);
                this.isLoaded = true;
                this.playerUUID = player.getUUID();
                SoundType.STORAGE_OPEN.play(player, true);

                // Allow only 1 player to access the same storage at the same time
                this.repositoryQueryLockController.lockContainerUUID(storageContainerUUID);

                // Let other servers know about this storage being opened
                Bukkit.getServer().getPluginManager().callEvent(new RuneStorageInventoryOpenEvent(new RuneStorageInventoryOpenEvent.Payload(player.asBukkitPlayer().getName(), player.getUUID(), storageContainerUUID)));
                player.asBukkitPlayer().openInventory(getBaseInventory());
            });

        });

        return true;
    }

    @Nullable
    public UUID getStorageContainerUUID() {
        return this.storageContainerUUID;
    }

    public void saveContainerSync(boolean updateItemMap, boolean forceUnlock) {
        if (this.storageContainerUUID == null || this.playerUUID == null || !this.isLoaded) {
            return;
        }

        if (updateItemMap) {
            updateMemoryMap();
        }

        try {
            String serialized = ItemContainerContentSerializer.serializeItemContainerContentSync(this, true);
            this.repositoryQueryLockController.save(this.storageContainerUUID, serialized);

            if (forceUnlock) {
                this.repositoryQueryLockController.unlockContainerUUID(storageContainerUUID);
            }
        } catch (Exception e) {
            RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(this.playerUUID);

            long incidentId = System.currentTimeMillis();
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[StorageSerializationError #" + incidentId + "] [Type: " + this.type + "]" + " Failed to serialize storage of " + this.storageContainerUUID + "! ", e);

            if (player != null) {
                HudService.sendFailMessage(player, Component.text("[Type: " + this.type + "]" + " Storage content serialization failed. Please submit a bug report. Incident #" + incidentId));
            }

        }

    }

    private void writeToStoragePDC(ItemStack item, List<ItemStackSlotPair> items) {
        List<ItemRecord> records = items.stream().map(i -> {
            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(i.itemStack());
            if (type == null) return null;
            return new ItemRecord(type.id, i.itemStack().getAmount());
        }).toList();

        StorageUtils.addDisplayItemIdsPDC(item, records);

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(item);
        if (type == null) return;
        type.getMetaGenerator().updatePresentationLayer(item);
    }

    @Override
    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {
        if (this.storageContainerUUID == null || this.playerUUID == null || !this.isLoaded) {
            return;
        }

        updateMemoryMap();

        if (player != null) {
            SoundType.STORAGE_CLOSE.play(player, true);

            AtomicReference<ItemStack> matchingContainer = new AtomicReference<>();
            Arrays.stream(player.asBukkitPlayer().getInventory().getContents()).forEach(inInventory -> {
                if (inInventory == null) return;
                AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(inInventory);
                if (!(type instanceof StorageItem)) return;
                StorageItemState state = ((StorageItem) type).getState(inInventory);
                if (state == null) return;
                if (state.getUUID().equals(this.storageContainerUUID)) {
                    matchingContainer.set(inInventory);
                }
            });

            if (matchingContainer.get() != null) {
                writeToStoragePDC(matchingContainer.get(), getContent());
            }

        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            saveContainerSync(false, true);
        });
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        if (!this.isLoaded) {
            player.asBukkitPlayer().closeInventory();
        }
    }

    @Override
    public void onPlayerDropItem(PlayerDropItemEvent event, RunePlayer player) {
        AbstractItemType droppedType = RuneQuestPlugin.getItemRegistry().getType(event.getItemDrop().getItemStack());

        if (droppedType instanceof StorageItem storageItem) {
            StorageItemState state = storageItem.getState(event.getItemDrop().getItemStack());

            // Prevent dropping if item state is invalid (just to be safe)
            if (state == null) {
                event.setCancelled(true);
                return;
            }

            // Prevent dropping if the dropped storage is the one currently being accessed
            if (state.getUUID().equals(this.storageContainerUUID)) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }
}
