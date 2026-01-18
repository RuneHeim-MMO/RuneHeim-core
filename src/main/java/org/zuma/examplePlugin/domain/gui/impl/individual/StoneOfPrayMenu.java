package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.impl.utility.StoneOfPray;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.StoneOfPrayState;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.prayer.Prayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StoneOfPrayMenu extends PaginatedIndividualGUI {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>(Arrays.asList(0,  4,  8, 9, 13, 17, 18, 22, 26, 27, 31, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 51, 52, 53));
    private final HashMap<Integer, Prayer> prayerMap = new HashMap<>();

    public StoneOfPrayMenu(String title, int id) {
        super(title, id, 6, IGNORED_SLOTS);
    }


    private void updateInventory(RunePlayer player) {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(ItemId.STONE_OF_PRAY);

        if (!(type instanceof StoneOfPray stone)) {
            player.asBukkitPlayer().closeInventory();
            return;
        }

        AbstractState state = stone.getState(player.asBukkitPlayer().getInventory().getItemInMainHand());

        if (!(state instanceof StoneOfPrayState prayState)) {
            player.asBukkitPlayer().closeInventory();
            return;
        }

        List<Prayer> presets = prayState.getPreset();
        List<ItemStack> content = new ArrayList<>();
        AtomicInteger index = new AtomicInteger();
        prayerMap.clear();

        Arrays.stream(Prayer.values()).forEach(prayer -> {
            boolean enabled = presets.contains(prayer);

            ItemStack valueItem = enabled ?
                    GUIControl.YES.asItemstack(Component.text(prayer.getName()), List.of(Component.text("Active", Color.SUCCESS.asColor()))) :
                    GUIControl.NO.asItemstack(Component.text(prayer.getName()), List.of(Component.text("Not active", Color.ERROR.asColor())));

            if (prayer.getLevelRequirement() > player.getSkill(SkillType.PRAYER).getLevelWithBonuses()) {
                valueItem = GUIControl.INFO.asItemstack(Component.text(prayer.getName()), List.of(Component.text("You do not meet requirements for this prayer.", Color.ERROR.asColor())));
            } else {
                assignMenuValue(valueItem, index.get());
            }

            List<Component> lore = List.of(Component.text(prayer.getDescription(), Color.ITEM_LORE_DESCRIPTION.asColor()));
            content.add(enabled ? prayer.getEnabledBtn().asItemstack(Component.text(prayer.getName()), lore) : prayer.getDisabledBtn().asItemstack(Component.text(prayer.getName()), lore));

            content.add(GUIControl.CHEVRON_RIGHT.asItemstack());
            content.add(valueItem);

            prayerMap.put(index.get(), prayer);
            index.getAndIncrement();
        });

        setContent(content, true);
        getBaseInventory();
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        updateInventory(player);
        player.asBukkitPlayer().openInventory(getBaseInventory());
        return true;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) return;
        Integer itemValue = getMenuValueInt(clickedItem);
        if (itemValue == null) return;


        Prayer matchingPrayer = prayerMap.get(itemValue);
        if (matchingPrayer == null) return;

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(ItemId.STONE_OF_PRAY);

        if (!(type instanceof StoneOfPray stone)) {
            player.asBukkitPlayer().closeInventory();
            return;
        }

        AbstractState state = stone.getState(player.asBukkitPlayer().getInventory().getItemInMainHand());

        if (!(state instanceof StoneOfPrayState prayState)) {
            player.asBukkitPlayer().closeInventory();
            return;
        }

        if (prayState.getPreset().contains(matchingPrayer)) {
            prayState.getPreset().remove(matchingPrayer);
        } else {
            prayState.getPreset().add(matchingPrayer);
        }

        List<Prayer> conflicts = new ArrayList<>();
        for (Prayer current : new ArrayList<>(prayState.getPreset())) {
            if (current.getConflictingPrayers().contains(matchingPrayer) || matchingPrayer.getConflictingPrayers().contains(current)) {
                conflicts.add(current);
            }
        }

        // remove all conflicts at once
        if (!conflicts.isEmpty()) {
            prayState.getPreset().removeAll(conflicts);
        }

        SoundType.UI_CLICK.play(player, false);
        updateInventory(player);
        type.getMetaGenerator().updatePresentationLayer(player.asBukkitPlayer().getInventory().getItemInMainHand());
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

}
