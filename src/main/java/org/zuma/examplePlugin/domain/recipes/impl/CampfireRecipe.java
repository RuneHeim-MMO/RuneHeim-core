package org.zuma.examplePlugin.domain.recipes.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.impl.CampfireBlock;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipes.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.recipes.meta.v1.CampfireRecipeMeta;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CampfireRecipe extends AbstractRecipe {
    private final SkillRequirement mainRequirement;
    private final int MINIMUM_SUCCESS_CHANCE = 50;
    private final int burnTime;
    private final boolean soulCampfireSkin;
    private final String initialName, burningName;
    @Nullable
    private final ItemRecord despawnDrop;

    private final HashMap<UUID, CampfireBlock> inProgressBlocks = new HashMap<>();

    public CampfireRecipe(AbstractRecipeMeta meta, CampfireRecipeMeta campfireRecipeMeta) {
        super(new ItemStack(Material.OAK_LOG), new AbstractRecipeMeta(meta.id(), RecipeType.CAMPFIRE_RECIPE, meta.craftTimeMs(), meta.requirements(), meta.ingredients(), null, null, meta.successXp(), meta.cost()));

        this.burnTime = campfireRecipeMeta.burnTime();
        this.soulCampfireSkin = campfireRecipeMeta.isSoulCampfire();
        this.despawnDrop = campfireRecipeMeta.despawnDrop();
        this.initialName = campfireRecipeMeta.initialName();
        this.burningName = campfireRecipeMeta.burningName();

        ItemMeta stackMeta = super.getDisplayItemstack().getItemMeta();
        stackMeta.displayName(Component.text(this.burningName).decoration(TextDecoration.ITALIC, false));
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

        return ThreadLocalRandom.current().nextInt(0, 101) < getChanceOfSuccess(player);

    }

    @Override
    public int getChanceOfSuccess(RunePlayer player) {
        if (mainRequirement == null) {
            return 100;
        }

        return PlayerUtils.getCraftSuccessChance(player, mainRequirement, MINIMUM_SUCCESS_CHANCE);
    }

    @Override
    public void handleRecipeCraftingStart(RunePlayer player) {
        CampfireBlock campfire = new CampfireBlock(player.getUUID(), PlayerUtils.trunacePlayerFeetLocation(player), initialName, burningName, soulCampfireSkin, burnTime, despawnDrop);
        inProgressBlocks.put(player.getUUID(), campfire);
        RuneQuestPlugin.getBlockManager().registerBlock(campfire);
    }

    @Override
    public void handleRecipeCraftingSuccess(RunePlayer player) {
        CampfireBlock progressBlock = inProgressBlocks.get(player.getUUID());
        inProgressBlocks.remove(player.getUUID());

        if (progressBlock != null) {
            progressBlock.startFire();
        }
    }

    @Override
    public void handleRecipeCraftingAborted(RunePlayer player) {
        CampfireBlock progressBlock = inProgressBlocks.get(player.getUUID());
        inProgressBlocks.remove(player.getUUID());

        if (progressBlock != null) {
            RuneQuestPlugin.getBlockManager().unregisterBlock(progressBlock);
        }

    }

}
