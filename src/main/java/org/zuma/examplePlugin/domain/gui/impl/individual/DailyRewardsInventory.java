package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.daily.DailyRewardEntry;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.CoinsItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.*;

public class DailyRewardsInventory extends IndividualGUI {
    private static final List<Integer> AVAILABLE_SLOTS = Arrays.asList(
            1, 2, 3, 4, 5, 6, 7,
            19, 20, 21, 22, 23, 24, 25,
            49
    );

    public DailyRewardsInventory(String title, int id) {
        super(title, id, 6);
    }

    private void updateInventory(RunePlayer player, Inventory inventory) {
        Iterator<Integer> slotIterator = AVAILABLE_SLOTS.iterator();
        Collection<DailyRewardEntry> dailyRewards = player.getDailyRewards().getRewardEntries().values();
        int currentItemsDay = 0;
        int currentStreakDay = player.getDailyRewards().getCurrentStreak();

        for (DailyRewardEntry rewardEntry : dailyRewards) {
            currentItemsDay++;

            boolean isTimedOut = currentStreakDay > currentItemsDay;
            boolean isToday = currentStreakDay == currentItemsDay;
            boolean isInFuture = currentStreakDay < currentItemsDay;

            if (!slotIterator.hasNext()) {
                break;
            }

            int slot = slotIterator.next();
            AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(rewardEntry.itemId());

            if (itemType == null) {
                continue;
            }

            if (isTimedOut) {
                continue;
            }
            if (isToday) {
                if (!player.getDailyRewards().canClaimToday()) {
                    continue;
                }
            }

            ItemStack button = itemType instanceof CoinsItem ? itemType.createItemStackOfType(rewardEntry.max()) : itemType.createItemStackOfType();

            ItemMeta meta = button.getItemMeta();
            if (meta == null || meta.displayName() == null || meta.lore() == null) continue;

            Component oldDisplayName = meta.displayName();

            meta.displayName(Component.text("Day #" + currentItemsDay).decoration(TextDecoration.ITALIC, false));

            List<Component> oldLore = meta.lore();
            List<Component> lore = new ArrayList<>();

            if (isToday) {
                lore.add(Component.text("You can claim this reward today!", NamedTextColor.GREEN));

            }

            if (isInFuture) {
                lore.add(Component.text("This reward will be available in " + (currentItemsDay - currentStreakDay) + " day(s).", Color.ERROR.asColor()));
            }

            lore.add(Component.empty());
            lore.add(Component.text(rewardEntry.min() + " - " + rewardEntry.max() + "x ", Color.ACCENT.asColor()).append(oldDisplayName));
            lore.add(Component.empty());

            if (oldLore != null) {
                for (Component c : oldLore) {
                    lore.add(c.color(Color.ITEM_TYPE_LABEL.asColor()));
                }
            }


            meta.lore(lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList());
            button.setItemMeta(meta);

            if (isInFuture) {
                assignMenuValue(button, -2);
            }

            if (isToday) {
                // -1 todays slot, -2 - future items
                assignMenuValue(button, -1);
            }

            inventory.setItem(slot, button);
        }

    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        Inventory inventory = getBaseInventory();
        updateInventory(player, inventory);

        player.asBukkitPlayer().openInventory(inventory);
        return true;
    }


    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValueInt(currentItem);

        if (menuValue == null) {
            return;
        }


        if (menuValue == -1 && player.getDailyRewards().canClaimToday()) {
            SoundType.CASINO_START_ANTICIPATION.play(player, false);
            event.getClickedInventory().setItem(slot, GUIControl.LOADING_1.asItemstack());

            new BukkitRunnable() {
                int i = 0;

                public void run() {
                    if (!player.isOnline()) {
                        cancel();
                        return;
                    }

                    if (i >= 0 && i < 10) {
                        event.getClickedInventory().setItem(slot, GUIControl.LOADING_2.asItemstack());
                    }
                    if (i >= 10 && i < 20) {
                        event.getClickedInventory().setItem(slot, GUIControl.LOADING_3.asItemstack());
                    }
                    if (i >= 20 && i < 30) {
                        event.getClickedInventory().setItem(slot, GUIControl.LOADING_4.asItemstack());
                    }
                    if (i >= 30 && i < 50) {
                        event.getClickedInventory().setItem(slot, GUIControl.LOADING_5.asItemstack());
                    }

                    if (i >= 40) {
                        SoundType.CASINO_WIN.play(player, false);
                        player.getDailyRewards().claimToday(player);
                        RuneQuestPlugin.getDailyRewardHandler().savePlayerDailyRewardData(player, true);
                        player.asBukkitPlayer().closeInventory();
                        cancel();
                        return;
                    }


                    SoundType.CASINO_ROLL_TICK.play(player.asBukkitPlayer().getLocation(), player.asBukkitPlayer(), i * 0.02f);
                    i += 2;
                }
            }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 2L);
        }

        if (menuValue == -2) {
            // Future reward
            HudService.sendFailMessage(player, Component.text("This reward is not available yet!", Color.ERROR.asColor()));
        }


    }

}
