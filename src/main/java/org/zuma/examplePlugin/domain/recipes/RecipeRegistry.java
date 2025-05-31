package org.zuma.examplePlugin.domain.recipes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.base.SmeltingRecipe;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeId;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeRegistry {
    private final ItemRegistry itemRegistry;
    private final HashMap<Integer, AbstractRecipe> recipes = new HashMap<>();

    public RecipeRegistry(ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;

        this.registerRecipe(RecipeId.TEST_RECIPE.getId(), RecipeType.INVENTORY_RECIPE, 3000,
                List.of(new SkillRequirement(SkillType.COOKING, 1)),
                List.of(new ItemRecord(ItemId.IRON_ORE, 1)),
                new ItemRecord(ItemId.IRON_BAR, 1),
                null);
    }

    public List<AbstractRecipe> getRecipesByType(RecipeType type) {
        return recipes.values().stream().filter(recipe -> recipe.getType() == type).toList();
    }

    public @Nullable AbstractRecipe getRecipeById(int id) {
        return recipes.get(id);
    }

    public void registerRecipe(int id, RecipeType type, int craftTimeMs, List<SkillRequirement> requirements, List<ItemRecord> ingredients, ItemRecord successResult, @Nullable ItemRecord failResult) {
        AbstractItem item = this.itemRegistry.getItem(successResult.getItemId());

        if (item == null) {
            RuneQuestPlugin.getRuneLogger().logWithStack(LogLevel.WARN, "Cannot register " + type.name() + " recipe. Recipe result ItemRecord id is invalid.");
            return;
        }

        ItemStack itemStack = item.asItemStack();
        ItemMeta meta = itemStack.getItemMeta();

        meta.lore(new ArrayList<>());
        itemStack.setItemMeta(meta);

        this.recipes.put(id,
                new SmeltingRecipe(id, itemStack, craftTimeMs, requirements, ingredients, successResult, failResult));
    }
}
