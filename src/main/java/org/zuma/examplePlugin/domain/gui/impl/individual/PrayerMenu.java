package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.prayer.Prayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.*;

public class PrayerMenu extends IndividualGUI {
    private static final List<Integer> AVAILABLE_SLOTS = Arrays.asList(
            6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44
    );

    private static final List<Integer> TURN_OFF_BUTTON = Arrays.asList(
            37, 38, 39
    );

    private int taskId = -1;

    public PrayerMenu(String title, int id) {
        super(title, id, 6);
    }

    private void updateInventory(RunePlayer player, Inventory inventory) {

        Iterator<Integer> slotIterator = AVAILABLE_SLOTS.iterator();
        for (Prayer prayer : Prayer.values()) {
            if (!slotIterator.hasNext()) {
                break;
            }

            int slot = slotIterator.next();

            ItemStack button = new ItemStack(Material.AIR);


            boolean isActive = RuneQuestPlugin.getPrayerManager().getIsPrayerActive(player.getUUID(), prayer);
            List<Component> descriptionLines = new ArrayList<>();

            descriptionLines.add(Component.text(prayer.getDescription(), Color.ITEM_LORE_DESCRIPTION.asColor()));
            descriptionLines.add(Component.empty());
            descriptionLines.add(Component.text("Requirements:", Color.ACCENT.asColor()));
            descriptionLines.add(SkillType.PRAYER.getSymbolComponent().append(Component.text(prayer.getLevelRequirement() + " " + SkillType.PRAYER.getLabel(), Color.ITEM_REQUIREMENT.asColor())));

            button = isActive ? prayer.getEnabledBtn().asItemstack(Component.text(prayer.getName(), Color.SUCCESS.asColor()), descriptionLines) :
                    prayer.getDisabledBtn().asItemstack(Component.text(prayer.getName(), Color.ERROR.asColor()), descriptionLines);


            assignMenuValue(button, prayer.getId());
            inventory.setItem(slot, button);
        }
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        Inventory inventory = getBaseInventory();
        updateInventory(player, inventory);

        ItemStack turnOffItem = GUIControl.TRANSPARENT.asItemstack(Component.text("Turn Off All Prayers", Color.ERROR.asColor()));
        assignMenuValue(turnOffItem, -1);

        TURN_OFF_BUTTON.forEach(slot -> {
            inventory.setItem(slot, turnOffItem);
        });

        player.asBukkitPlayer().openInventory(inventory);

        this.taskId = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> updateInventory(player, inventory), 0L, 20L).getTaskId();
        return true;
    }


    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue == null) return;

        if (menuValue == -1) {
            RuneQuestPlugin.getPrayerManager().clearPrayers(player);
            updateInventory(player, event.getInventory());
            SoundType.UI_CLICK.play(player, false);
            return;
        }

        Prayer prayer = Arrays.stream(Prayer.values())
                .filter(p -> p.getId() == menuValue)
                .findFirst()
                .orElse(null);

        if (prayer == null) {
            return;
        }

        try {
            boolean isActive = RuneQuestPlugin.getPrayerManager().getIsPrayerActive(player.getUUID(), prayer);
            RuneQuestPlugin.getPrayerManager().setPrayer(player, prayer, !isActive);
            updateInventory(player, event.getInventory());
            SoundType.UI_CLICK.play(player, false);
        } catch (Exception e) {
            HudService.sendFailMessage(player, Component.text(e.getMessage()));
        }

    }


    @Override
    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {
        if (this.taskId != -1) {
            Bukkit.getScheduler().cancelTask(this.taskId);
            this.taskId = -1;
        }
    }
}
