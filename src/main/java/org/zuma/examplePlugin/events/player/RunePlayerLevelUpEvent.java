package org.zuma.examplePlugin.events.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

public class RunePlayerLevelUpEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final SkillType type;
    private final int oldLevel, newLevel;
    private final XpSource source;

    public RunePlayerLevelUpEvent(RunePlayer player, SkillType type, int oldLevel, int newLevel, XpSource source) {
        this.player = player;
        this.type = type;
        this.newLevel = newLevel;
        this.oldLevel = oldLevel;
        this.source = source;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    public SkillType getType() {
        return type;
    }

    public XpSource getSource() {
        return source;
    }

    public int getNewLevel() {
        return newLevel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public int getOldLevel() {
        return oldLevel;
    }
}
