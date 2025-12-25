package org.zuma.examplePlugin.events.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.settings.PlayerSetting;

public class PlayerSettingUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final PlayerSetting setting;
    private final boolean newValue;

    public PlayerSettingUpdateEvent(RunePlayer player, PlayerSetting setting, boolean newValue) {
        this.player = player;
        this.setting = setting;
        this.newValue = newValue;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    public PlayerSetting getSetting() {
        return setting;
    }

    public boolean getNewValue() {
        return newValue;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }


}
