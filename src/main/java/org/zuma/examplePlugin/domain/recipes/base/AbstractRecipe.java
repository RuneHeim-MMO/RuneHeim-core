package org.zuma.examplePlugin.domain.recipes.base;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.ArrayList;
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

    public abstract int getChanceOfSuccess(RunePlayer player);

    public ItemStack getDisplayItemstack(RunePlayer player, ItemRegistry registry) {
        ItemStack newItem = new ItemStack(displayItemstack);
        ItemMeta meta = newItem.getItemMeta();
        List<Component> itemLore = new ArrayList<>();

        if (!ingredients.isEmpty()) {
            itemLore.add(Component.text(""));
            itemLore.add(Component.text("Ingredients: ", Colors.ITEM_LABEL.asColor()));

            ingredients.forEach(record -> {
                AbstractItem ingredient = registry.getItem(record.getItemId());

                if (ingredient == null) {
                    RuneQuestPlugin.getRuneLogger().logWithStack(LogLevel.WARN, "Unknown item id! ID: " + record.getItemId());
                    return;
                }

                boolean playHasInInventory = PlayerUtils.hasItems(player, List.of(record));
                Colors color = playHasInInventory ? Colors.SUCCESS : Colors.ERROR;

                itemLore.add(Component.text(record.getAmount() + "x " + ingredient.displayName).color(color.asColor()));
            });
        }


        if (!requirements.isEmpty()) {
            itemLore.add(Component.text(""));
            itemLore.add(Component.text("Requirements: ", Colors.ITEM_LABEL.asColor()));

            requirements.forEach(requirement -> {
                boolean playMeetsReq = requirement.playerMeetsRequirement(player);
                Colors color = playMeetsReq ? Colors.SUCCESS : Colors.ERROR;

                itemLore.add(Component.text(requirement.getType().getSymbol() + " " + requirement.getMinimumLevel() + " " + requirement.getType().getLabel()).color(color.asColor()));
            });

        }

        itemLore.add(Component.text(""));
        itemLore.add(Component.text("Result: ", Colors.ITEM_LABEL.asColor()));
        itemLore.add(Component.text(successResult.getAmount() + "x ").append(displayItemstack.displayName()).color(Colors.ITEM_LABEL.asColor()));
        itemLore.add(Component.text(""));
        itemLore.add(Component.text("Chance of success: " + Math.toIntExact(getChanceOfSuccess(player)) + "%", Colors.ITEM_LABEL.asColor()));
        itemLore.add(Component.text("Crafting time: " + Math.toIntExact(craftTimeMs / 1000) + " seconds.", Colors.ITEM_LABEL.asColor()));


        meta.lore(itemLore);
        newItem.setItemMeta(meta);

        return newItem;
    }

    public int getId() {
        return id;
    }

    public @Nullable ItemRecord getFailResult() {
        return failResult;
    }
}
