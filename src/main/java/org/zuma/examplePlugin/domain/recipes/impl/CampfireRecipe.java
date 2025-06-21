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
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CampfireRecipe extends AbstractRecipe {
    private final SkillRequirement mainRequirement;
    private final int MINIMUM_SUCCESS_CHANCE = 50;

    private final HashMap<UUID, CampfireBlock> inProgressBlocks = new HashMap<>();

    public CampfireRecipe(ConcreteRecipeMeta meta) {
        super(new ItemStack(Material.OAK_LOG), new AbstractRecipeMeta(meta.id(), RecipeType.CAMPFIRE_RECIPE, meta.craftTimeMs(), meta.requirements(), meta.ingredients(), null, null, meta.successXp()));

        ItemMeta stackMeta = super.getDisplayItemstack().getItemMeta();
        stackMeta.displayName(Component.text("Campfire").decoration(TextDecoration.ITALIC, false));
        super.getDisplayItemstack().setItemMeta(stackMeta);


        mainRequirement = super.getRequirements().stream()
                .filter(req -> req.type() == SkillType.FIREMAKING)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isCraftSuccessful(RunePlayer player) {
        if (mainRequirement == null) {
            return true;
        }

        int successChance = PlayerUtils.getCraftSuccessChance(player, mainRequirement, MINIMUM_SUCCESS_CHANCE);
        return ThreadLocalRandom.current().nextInt(0, 101) < successChance;

    }

    @Override
    public int getChanceOfSuccess(RunePlayer player) {
        if (mainRequirement == null) {
            return 100;
        }

        return PlayerUtils.getCraftSuccessChance(player, mainRequirement, MINIMUM_SUCCESS_CHANCE);
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
