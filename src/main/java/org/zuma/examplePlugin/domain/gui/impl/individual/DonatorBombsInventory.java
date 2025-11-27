package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.*;

public class DonatorBombsInventory extends PaginatedIndividualGUI {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>(List.of(0,1,2,3,4,5,6,7,8));
    private static final EnumMap<ServerModifierType, Integer> buttonIds = new EnumMap<>(Map.of(
            ServerModifierType.DOUBLE_SKILL_XP, 1,
            ServerModifierType.DOUBLE_COMBAT_XP, 2,
            ServerModifierType.INCREASED_MOB_DROPS, 3,
            ServerModifierType.INCREASED_RESOURCE_DROPS, 4,
            ServerModifierType.INCREASED_PERFECT_STRIKE, 5
            // 6 for megaphones
    ));

    public DonatorBombsInventory(String title, int id) {
        super(title, id, 6, IGNORED_SLOTS);
    }

    private ItemStack createBombIcon(ServerModifierType type, int amount) {
        ItemStack item = new ItemStack(Material.AIR);

        switch (type) {
            case DOUBLE_SKILL_XP -> {
                item = GUIControl.SKILL_XP_BOMB_ICON.asItemstack(
                        Component.text(type.getLabel()),
                        List.of(
                                Component.text("Once activated, it is consumed", Color.ITEM_LORE_DESCRIPTION.asColor()),
                                Component.text("and grants ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("Double Skill XP", Color.ACCENT.asColor())),
                                Component.text("to the entire server for ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("20 minutes", Color.ACCENT.asColor()))
                        )
                );
            }

            case DOUBLE_COMBAT_XP -> {
                item = GUIControl.COMBAT_XP_BOMB_ICON.asItemstack(
                        Component.text(type.getLabel()),
                        List.of(
                                Component.text("Once activated, it is consumed", Color.ITEM_LORE_DESCRIPTION.asColor()),
                                Component.text("and grants ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("Double Combat XP", Color.ACCENT.asColor())),
                                Component.text("to the entire server for ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("20 minutes", Color.ACCENT.asColor()))
                        )
                );
            }

            case INCREASED_MOB_DROPS -> {
                item = GUIControl.MOB_DROPS_BOMB_ICON.asItemstack(
                        Component.text(type.getLabel()),
                        List.of(
                                Component.text("Once activated, it is consumed", Color.ITEM_LORE_DESCRIPTION.asColor()),
                                Component.text("and grants a ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("50% increased mob drops", Color.ACCENT.asColor())),
                                Component.text("buff to the entire server for ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("20 minutes", Color.ACCENT.asColor()))
                        )
                );
            }

            case INCREASED_RESOURCE_DROPS -> {
                item = GUIControl.RESOURCE_DROPS_BOMB_ICON.asItemstack(
                        Component.text(type.getLabel()),
                        List.of(
                                Component.text("Once activated, it is consumed", Color.ITEM_LORE_DESCRIPTION.asColor()),
                                Component.text("and grants a ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("50% increased resource drops", Color.ACCENT.asColor())),
                                Component.text("buff to the entire server for ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("20 minutes", Color.ACCENT.asColor()))
                        )
                );
            }

            case INCREASED_PERFECT_STRIKE -> {
                item = GUIControl.PERFECT_STRIKE_BOMB_ICON.asItemstack(
                        Component.text(type.getLabel()),
                        List.of(
                                Component.text("Once activated, it is consumed", Color.ITEM_LORE_DESCRIPTION.asColor()),
                                Component.text("and grants a ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("5x Perfect Strike chance", Color.ACCENT.asColor())),
                                Component.text("buff to the entire server for ", Color.ITEM_LORE_DESCRIPTION.asColor())
                                        .append(Component.text("20 minutes", Color.ACCENT.asColor()))
                        )
                );
            }
        }


        assignMenuValue(item, buttonIds.get(type));
        return item;
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        List<ItemStack> content = new ArrayList<>();
        EnumMap<ServerModifierType, Integer> bombAmounts = player.getBombsContainer().getBombsAmounts();

        for (ServerModifierType type : ServerModifierType.values()) {
            if (bombAmounts.get(type) > 0) {
                for (int i = 0; i < bombAmounts.get(type); i++) {
                    ItemStack bombIcon = createBombIcon(type, bombAmounts.get(type));
                    content.add(bombIcon);
                }
            }
        }

        if (player.getBombsContainer().getMegaphoneUses() > 0) {
            for (int i = 0; i < player.getBombsContainer().getMegaphoneUses(); i++) {
                ItemStack megaphoneIcon = GUIControl.MEGAPHONE_ICON.asItemstack(Component.text("Megaphone"), List.of(
                        Component.text("Use to broadcast a message to all players online!", Color.ITEM_LORE_DESCRIPTION.asColor())
                ));

                assignMenuValue(megaphoneIcon, 6);
                content.add(megaphoneIcon);
            }
        }

        setContent(content, true);

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

        Integer buttonId = getMenuValue(clickedItem);

        if (buttonId == null) {
            return;
        }

        if (buttonId == 6) {
            player.asBukkitPlayer().performCommand("megaphone");
            player.asBukkitPlayer().closeInventory();
            return;
        }

        ServerModifierType type = buttonIds.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(buttonId))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (type == null) {
            return;
        }


        boolean result = RuneQuestPlugin.getServerModifierHandler().activateXPBomb(player, type, type.getDefaultDurationMinutes() * 60 * 1000L + 100); // +100ms to account for scheduling delays

        if (result) {
            player.asBukkitPlayer().closeInventory();
        }
    }


    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

}
