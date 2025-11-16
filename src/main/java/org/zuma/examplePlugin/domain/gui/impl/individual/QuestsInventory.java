package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.constants.QuestStatus;

import java.util.*;

public class QuestsInventory extends PaginatedIndividualGUI {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    private final HashMap<Integer, String> questIdToValueMap = new HashMap<>();

    public QuestsInventory(String title, int id) {
        super(title, id, 6, IGNORED_SLOTS);
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        final int[] index = {0};

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
                        .map(quest -> {
                            ItemStack icon = quest.getDisplayItemStack(player);
                            assignMenuValue(icon, index[0]);
                            questIdToValueMap.put(index[0], quest.getId());
                            index[0]++;
                            return icon;

                        })
                        .toList(),
                true);

        player.asBukkitPlayer().openInventory(getBaseInventory());
        return true;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(clickedItem);
        if (menuValue == null) {
            return;
        }

        String questId = questIdToValueMap.get(menuValue);

        if (questId == null) {
            return;
        }

        Quest matchingQuest = RuneQuestPlugin.getQuestHandler().getRegistry().getQuestById(questId);

        if (matchingQuest == null) {
            return;
        }

        QuestStatus status = matchingQuest.getPlayerQuestStatus(player);

        if (status.equals(QuestStatus.STARTED)) {
            // Player can only track started quests
            player.setTrackedQuestId(questId);
            SoundType.UI_CLICK.play(player, false);
            HudService.sendInfoMessage(player, Component.text("Now tracking quest: " + matchingQuest.getName()));
            return;
        }


    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

}
