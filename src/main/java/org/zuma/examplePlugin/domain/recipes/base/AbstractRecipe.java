package org.zuma.examplePlugin.domain.recipes.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
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
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;

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
    private final List<SkillXpRecord> successXp;


    public AbstractRecipe(int id, ItemStack displayItemstack,
                          RecipeType type, int craftTimeMs, List<SkillRequirement> requirements,
                          List<ItemRecord> ingredients, ItemRecord successResult, @Nullable ItemRecord failResult, List<SkillXpRecord> successXp) {
        this.id = id;
        this.displayItemstack = displayItemstack;
        this.type = type;
        this.requirements = requirements;
        this.ingredients = ingredients;
        this.craftTimeMs = craftTimeMs;
        this.successResult = successResult;
        this.failResult = failResult;
        this.successXp = successXp;
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

    public void onCraft(RunePlayer player, int craftAmount) {
        this.successXp.forEach(skill -> {
            player.addShareableXp(skill.type(), skill.xp());
        });
    }

    public ItemStack getDisplayItemstack(RunePlayer player, ItemRegistry registry) {
        ItemStack newItem = new ItemStack(displayItemstack);
        ItemMeta meta = newItem.getItemMeta();
        List<Component> itemLore = new ArrayList<>();
        int chanceOfSuccess = Math.toIntExact(getChanceOfSuccess(player));
        int craftTimeSeconds = Math.toIntExact(craftTimeMs / 1000);

        itemLore.add(Component.text("✔ " + craftTimeSeconds + " sec crafting time", Colors.coloredProgressValue(craftTimeSeconds, 0, 120, true)));
        itemLore.add(Component.text("⏱ " + chanceOfSuccess + "% chance of success", Colors.coloredProgressValue(chanceOfSuccess, 0, 100, false)));

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
        itemLore.add(Component.text(successResult.getAmount() + "x ", Colors.ITEM_LABEL.asColor()).append(displayItemstack.displayName()));
        itemLore.add(Component.text(""));

        meta.lore(itemLore.stream().map(lore -> lore.decoration(TextDecoration.ITALIC, false)).toList());
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
