package org.zuma.examplePlugin.domain.recipe.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.impl.CampfireBlock;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipe.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.recipe.meta.v1.CampfireRecipeMeta;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

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


        mainRequirement = super.getSkillRequirements().stream()
                .filter(req -> req.type() == SkillType.FIREMAKING)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isCraftSuccessful(RunePlayer player) {
        return true;
       /* if (mainRequirement == null) {
            return true;
        }

        return ThreadLocalRandom.current().nextInt(0, 101) < getChanceOfSuccess(player);*/

    }

    @Override
    public int getChanceOfSuccess(RunePlayer player) {
        return 100;

        /* if (mainRequirement == null) {
            return 100;
        }

        return PlayerUtils.getCraftSuccessChance(player, mainRequirement, MINIMUM_SUCCESS_CHANCE);*/
    }

    @Override
    public boolean handleRecipeCraftingStart(RunePlayer player) {
        Location campfireLocation = PlayerUtils.trunacePlayerFeetLocation(player);
        Block blockBelow = campfireLocation.getBlock().getRelative(BlockFace.DOWN);

        if (!blockBelow.getType().isSolid() || campfireLocation.getBlock().getType() != Material.AIR || RuneQuestPlugin.getBlockManager().getBlockAt(campfireLocation.getBlock()) != null) {
            // Invalid location (block below not solid/block to be replaced is not air)
            HudService.showFailTitle(player, "You cannot place campfire here.");
            return false;
        }

        CampfireBlock campfire = new CampfireBlock(player.getUUID(), campfireLocation, initialName, burningName, soulCampfireSkin, burnTime, despawnDrop);
        inProgressBlocks.put(player.getUUID(), campfire);
        RuneQuestPlugin.getBlockManager().registerBlock(campfire);
        return true;
    }

    @Override
    public void handleRecipeCraftingSuccess(RunePlayer player) {
        CampfireBlock progressBlock = inProgressBlocks.get(player.getUUID());
        inProgressBlocks.remove(player.getUUID());

        if (progressBlock != null) {
            progressBlock.startFire();
            AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.CAMPFIRES_STARTED, 1);
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
