package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.handlers.game.BotVerificationHandler;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIOverlay;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BotVerificationMenu extends IndividualGUI {
    private final ClickTask task;
    private final Inventory inventory;
    private boolean isSolved;
    private Location lockLocation;
    private int failedAttempts;

    public BotVerificationMenu(String title, int id) {
        super(title, id, 6);
        this.isSolved = false;
        this.lockLocation = null;

        this.task = getRandomTask();
        setTitle(GUIOverlay.GENERIC_OVERLAY_6.asGlyph() + "§f" + Icon.ERROR.get() + "§f Verification - Click " + task.amount + "x " + task.color.getName() + "§f wool");

        this.inventory = getBaseInventory();
        this.failedAttempts = 0;
    }

    private ClickTask getRandomTask() {
        WoolColor[] colors = WoolColor.values();
        WoolColor randomColor = colors[(int) (Math.random() * colors.length)];
        int randomAmount = 3 + (int) (Math.random() * 3);
        return new ClickTask(randomColor, randomAmount);
    }

    private ItemStack getWoolItem(WoolColor color) {
        ItemStack i;

        switch (color) {
            case RED -> i = new ItemStack(Material.RED_WOOL);
            case CYAN -> i = new ItemStack(Material.CYAN_WOOL);
            case GREEN -> i = new ItemStack(Material.GREEN_WOOL);
            case YELLOW -> i = new ItemStack(Material.YELLOW_WOOL);
            case BLACK -> i = new ItemStack(Material.BLACK_WOOL);
            case WHITE -> i = new ItemStack(Material.WHITE_WOOL);
            default -> i = new ItemStack(Material.WHITE_WOOL);
        }


        // Set wool name to wool
        ItemMeta meta = i.getItemMeta();
        meta.displayName(Component.text("Wool"));
        i.setItemMeta(meta);

        return i;
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        this.lockLocation = player.asBukkitPlayer().getLocation();
        List<Integer> visitedSlots = new ArrayList<>();
        int slots = this.rows * 9;

        for (int i = 0; i < slots; i++) {
            // Fill with random wool
            WoolColor[] colors = WoolColor.values();
            WoolColor randomColor = colors[(int) (Math.random() * colors.length)];
            inventory.setItem(i, getWoolItem(randomColor));
        }


        int placed = 0;
        while (placed < task.amount) {
            int slot = (int) (Math.random() * (slots));
            if (visitedSlots.contains(slot)) {
                continue;
            }
            inventory.setItem(slot, getWoolItem(task.color));
            visitedSlots.add(slot);
            placed++;
        }


        player.asBukkitPlayer().openInventory(inventory);
        return true;
    }

    private void handleSolved(RunePlayer player) {
        if (isSolved) {
            return;
        }

        isSolved = true;
        player.setBotVerificationPending(false);
        BotVerificationHandler.incrementVerificationAmount(player);
        SoundType.SUCCESS_ACTION.play(player, false);

        player.asBukkitPlayer().closeInventory();
        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.SOLVED_BOT_VERIFICATIONS, 1);

        DropTable table = RuneQuestPlugin.getDropTableHandler().getByID(DropTableId.COINS_MEDIUM_GUARANTEED.asInt());
        if (table != null) {
            ItemRecord reward = table.roll(player);

            if (reward != null) {
                player.giveItemOrDrop(reward);
                HudService.sendSuccessMessage(player, Component.text("You have successfully completed the bot verification and received ")
                        .append(CurrencyUtils.getCoinsCurrencyComponent(reward.getAmount()).append(Component.text(" coins.", Color.SUCCESS.asColor()))));
            }
        }

    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        if (isSolved) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        WoolColor clickedColor = switch (clickedItem.getType()) {
            case RED_WOOL -> WoolColor.RED;
            case CYAN_WOOL -> WoolColor.CYAN;
            case GREEN_WOOL -> WoolColor.GREEN;
            case YELLOW_WOOL -> WoolColor.YELLOW;
            case BLACK_WOOL -> WoolColor.BLACK;
            case WHITE_WOOL -> WoolColor.WHITE;
            default -> null;
        };

        if (clickedColor == null) {
            return;
        }


        if (clickedColor == task.color) {
            task.decrementAmount();
            this.inventory.setItem(event.getSlot(), new ItemStack(Material.GRAY_WOOL));

            SoundType.UI_CLICK.play(player, false);

            if (task.amount <= 0) {
                handleSolved(player);
            }

        } else {
            SoundType.FAIL_ACTION.play(player, false);
            this.failedAttempts += 1;

            if (this.failedAttempts >= 3) {
                AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.FAILED_BOT_VERIFICATIONS, 1);
                PlayerUtils.alertStaff("[Bot Verification] " + player.asBukkitPlayer().getName() + " has failed the bot verification and has been kicked.");
                player.asBukkitPlayer().kick(TextUtils.getBrandKickScreen(Component.text("Bot Verification Failed!\nAre you a bot?", Color.ERROR_LIGHT.asColor())));
            }
        }
    }

    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {
        if (player == null) {
            return;
        }

        if (isSolved) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (!player.isOnline()) return;
            if (lockLocation != null) player.asBukkitPlayer().teleport(lockLocation);

            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.BOT_VERIFICATION_MENU.asInt(), player, null, null, null, true);
        }, 5L);

    }


    private enum WoolColor {
        RED("§cRed"),
        CYAN("§bCyan"),
        GREEN("§aGreen"),
        YELLOW("§eYellow"),
        BLACK("§8Black"),
        WHITE("§fWhite");

        public final String name;

        WoolColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    private static class ClickTask {
        public final WoolColor color;
        public int amount;

        public ClickTask(WoolColor color, int amount) {
            this.color = color;
            this.amount = amount;
        }

        public void decrementAmount() {
            this.amount = Math.max(0, this.amount - 1);
        }

    }

}
