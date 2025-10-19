package org.zuma.examplePlugin.events.player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

public class RunePlayerGainedXPEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final SkillType type;
    private final double amount;
    private final XpSource source;
    private boolean cancelled;

    public RunePlayerGainedXPEvent(RunePlayer player, SkillType type, double amount, XpSource source) {
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

    public double getAmount() {
        return amount;
    }

    public XpSource getSource() {
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

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


}
