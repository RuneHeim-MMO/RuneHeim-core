package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.bank.BankQueryLockController;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.base.PaginatedBankGUI;
import org.zuma.examplePlugin.domain.gui.serializer.ItemContainerContentSerializer;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.*;

public class BankInventory extends PaginatedBankGUI {
    private final BankQueryLockController bankQueryLockController;
    private boolean isLoaded;

    @Nullable
    private UUID playerUUID;

    public BankInventory(String title, int id, BankQueryLockController bankQueryLockController) {
        super(title, id, 6, new HashSet<>(Arrays.asList(0, 1, 9, 10)));
        this.bankQueryLockController = bankQueryLockController;
        this.isLoaded = false;
        this.playerUUID = null;
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        final String[] bankContent = {null};

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                bankContent[0] = bankQueryLockController.getByUUID(player.getUUID());
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(player, Component.text("Please wait! Save/load is in progress."));
                    onFail.run();
                });
                return;
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    long incidentId = System.currentTimeMillis();
                    HudService.sendFailMessage(player, Component.text("Bank content retrieval failed. Please submit a bug report. Incident #" + incidentId));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[BankRetrievalError #" + incidentId + "] Failed to retrieve serialized bank contents of " + player.getUUID() + "! " + e);
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
                        HudService.sendFailMessage(player, Component.text("Bank content deserialization failed. Please submit a bug report. Incident #" + incidentId));
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[BankDeserializationError #" + incidentId + "] Failed to deserialize bank of " + player.getUUID() + "! " + e);
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
        if (this.playerUUID == null) {
            return;
        }

        if (updateItemMap) {
            updateMemoryMap();
        }

        try {
            String serialized = ItemContainerContentSerializer.serializeItemContainerContentSync(this, true);
            this.bankQueryLockController.save(this.playerUUID, serialized);
        } catch (Exception e) {
            RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(this.playerUUID);

            long incidentId = System.currentTimeMillis();
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[BankSerializationError #" + incidentId + "] Failed to serialize bank of " + this.playerUUID + "! ", e);

            if (player != null) {
                HudService.sendFailMessage(player, Component.text("Bank content serialization failed. Please submit a bug report. Incident #" + incidentId));
            }

        }

    }

    @Override
    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {
        if (this.playerUUID == null || !this.isLoaded) {
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
