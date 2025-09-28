package org.zuma.examplePlugin.core.constants;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;

public enum MusicType {
    ROGUES_HAVEN_THEME("runequest.rogues_haven.music", 197, 0.25F),
    SAHARIJIN_THEME("runequest.saharijin.music", 245, 0.1F),
    SAHARIJIN_ARENA_THEME("runequest.saharijin.arena.music", 73, 0.1F),
    ZARALI_THEME("runequest.zarali.music", 175, 0.1F),
    SAKURMEER_THEME("runequest.sakurmeer.music", 164, 0.05F),
    DRAKKAR_THEME("runequest.drakkar.music", 249, 0.05F),
    GLACIEM_THEME("runequest.glaciem.music", 96, 0.1F),
    AETHERIS_THEME("runequest.aetheris.music", 165, 0.1F),
    INFERNAK_THEME("runequest.infernak.music", 81, 0.1F),
    SWAMP_THEME("runequest.swamp.music", 126, 0.2F);

    private final String soundKey;
    private final float volume, pitch;
    private final int durationSeconds;

    MusicType(String soundKey, int durationSeconds, float volume) {
        this.soundKey = soundKey;
        this.volume = volume;
        this.pitch = 1F;
        this.durationSeconds = durationSeconds + 10; // Add 10 seconds buffer
    }


    public void play(Player targetPlayer) {
        SoundCategory category = SoundCategory.RECORDS;
        playSound(targetPlayer, soundKey, category);
    }

    private void playSound(Player player, String sound, SoundCategory category) {
        if (player != null) {
            if (sound instanceof String customKey) {
                player.playSound(player.getLocation(), customKey, category, volume, pitch);
            }
        }
    }

    public static void stopMusic(RunePlayer player) {
        player.asBukkitPlayer().stopSound(SoundCategory.RECORDS);
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }
}
