package org.zuma.examplePlugin.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.constants.SkillType;
import org.zuma.examplePlugin.constants.XPSource;
import org.zuma.examplePlugin.player.RunePlayer;

public class RunePlayerGainedXPEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final SkillType type;
    private final int amount;
    private final XPSource source;
    private boolean cancelled;

    public RunePlayerGainedXPEvent(RunePlayer player, SkillType type, int amount, XPSource source) {
        this.player = player;
        this.type = type;
        this.amount = amount;
        this.source = source;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    public SkillType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public XPSource getSource() {
        return source;
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

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }


}
