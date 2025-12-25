package org.zuma.examplePlugin.events.item;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class PlayerItemToBeGiven extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final ItemStack itemStack;

    public PlayerItemToBeGiven(RunePlayer player, ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack;
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

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }


    public ItemStack getItemStack() {
        return itemStack;
    }
}
