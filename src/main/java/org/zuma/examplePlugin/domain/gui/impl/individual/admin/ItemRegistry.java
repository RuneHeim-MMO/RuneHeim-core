package org.zuma.examplePlugin.domain.gui.impl.individual.admin;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIOverlay;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Comparator;


public class ItemRegistry extends PaginatedIndividualGUI {
    public ItemRegistry(int id) {
        super(GUIOverlay.GENERIC_OVERLAY_6.asGlyph() + "Item Registry", id, 6, null);
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        if (!player.getRole().hasPermission(Permission.INTERACTABLE_SPAWN)) {
            return false;
        }
        if (!player.getRole().hasPermission(Permission.STAFF_ITEM_SPAWN)) {
            HudService.sendFailMessage(player, Component.text("Missing STAFF_ITEM_SPAWN permission.").color(Color.ERROR.asColor()));
            return false;
        }

        setContent(RuneQuestPlugin.getItemRegistry().getAllItems().stream().sorted(Comparator.comparingInt(a -> a.id)).map(AbstractItemType::createItemStackOfType).toList(), false);
        player.asBukkitPlayer().openInventory(getBaseInventory());
        return true;
    }


    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {

    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }


}
