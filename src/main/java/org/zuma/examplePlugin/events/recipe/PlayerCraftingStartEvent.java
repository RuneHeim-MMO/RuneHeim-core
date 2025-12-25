package org.zuma.examplePlugin.events.recipe;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;

public class PlayerCraftingStartEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final AbstractRecipe recipe;
    private final int multiplier;
    private boolean cancelled;

    public PlayerCraftingStartEvent(RunePlayer player, AbstractRecipe recipe, int multiplier) {
        this.player = player;
        this.recipe = recipe;
        this.multiplier = multiplier;
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

    public int getMultiplier() {
        return multiplier;
    }
}