package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.Quest;

import java.time.Duration;

public class QuestStartEffect implements AnimatedEffect {
    private final RunePlayer player;
    private final Quest quest;

    public QuestStartEffect(RunePlayer player, Quest quest) {
        this.player = player;
        this.quest = quest;

    }

    @Override
    public void playAt(Location location, double time) {
        if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) return;

        if (time == 0.0) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text(quest.getName(), Color.ACCENT.asColor()),
                    Component.text("Quest has been started!", Color.SUCCESS.asColor()),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(4, 5), Duration.ofSeconds(3)
                    )
            ));

            SoundType.PLAYER_QUEST_START.play(player.asBukkitPlayer().getLocation(), player.asBukkitPlayer(), -0.3f);
        }

        if (time == 5.0 && player.asBukkitPlayer() != null && player.asBukkitPlayer().isOnline())
            SoundType.PLAYER_QUEST_START.play(player.asBukkitPlayer().getLocation(), player.asBukkitPlayer(), -0.2f);

        if (time == 9.0 && player.asBukkitPlayer() != null && player.asBukkitPlayer().isOnline())
            SoundType.PLAYER_QUEST_START.play(player.asBukkitPlayer().getLocation(), player.asBukkitPlayer(), -0.4f);
    }

    @Override
    public int getDurationTicks() {
        return 10;
    }

}
