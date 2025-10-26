package org.zuma.examplePlugin.domain.gui.impl.individual.admin;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIOverlay;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;

import java.util.Comparator;


public class RecipeRegistry extends PaginatedIndividualGUI {
    public RecipeRegistry(int id) {
        super(GUIOverlay.GENERIC_OVERLAY_6.asGlyph() + "Recipe Registry", id, 6, null);
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

        setContent(RuneQuestPlugin.getRecipeRegistry().getAllRecipes().stream().sorted(Comparator.comparingInt(AbstractRecipe::getId)).map(r -> r.getDisplayItemstack(player, RuneQuestPlugin.getItemRegistry())).toList(), true);
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
