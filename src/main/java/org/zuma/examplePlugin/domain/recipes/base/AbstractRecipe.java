package org.zuma.examplePlugin.domain.recipes.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.handlers.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.IngredientRecord;
import org.zuma.examplePlugin.domain.recipes.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipes.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRecipe implements SkillRestrictable {
    private final int id;
    private final RecipeType type;
    private final ItemStack displayItemstack;
    private final List<SkillRequirement> requirements;
    private final List<IngredientRecord> ingredients;
    private final int craftTimeMs;


    @Nullable
    private final ItemRecord failResult;
    @Nullable
    private final ItemRecord successResult;
    private final List<SkillXpRecord> successXp;


    public AbstractRecipe(ItemStack displayItemstack, AbstractRecipeMeta meta) {
        this.id = meta.id();
        this.displayItemstack = displayItemstack;
        this.type = meta.type();
        this.requirements = meta.requirements();
        this.ingredients = meta.ingredients();
        this.craftTimeMs = meta.craftTimeMs();
        this.successResult = meta.successResult();
        this.failResult = meta.failResult();
        this.successXp = meta.successXp();
    }

    public @NotNull List<SkillRequirement> getRequirements() {
        return requirements;
    }

    public RecipeType getType() {
        return type;
    }

    public List<IngredientRecord> getIngredients() {
        return ingredients;
    }

    public int getCraftTimeMs() {
        return craftTimeMs;
    }

    @Nullable
    public ItemRecord getSuccessResult() {
        return successResult;
    }

    public abstract boolean isCraftSuccessful(RunePlayer player);

    public abstract int getChanceOfSuccess(RunePlayer player);

    public void onCraftFinish(RunePlayer player, int craftAmount) {
        this.successXp.forEach(skill -> {
            player.distributeXp(skill.type(), skill.xp() * craftAmount);
        });
    }

    private Component getLoreForRequiredItem(ItemRegistry registry, ItemRecord record, RunePlayer player) {
        AbstractItemType ingredient = registry.getItem(record.getItemId());

        if (ingredient == null) {
            RuneQuestPlugin.getRuneLogger().logWithStack(LogLevel.WARN, "Unknown item id! ID: " + record.getItemId());
            return null;
        }

        boolean playHasInInventory = PlayerUtils.hasItems(player, List.of(record));
        Color color = playHasInInventory ? Color.SUCCESS : Color.ERROR;

        return Component.text(record.getAmount() + "x " + ingredient.displayName).color(color.asColor());
    }

    public ItemStack getDisplayItemstack() {
        return this.displayItemstack;
    }

    public ItemStack getDisplayItemstack(RunePlayer player, ItemRegistry registry) {
        boolean playerMeetsSkillRequirements = player.meetsRequirements(this);

        ItemStack newItem = playerMeetsSkillRequirements ? displayItemstack.clone() : new ItemStack(Material.BARRIER);
        ItemMeta meta = newItem.getItemMeta();
        IllegalItemWatchdog.tagIllegalItem(meta);
        List<Component> itemLore = new ArrayList<>();
        int chanceOfSuccess = Math.toIntExact(getChanceOfSuccess(player));
        int craftTimeSeconds = Math.toIntExact(craftTimeMs / 1000);

        if (!playerMeetsSkillRequirements) {
            meta.displayName(Component.text(TextUtils.extractPlainText(displayItemstack.displayName()).replace("[", "").replace("]", ""), Color.ERROR_LIGHT.asColor()).decoration(TextDecoration.ITALIC, false));
        }

        itemLore.add(Component.text("✔ " + craftTimeSeconds + " sec crafting time", Color.coloredProgressValue(craftTimeSeconds, 0, 120, true)));
        itemLore.add(Component.text("⏱ " + chanceOfSuccess + "% chance of success", Color.coloredProgressValue(chanceOfSuccess, 0, 100, false)));

        List<ItemRecord> recipeIngredients = !ingredients.isEmpty() ? ingredients.stream()
                .filter(IngredientRecord::isConsumedOnCraft)
                .map(record -> new ItemRecord(record.itemId(), record.amount())).toList() : new ArrayList<>();

        List<ItemRecord> requiredTools = !ingredients.isEmpty() ? ingredients.stream()
                .filter(record -> !record.isConsumedOnCraft())
                .map(record -> new ItemRecord(record.itemId(), record.amount())).toList() : new ArrayList<>();


        if (!requiredTools.isEmpty()) {
            itemLore.add(Component.empty());
            itemLore.add(Component.text("Tools: ", Color.ITEM_LABEL.asColor()));

            requiredTools.forEach(record -> {
                Component loreItem = getLoreForRequiredItem(registry, record, player);

                if (loreItem != null)
                    itemLore.add(loreItem);
            });
        }

        if (!recipeIngredients.isEmpty()) {
            itemLore.add(Component.empty());
            itemLore.add(Component.text("Ingredients: ", Color.ITEM_LABEL.asColor()));

            recipeIngredients.forEach(record -> {
                Component loreItem = getLoreForRequiredItem(registry, record, player);

                if (loreItem != null)
                    itemLore.add(loreItem);
            });
        }


        if (!requirements.isEmpty()) {
            itemLore.add(Component.empty());
            itemLore.add(Component.text("Requirements: ", Color.ITEM_LABEL.asColor()));

            requirements.forEach(requirement -> {
                boolean playMeetsReq = requirement.playerMeetsRequirement(player);
                Color color = playMeetsReq ? Color.SUCCESS : Color.ERROR;

                itemLore.add(Component.text(requirement.type().getSymbol(), NamedTextColor.WHITE).append(
                        Component.text(" " + requirement.minimumLevel() + " " + requirement.type().getLabel()).color(color.asColor())));
            });

        }


        // Success result can be null for recipes like campfire recipe.
        int successCrafts = successResult != null ? successResult.getAmount() : 1;

        itemLore.add(Component.empty());
        itemLore.add(Component.text("Result: ", Color.ITEM_LABEL.asColor()));
        itemLore.add(Component.text(successCrafts + "x ", Color.ITEM_LABEL.asColor()).append(displayItemstack.displayName()));
        itemLore.add(Component.empty());

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

    public void handleRecipeCraftingSuccess(RunePlayer player) {

    }

    public void handleRecipeCraftingStart(RunePlayer player) {

    }

    public void handleRecipeCraftingAborted(RunePlayer player) {

    }
}
