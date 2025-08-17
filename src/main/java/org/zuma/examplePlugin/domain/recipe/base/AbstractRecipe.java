package org.zuma.examplePlugin.domain.recipe.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.equipable.Tool;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;
import org.zuma.examplePlugin.domain.player.actions.ProgressBarAction;
import org.zuma.examplePlugin.domain.recipe.IngredientRecord;
import org.zuma.examplePlugin.domain.recipe.constants.RecipeType;
import org.zuma.examplePlugin.domain.recipe.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;
import org.zuma.examplePlugin.events.recipe.PlayerCraftingFinishEvent;
import org.zuma.examplePlugin.events.recipe.PlayerCraftingStartEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.zuma.examplePlugin.core.utils.ItemUtils.multiplyItemRecords;

public abstract class AbstractRecipe implements SkillRestrictable {
    private final int id;
    private final RecipeType type;
    private final ItemStack displayItemstack;
    private final List<SkillRequirement> requirements;
    private final List<IngredientRecord> ingredients;
    private final int craftTimeMs;

    @Nullable
    private final Long cost;
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
        this.cost = meta.cost();
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
        AbstractItemType ingredient = registry.getType(record.getItemId());

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
                .filter(record -> registry.getType(record.itemId()) instanceof org.zuma.examplePlugin.domain.item.impl.Material)
                .map(record -> new ItemRecord(record.itemId(), record.amount())).toList() : new ArrayList<>();

        List<ItemRecord> requiredTools = !ingredients.isEmpty() ? ingredients.stream()
                .filter(record -> registry.getType(record.itemId()) instanceof Tool)
                .map(record -> new ItemRecord(record.itemId(), record.amount())).toList() : new ArrayList<>();


        if (!requiredTools.isEmpty()) {
            itemLore.add(Component.empty());
            itemLore.add(Component.text("Tools: ", Color.ITEM_CATEGORY_LABEL.asColor()));

            requiredTools.forEach(record -> {
                Component loreItem = getLoreForRequiredItem(registry, record, player);

                if (loreItem != null)
                    itemLore.add(loreItem);
            });
        }

        if (!recipeIngredients.isEmpty()) {
            itemLore.add(Component.empty());
            itemLore.add(Component.text("Ingredients: ", Color.ITEM_CATEGORY_LABEL.asColor()));

            recipeIngredients.forEach(record -> {
                Component loreItem = getLoreForRequiredItem(registry, record, player);

                if (loreItem != null)
                    itemLore.add(loreItem);
            });
        }


        if (!requirements.isEmpty()) {
            itemLore.add(Component.empty());
            itemLore.add(Component.text("Requirements: ", Color.ITEM_CATEGORY_LABEL.asColor()));

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
        itemLore.add(Component.text("Result: ", Color.ITEM_CATEGORY_LABEL.asColor()));
        itemLore.add(Component.text(successCrafts + "x ", Color.ITEM_CATEGORY_LABEL.asColor()).append(displayItemstack.displayName()));

        if (cost != null && cost > 0) {
            itemLore.add(Component.empty());
            itemLore.add(Component.text("Cost: ", Color.ITEM_CATEGORY_LABEL.asColor()));
            itemLore.add(CurrencyUtils.getCoinsCurrencyComponent(cost));
        }

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

    public Long getRecipeCost() {
        if (this.cost == null) return 0L;

        return this.cost;
    }

    public @NotNull List<ItemRecord> getRequiredIngredients(int times) {
        List<ItemRecord> totalIngredients = new ArrayList<>();
        List<IngredientRecord> recipeIngredients = this.getIngredients();

        recipeIngredients.forEach(ingredientRecord -> {
            if (!ingredientRecord.isConsumedOnSuccess() && !ingredientRecord.isConsumedOnFail()) {
                // It's a tool, or the quantity does not matter - so, set as required quantity to be 1;
                totalIngredients.add(new ItemRecord(ingredientRecord.itemId(), ingredientRecord.amount()));
            } else {
                totalIngredients.add(new ItemRecord(ingredientRecord.itemId(), ingredientRecord.amount() * times));
            }

        });

        return totalIngredients;
    }

    public boolean isCraftAffordable(RunePlayer player, int times) {
        return player.getPouchCoins() >= times * getRecipeCost();
    }

    public Optional<Component> getCraftBlockReason(RunePlayer player, int times) {
        Component resultComponent = getDisplayItemstack(player, RuneQuestPlugin.getItemRegistry()).displayName().color(NamedTextColor.WHITE);

        if (!isCraftAffordable(player, times)) {
            return Optional.of(Component.text("You do not have enough money to " + type.getVerb().toLowerCase() + " ")
                    .append(resultComponent)
                    .append(Component.text("! You need at least "))
                    .append(CurrencyUtils.getCoinsCurrencyComponent(getRecipeCost() * times)).append(Component.text(" coins.")));
        }

        if (player.isBusy()) {
            return Optional.of(Component.text("You are already busy with something else!"));
        }

        if (!player.meetsRequirements(this)) {
            return Optional.of(Component.text("You do not meet requirements to " + type.getVerb().toLowerCase() + " ").append(resultComponent));
        }

        if (!PlayerUtils.hasItems(player, getRequiredIngredients(times))) {
            return Optional.of(Component.text("You do not have required ingredients to " + type.getVerb().toLowerCase() + " " + times + "x ").append(resultComponent));
        }

        return Optional.empty();
    }

    public boolean craftRecipe(RunePlayer player, int times) throws IllegalStateException {
        if (times <= 0) {
            return false;
        }

        Component resultComponent = this.getDisplayItemstack(player, RuneQuestPlugin.getItemRegistry()).displayName().color(NamedTextColor.WHITE);
        String recipeResultNameString = times + "x " + TextUtils.extractPlainText(resultComponent);
        Optional<Component> blockReason = getCraftBlockReason(player, times);
        long totalCost = this.getRecipeCost() * times;

        if (blockReason.isPresent()) {
            HudService.sendFailMessage(player, blockReason.get());
            return false;
        }

        PlayerCraftingStartEvent event = new PlayerCraftingStartEvent(player, this, times);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return false;
        }

        type.getStartSound().play(player, true);
        this.handleRecipeCraftingStart(player);

        ActionParams params = new ActionParams.Builder()
                .durationMs(this.craftTimeMs * times)
                .actionLabel(type.getContinuousVerb() + " " + recipeResultNameString)
                .successActionLabel("")
                .stepSound(type.getStepSound())
                .successSound(type.getFinishSound())
                .onSuccess(() -> executeCraft(player, times, totalCost, resultComponent))
                .onFailure(() -> this.handleRecipeCraftingAborted(player))
                .build();

        player.startAction(new ProgressBarAction(player), params);

        return true;
    }

