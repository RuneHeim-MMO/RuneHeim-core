package org.zuma.examplePlugin.events.item;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class PlayerConsumableConsumeEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final AbstractItemType item;
    private final ItemStack itemStack;
    private final StateManager itemStateManager;
    private boolean cancelled;

    public PlayerConsumableConsumeEvent(RunePlayer player, AbstractItemType item, ItemStack itemStack, StateManager itemStateManager) {
        this.player = player;
        this.item = item;
        this.itemStack = itemStack;
        this.itemStateManager = itemStateManager;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    public Player getBukkitPlayer() {
        return player.asBukkitPlayer();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public AbstractItemType getItem() {
        return item;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public StateManager getItemStateManager() {
        return itemStateManager;
    }
}
