package org.zuma.examplePlugin.core.permissions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public enum Permission {
    WORLD_MANIPULATION(null),
    ITEM_WATCHDOG_BYPASS(null),
    COOLDOWN_BYPASS(null),
    BLOCKED_CMD_BYPASS(null),
    INTERACTABLE_SPAWN(null),
    DEBUG_COMMAND(null),

    CHAT_PROXIMITY_BYPASS(List.of("runeheim.chat", "runeheim.chat.proximity", "runeheim.chat.proximity.server", "runeheim.chat.proximity.network")),
    CHAT_PROFANITY_BYPASS(List.of("runeheim.chat.profanity")),
    CHAT_COOLDOWN_BYPASS(List.of("runeheim.chat.cooldown")),

    STAFF_MOVEMENT(List.of(
            "essentials.fly", "essentials.back", "essentials.back.ondeath",
            "essentials.back.onteleport", "essentials.vanish",
            "essentials.speed", "essentials.tp", "essentials.tp.others",
            "essentials.tp.position", "essentials.tpall", "essentials.tphere",
            "essentials.tppos", "essentials.warp", "essentials.warp.list")),

    STAFF_MESSAGING(List.of("essentials.broadcast")),

    STAFF_ITEM_SPAWN(List.of(
            "essentials.gamemode", "essentials.gamemode.all", "essentials.clearinventory",
            "essentials.invsee", "essentials.invsee.modify")),

    STAFF_MODERATION(List.of(
            "essentials.invsee", "essentials.invsee.equip", "essentials.joinfullserver",
            "essentials.kick", "essentials.socialspy", "essentials.time", "essentials.time.set",
            "essentials.kick.notify", "essentials.ban.notify", "essentials.list")),

    PLAYER_DEFAULTS(List.of(
            "essentials.silentjoin", "essentials.silentquit", "runeheim.msg"));


    private final List<String> nodes;

    Permission(@Nullable List<String> nodes) {
        this.nodes = nodes != null ? nodes : new ArrayList<>();
    }

    public List<String> getNodes() {
        return nodes;
    }
}
