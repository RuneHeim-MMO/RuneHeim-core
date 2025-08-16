package org.zuma.examplePlugin.events.player;

import org.bukkit.event.Cancellable;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.redis.RedisEvent;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.UUID;

public class RunePlayerLevelUpEvent extends RedisEvent<RunePlayerLevelUpEvent.Payload> implements Cancellable {
    private final RunePlayer player;
    private final SkillType type;
    private final int newLevel;
    private final XpSource source;
    private boolean cancelled;

    public static class Payload extends RedisPayload {
        public String playerName;
        public UUID playerUUID;
        public SkillType skillType;
        public int newLevel;
    }

    public RunePlayerLevelUpEvent(RunePlayer player, SkillType type, int newLevel, XpSource source) {
        this.player = player;
        this.type = type;
        this.newLevel = newLevel;
        this.source = source;
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public int getNewLevel() {
        return newLevel;
    }

    @Override
    public Payload composePayload() {
        Payload payload = new Payload();

        payload.playerName = this.player.asBukkitPlayer().getName();
        payload.playerUUID = this.player.getUUID();
        payload.skillType = this.type;
        payload.newLevel = this.newLevel;

        return payload;

    }

}
