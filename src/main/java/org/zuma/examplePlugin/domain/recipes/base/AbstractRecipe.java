package org.zuma.examplePlugin.domain.recipes.base;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.List;

public abstract class AbstractRecipe implements SkillRestrictable {
    private final int id;
    private final RecipeType type;
    private final ItemStack displayItemstack;
    private final List<SkillRequirement> requirements;
    private final List<ItemRecord> ingredients;
    private final int craftTimeMs;
    private final ItemRecord successResult;

    @Nullable
    private final ItemRecord failResult;


    public AbstractRecipe(int id, ItemStack displayItemstack, RecipeType type, int craftTimeMs, List<SkillRequirement> requirements, List<ItemRecord> ingredients, ItemRecord successResult, @Nullable ItemRecord failResult) {
        this.id = id;
        this.displayItemstack = displayItemstack;
        this.type = type;
        this.requirements = requirements;
        this.ingredients = ingredients;
        this.craftTimeMs = craftTimeMs;
        this.successResult = successResult;
        this.failResult = failResult;
    }

    public @NotNull List<SkillRequirement> getRequirements() {
        return requirements;
    }

    public RecipeType getType() {
        return type;
    }

    public List<ItemRecord> getIngredients() {
        return ingredients;
    }

    public int getCraftTimeMs() {
        return craftTimeMs;
    }

    public ItemRecord getSuccessResult() {
        return successResult;
    }

    public abstract boolean isCraftSuccessful(RunePlayer player);

    public ItemStack getDisplayItemstack() {
        return displayItemstack; // TODO: lore crap
    }

    public int getId() {
        return id;
    }

    public @Nullable ItemRecord getFailResult() {
        return failResult;
    }
}
