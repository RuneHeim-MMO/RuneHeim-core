package org.zuma.examplePlugin.events.recipe;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;

public class PlayerCraftingFinishEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final AbstractRecipe recipe;
    private final int successCrafts, failedCrafts;
    private boolean cancelled;

    public PlayerCraftingFinishEvent(RunePlayer player, AbstractRecipe recipe, int successCrafts, int failedCrafts) {
        this.player = player;
        this.recipe = recipe;
        this.successCrafts = successCrafts;
        this.failedCrafts = failedCrafts;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public AbstractRecipe getRecipe() {
        return recipe;
    }

    public int getFailedCrafts() {
        return failedCrafts;
    }

    public int getSuccessCrafts() {
        return successCrafts;
    }
}