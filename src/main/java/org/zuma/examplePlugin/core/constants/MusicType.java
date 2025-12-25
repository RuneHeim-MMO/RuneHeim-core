package org.zuma.examplePlugin.core.constants;

import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public enum MusicType {
    ROGUES_HAVEN_THEME("runequest.rogues_haven.music", 197, 0.20F,20),
    SAHARIJIN_THEME("runequest.saharijin.music", 245, 0.1F,20),
    SAHARIJIN_ARENA_THEME("runequest.saharijin.arena.music", 73, 0.1F,5),
    ZARALI_THEME("runequest.zarali.music", 175, 0.1F,20),
    SAKURMEER_THEME("runequest.sakurmeer.music", 164, 0.05F,20),
    DRAKKAR_THEME("runequest.drakkar.music", 249, 0.05F,20),
    GLACIEM_THEME("runequest.glaciem.music", 96, 0.1F,20),
    AETHERIS_THEME("runequest.aetheris.music", 165, 0.1F,20),
    INFERNAK_THEME("runequest.infernak.music", 81, 0.1F,20),
    SWAMP_THEME("runequest.swamp.music", 126, 0.2F,20),
    BRINEPORT_THEME("runequest.brineport.music", 71, 0.05F,20),
    ELDARWYN_THEME("runequest.eldarwyn.music", 133, 0.05F,20),
    ISLENOR_THEME("runequest.islenor.music", 159, 0.05F,20),
    SPOREFELL_THEME("runequest.sporefell.music", 189, 0.05F,20),
    THORNBARK_THEME("runequest.thornbark.music", 131, 0.05F,20),
    TUTORIAL_ISLAND_THEME("runequest.tutorial_island.music", 109, 0.1F,20),
    VALDRIN_THEME("runequest.valdrin.music", 157, 0.2F,20),
    SHIP_BUFFER_THEME("runequest.ship.music", 106, 0.1F,20);

    private final String soundKey;
    private final float volume, pitch;
    private final int durationSeconds;

    MusicType(String soundKey, int durationSeconds, float volume, int loopDelaySeconds) {
        this.soundKey = soundKey;
        this.volume = volume;
        this.pitch = 1F;
        this.durationSeconds = durationSeconds + 5 + loopDelaySeconds; // Add 5 seconds buffer
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
        if (player.asBukkitPlayer() != null) {
            player.asBukkitPlayer().stopSound(SoundCategory.RECORDS);
        }
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }
}
