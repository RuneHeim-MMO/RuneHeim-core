package org.zuma.examplePlugin.domain.gui.impl.individual;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.constants.QuestStatus;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class QuestsInventory extends PaginatedIndividualGUI {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

    public QuestsInventory(String title, int id) {
        super(title, id, 6, IGNORED_SLOTS);
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        setContent(
                RuneQuestPlugin.getQuestHandler().getRegistry().getAllQuests().stream().filter(q -> !q.isSilent())
                        .sorted(Comparator.comparing(
                                quest -> {
                                    QuestStatus status = quest.getPlayerQuestStatus(player);
                                    return switch (status) {
                                        case STARTED -> 1;
                                        case NOT_STARTED -> 2;
                                        case NOT_MEETING_REQUIREMENTS -> 3;
                                        case COMPLETED -> 4;
                                    };
                                }
                        ))
                        .map(quest -> quest.getDisplayItemStack(player))
                        .toList()
        );

        player.asBukkitPlayer().openInventory(getBaseInventory());
        return true;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

}
