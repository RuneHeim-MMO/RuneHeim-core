package org.zuma.examplePlugin.domain.player.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class UsernameRenderer {
    private final RunePlayer player;

    public UsernameRenderer(RunePlayer player) {
        this.player = player;
    }

    public void updateTabUsername() {
        int cmb = player.getCombatLvl();
        Component levelComponent = TextUtils.getLevelGradientComponent(cmb);

        int width = 3;
        String numStr = String.valueOf(cmb);
        int spacesNeeded = width - numStr.length();
        String spaces = " ".repeat(Math.max(0, spacesNeeded));

        Component levelPrefix = Component.text("[Lv.", levelComponent.color()).append(levelComponent).append(Component.text("]"));
        Component username = Component.text(player.asBukkitPlayer().getName(), NamedTextColor.WHITE);

        player.asBukkitPlayer().playerListName(
                Component.text(" ")
                        .append(levelPrefix)
                        .append(Component.text(spaces + " • "))
                        .append(username)
                        .append(Component.text(" "))
                        .append(player.getRole().getPrefix())
        );
    }
}
