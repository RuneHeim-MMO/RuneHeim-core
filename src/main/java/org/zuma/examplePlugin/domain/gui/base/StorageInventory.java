package org.zuma.examplePlugin.domain.gui.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.StorageItem;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.StorageItemState;
import org.zuma.examplePlugin.domain.storage.RepositoryQueryLockController;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.serializer.ItemContainerContentSerializer;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.storage.StorageType;

import javax.annotation.Nullable;
import java.util.*;

public class StorageInventory extends PaginatedStorageGUI {
    private final RepositoryQueryLockController repositoryQueryLockController;
    private final StorageType type;
    private boolean isLoaded;

    @Nullable
    private UUID playerUUID, containerUUID;

    public StorageInventory(String title, int id, int rows, int maxPages, StorageType type, RepositoryQueryLockController repositoryQueryLockController, Set<Integer> ignoredSlots) {
        super(title, id, rows, maxPages, ignoredSlots);
        this.repositoryQueryLockController = repositoryQueryLockController;
        this.type = type;
        this.isLoaded = false;
        this.playerUUID = null;
        this.containerUUID = null;
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        final String[] storageContent = {null};

        // Determine container UUID based on storage type
        switch (type) {
            case BANK -> containerUUID = player.getUUID(); // Each player has their own bank
            case ITEM_STORAGE -> {
                ItemStack inHand = PlayerUtils.getItemStackInHand(player.asBukkitPlayer());
                AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(inHand);

                if (!(itemType instanceof StorageItem storageItem)) {
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

                containerUUID = state.getUUID();
            }
            default -> {
                HudService.sendFailMessage(player, Component.text("Invalid storage type."));
                onFail.run();
                return false;
            }
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                storageContent[0] = repositoryQueryLockController.getByUUID(containerUUID);
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(player, Component.text("Please wait! Save/load is in progress."));
                    onFail.run();
                });
                return;
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    long incidentId = System.currentTimeMillis();
                    HudService.sendFailMessage(player, Component.text("[Type: " + this.type + " ]" + " Storage content retrieval failed. Please submit a bug report. Incident #" + incidentId));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[StorageRetrievalError #" + incidentId + "] " + "[Type: " + this.type + "]" + " Failed to retrieve serialized storage contents of " + containerUUID + "! " + e);
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
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[StorageDeserializationError #" + incidentId + "] [Type: " + this.type + "]" + " Failed to deserialize storage of " + containerUUID + "! " + e);
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
                player.asBukkitPlayer().openInventory(getBaseInventory());
            });

        });

        return true;
    }

    public void saveBankSync(boolean updateItemMap) {
        if (this.containerUUID == null || this.playerUUID == null || !this.isLoaded) {
            return;
        }

        if (updateItemMap) {
            updateMemoryMap();
        }

        try {
            String serialized = ItemContainerContentSerializer.serializeItemContainerContentSync(this, true);
            this.repositoryQueryLockController.save(this.containerUUID, serialized);
        } catch (Exception e) {
            RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(this.playerUUID);

            long incidentId = System.currentTimeMillis();
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[StorageSerializationError #" + incidentId + "] [Type: " + this.type + "]" + " Failed to serialize storage of " + this.containerUUID + "! ", e);

            if (player != null) {
                HudService.sendFailMessage(player, Component.text("[Type: " + this.type + "]" + " Storage content serialization failed. Please submit a bug report. Incident #" + incidentId));
            }

        }

    }

    @Override
    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {
        if (this.containerUUID == null || this.playerUUID == null || !this.isLoaded) {
            return;
        }

        updateMemoryMap();

        if (player != null) {
            SoundType.STORAGE_CLOSE.play(player, true);
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            saveBankSync(false);
        });
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        if (!this.isLoaded) {
            player.asBukkitPlayer().closeInventory();
        }
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }
}
