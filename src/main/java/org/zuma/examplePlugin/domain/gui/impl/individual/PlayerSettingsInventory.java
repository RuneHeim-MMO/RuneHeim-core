package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.settings.PlayerSetting;
import org.zuma.examplePlugin.domain.player.settings.PlayerSettings;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerSettingsInventory extends PaginatedIndividualGUI {
    private static final List<PlayerSetting> NAMETAG_RELOAD_TRIGGERS = Arrays.asList(
            PlayerSetting.SHOW_UPGRADED_PREFIX,
            PlayerSetting.SHOW_SUPPORTER_PREFIX,
            PlayerSetting.SHOW_BASE_PREFIX
    );

    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 17, 18, 22, 26, 27, 31, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 51, 52, 53));
    private final HashMap<Integer, PlayerSetting> settingMap = new HashMap<>();

    public PlayerSettingsInventory(String title, int id) {
        super(title, id, 6, IGNORED_SLOTS);
    }


    private void updateInventory(RunePlayer player) {
        PlayerSettings settings = player.getSettings();
        List<ItemStack> content = new ArrayList<>();
        AtomicInteger index = new AtomicInteger();
        settingMap.clear();

        settings.getAllSettings().forEach((setting, enabled) -> {
            if (!setting.isVisibleTo(player)) return;
            ItemStack valueItem = enabled ?
                    GUIControl.YES.asItemstack(Component.text(setting.getTrueDisplay()), List.of(Component.text(setting.getDescription(), Color.ITEM_LORE_DESCRIPTION.asColor()))) :
                    GUIControl.NO.asItemstack(Component.text(setting.getFalseDisplay()), List.of(Component.text(setting.getDescription(), Color.ITEM_LORE_DESCRIPTION.asColor())));
            assignMenuValue(valueItem, index.get());

            content.add(setting.getIcon().asItemstack(Component.text(setting.getDisplayName()),
                    List.of(Component.text(setting.getDescription(), Color.ITEM_LORE_DESCRIPTION.asColor()))));

            content.add(GUIControl.CHEVRON_RIGHT.asItemstack());
            content.add(valueItem);

            settingMap.put(index.get(), setting);
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
        Integer itemValue = getMenuValue(clickedItem);
        if (itemValue == null) return;

        PlayerSetting matchingSetting = settingMap.get(itemValue);
        if (matchingSetting == null) return;

        player.getSettings().toggleSetting(matchingSetting);
        SoundType.UI_CLICK.play(player, false);
        updateInventory(player);

        if (NAMETAG_RELOAD_TRIGGERS.contains(matchingSetting)) {
            player.getScoreboardManager().reloadNametags();
            player.getScoreboardUsernameRenderer().updateTabUsername();
        }
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

}