    private void executeCraft(RunePlayer player, int times, long totalCost, Component resultComponent) {
        Optional<Component> blockReason = getCraftBlockReason(player, times);
        if (blockReason.isPresent()) {
            HudService.sendFailMessage(player, blockReason.get());
            return;
        }

        int successCrafts = getSuccessCraftsAmount(player, times);
        int failedCrafts = times - successCrafts;

        if (!removeCraftingIngredients(player, successCrafts, failedCrafts)) return;

        PlayerCraftingFinishEvent finishEvent = new PlayerCraftingFinishEvent(player, this, successCrafts, failedCrafts);
        Bukkit.getPluginManager().callEvent(finishEvent);
        if (finishEvent.isCancelled()) {
            handleRecipeCraftingAborted(player);
            return;
        }

        if (totalCost > 0) {
            player.setPouchCoins(player.getPouchCoins() - totalCost, false);
            HudService.showCoinsChangeBubble(player, -totalCost);
        }

        if (successCrafts > 0) {
            applySuccess(player, successCrafts, resultComponent);
        } else if (times == 1) {
            handleRecipeCraftingAborted(player);
        }

        if (failedCrafts > 0) {
            applyFailure(player, failedCrafts, resultComponent);
        }
    }

    private boolean removeCraftingIngredients(RunePlayer player, int successCrafts, int failedCrafts) {

        if (successCrafts > 0) {
            List<ItemRecord> successIngredients = multiplyItemRecords(
                    ingredients.stream().filter(IngredientRecord::isConsumedOnSuccess)
                            .map(record -> new ItemRecord(record.itemId(), record.amount()))
                            .toList(),
                    successCrafts
            );

            if (!PlayerUtils.removeItems(player, successIngredients)) {
                HudService.sendFailMessage(player, Component.text("Failed removing items from player inventory."));
                handleRecipeCraftingAborted(player);
                return false;
            }

        }

        if (failedCrafts > 0) {
            List<ItemRecord> failIngredients = multiplyItemRecords(
                    ingredients.stream().filter(IngredientRecord::isConsumedOnFail)
                            .map(record -> new ItemRecord(record.itemId(), record.amount()))
                            .toList(),
                    failedCrafts
            );


            if (!PlayerUtils.removeItems(player, failIngredients)) {
                HudService.sendFailMessage(player, Component.text("Failed removing items from player inventory."));
                handleRecipeCraftingAborted(player);
                return false;
            }
        }

        return true;
    }

    private void applySuccess(RunePlayer player, int successCrafts, Component resultComponent) {
        if (successResult != null) {
            player.giveItemOrDrop(new ItemRecord(
                    successResult.getItemId(),
                    successResult.getAmount() * successCrafts
            ));
        }

        onCraftFinish(player, successCrafts);
        handleRecipeCraftingSuccess(player);

        player.asBukkitPlayer().sendMessage(
                Component.text("Successfully " + type.getPastTenseVerb().toLowerCase() + " " + successCrafts + "x ")
                        .append(resultComponent)
                        .color(Color.SUCCESS.asColor())
        );

        type.getFinishSound().play(player, true);
    }

    private void applyFailure(RunePlayer player, int failedCrafts, Component resultComponent) {
        if (failResult != null) {
            int amount = (successResult != null ? successResult.getAmount() : 1) * failedCrafts;
            player.giveItemOrDrop(new ItemRecord(failResult.getItemId(), amount));
        }

        if (failedCrafts == 1) {
            player.asBukkitPlayer().sendMessage(Icon.ERROR.asComponent().append(
                            Component.text("Failed to " + type.getVerb().toLowerCase() + " a ")
                                    .append(resultComponent)
                                    .color(Color.ERROR.asColor())
                    )
            );
        }
    }


    private int getSuccessCraftsAmount(RunePlayer player, int craftAmount) {
        int successCrafts = 0;

        for (int i = 0; i < craftAmount; i++) {
            if (this.isCraftSuccessful(player)) {
                successCrafts++;
            }
        }

        return successCrafts;
    }

    public boolean matchesItemPair(AbstractItemType first, AbstractItemType second) {
        boolean hasFirst = false, hasSecond = false;
        for (IngredientRecord record : this.getIngredients()) {
            int id = record.itemId().asInt();
            if (id == first.id) hasFirst = true;
            if (id == second.id) hasSecond = true;
            if (hasFirst && hasSecond) return true;
        }
        return false;
    }

    public void handleRecipeCraftingSuccess(RunePlayer player) {

    }

    public void handleRecipeCraftingStart(RunePlayer player) {

    }

    public void handleRecipeCraftingAborted(RunePlayer player) {

    }
}
