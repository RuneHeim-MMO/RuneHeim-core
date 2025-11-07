package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.bank.RepositoryQueryLockController;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.base.PaginatedBankGUI;
import org.zuma.examplePlugin.domain.gui.serializer.ItemContainerContentSerializer;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.StorageItem;
import org.zuma.examplePlugin.domain.item.state.states.StorageItemState;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class ItemInventory extends PaginatedBankGUI {
    private final RepositoryQueryLockController repositoryQueryLockController;
    private boolean isLoaded;

    @Nullable
    private UUID containerUUID, playerUUID;

    public ItemInventory(String title, int id, RepositoryQueryLockController repositoryQueryLockController) {
        super(title, id, 6, new HashSet<>(Arrays.asList(0, 1, 9, 10)));
        this.repositoryQueryLockController = repositoryQueryLockController;
        this.isLoaded = false;
        this.containerUUID = null;
        this.playerUUID = null;
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        final String[] bankContent = {null};

        ItemStack inHand = PlayerUtils.getItemStackInHand(player.asBukkitPlayer());
        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(inHand);

        if(!(itemType instanceof StorageItem storageItem)){
            return false;
        }

        StorageItemState state = storageItem.getState(inHand);

        if(state == null) {
            return false;
        }

        this.containerUUID = state.getUUID();

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                bankContent[0] = repositoryQueryLockController.getByUUID(player.getUUID());
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(player, Component.text("Please wait! Save/load is in progress."));
                    onFail.run();
                });
                return;
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    long incidentId = System.currentTimeMillis();
                    HudService.sendFailMessage(player, Component.text("Item content retrieval failed. Please submit a bug report. Incident #" + incidentId));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[ItemBankRetrievalError #" + incidentId + "] Failed to retrieve serialized item contents of container " + containerUUID + "! " + e);
                    onFail.run();
                });
                return;
            }

            List<ItemStackSlotPair> loadedPairs = List.of();

            if (bankContent[0] != null) {
                try {
                    loadedPairs = ItemContainerContentSerializer.deserializeItemContainerContentSync(bankContent[0]);
                } catch (Exception e) {
                    Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                        long incidentId = System.currentTimeMillis();
                        HudService.sendFailMessage(player, Component.text("Item content deserialization failed. Please submit a bug report. Incident #" + incidentId));
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[ItemBankDeserializationError #" + incidentId + "] Failed to deserialize item container of " + containerUUID + "! " + e);
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
                player.asBukkitPlayer().openInventory(getBaseInventory());
            });

        });

        return true;
    }

    public void saveBankSync(boolean updateItemMap) {
        if (this.containerUUID == null) {
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
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[ItemBankSerializationError #" + incidentId + "] Failed to serialize item of " + this.containerUUID + "! ", e);

            if (player != null) {
                HudService.sendFailMessage(player, Component.text("Item bank content serialization failed. Please submit a bug report. Incident #" + incidentId));
            }

        }

    }

    @Override
    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {
        if (this.containerUUID == null || !this.isLoaded) {
            return;
        }

        updateMemoryMap();

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
