package org.zuma.examplePlugin.domain.player.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Objects;

public class ScoreboardUsernameRenderer {
    private final RunePlayer player;

    public ScoreboardUsernameRenderer(RunePlayer player) {
        this.player = player;
    }

    public void updateTabUsername() {
        int cmb = player.getCombatLvl();
        Component levelComponent = TextUtils.getLevelGradientComponent(cmb);
        Component supporterPrefix = !Objects.equals(player.getRole().getSupporterRoleIcon(player), "") ? Component.text(player.getRole().getSupporterRoleIcon(player)) : Component.empty();
        Component levelPrefix = Component.text("[Lv.", levelComponent.color()).append(levelComponent).append(Component.text("]"));
        Component username = Component.text(player.asBukkitPlayer().getName(), NamedTextColor.WHITE);

        player.asBukkitPlayer().playerListName(
                Component.text(" ")
                        .append(supporterPrefix)
                        .append(levelPrefix)
                        .append(Component.text(" • "))
                        .append(username)
                        .append(Component.text(" "))
                        .append(player.getRole().getPrefix(player))
        );
    }
}
