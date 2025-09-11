package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.FireworkUtils;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.Quest;

import java.time.Duration;

public class QuestCompletedEffect implements AnimatedEffect {
    private final RunePlayer player;
    private final Quest quest;

    public QuestCompletedEffect(RunePlayer player, Quest quest) {
        this.player = player;
        this.quest = quest;
    }

    @Override
    public void playAt(Location location, double time) {
        SoundType.PLAYER_QUEST_COMPLETE.play(player, true);

        player.asBukkitPlayer().showTitle(Title.title(
                Component.text(quest.getName(), Color.ACCENT.asColor()),
                Component.text("Has been completed!", Color.SUCCESS.asColor()),
                Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(4, 5), Duration.ofSeconds(3)
                )
        ));
    }

    @Override
    public int getDurationTicks() {
        return 0;
    }

}
