package org.zuma.examplePlugin.domain.recipes.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.impl.CampfireBlock;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.IngredientRecord;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipes.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.recipes.meta.ConcreteRecipeMeta;

import java.util.HashMap;
import java.util.UUID;

public class CampfireRecipe extends AbstractRecipe {
    private final HashMap<UUID, CampfireBlock> inProgressBlocks = new HashMap<>();

    public CampfireRecipe(ConcreteRecipeMeta meta) {
        super(new ItemStack(Material.OAK_LOG), new AbstractRecipeMeta(meta.id(), RecipeType.CAMPFIRE_RECIPE, meta.craftTimeMs(), meta.requirements(), meta.ingredients(), null, null, meta.successXp()));

        ItemMeta stackMeta = super.getDisplayItemstack().getItemMeta();
        stackMeta.displayName(Component.text("Campfire").decoration(TextDecoration.ITALIC, false));
        super.getDisplayItemstack().setItemMeta(stackMeta);
    }

    @Override
    public boolean isCraftSuccessful(RunePlayer player) {
        return true;
    }

    @Override
    public int getChanceOfSuccess(RunePlayer player) {
        return 100;
    }

    @Override
    public void onCraftingStart(RunePlayer player) {
        boolean isSoulSkin = this.getIngredients().stream().map(IngredientRecord::itemId).toList().contains(ItemId.MAGIC_LOG);

        CampfireBlock campfire = new CampfireBlock(PlayerUtils.trunacePlayerFeetLocation(player), "Logs", isSoulSkin, 300);
        inProgressBlocks.put(player.getUUID(), campfire);
        RuneQuestPlugin.getBlockManager().registerBlock(campfire);
    }

    @Override
    public void onCraftingSuccess(RunePlayer player) {
        CampfireBlock progressBlock = inProgressBlocks.get(player.getUUID());
        inProgressBlocks.remove(player.getUUID());

        if (progressBlock != null) {
            progressBlock.startFire();
        }
    }

    @Override
    public void onCraftingCancelled(RunePlayer player) {
        CampfireBlock progressBlock = inProgressBlocks.get(player.getUUID());
        inProgressBlocks.remove(player.getUUID());

        if (progressBlock != null) {
            RuneQuestPlugin.getBlockManager().unregisterBlock(progressBlock);
        }

    }

}
