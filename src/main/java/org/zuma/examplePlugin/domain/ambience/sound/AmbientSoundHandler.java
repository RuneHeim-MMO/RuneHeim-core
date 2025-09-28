package org.zuma.examplePlugin.domain.ambience.sound;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.util.ArrayList;
import java.util.List;

public class AmbientSoundHandler {
    private final List<AmbientSound> ambientSounds;

    public AmbientSoundHandler() {
        this.ambientSounds = new ArrayList<>();
    }

    public void registerSoundsFromRepository(AmbientSoundRepository repository) {
        repository.getAll().forEach(this::registerAmbientSound);
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Registered " + ambientSounds.size() + " ambient sounds.");
    }

    public void registerAmbientSound(AmbientSound sound) {
        this.ambientSounds.add(sound);
    }


    public void startLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                ambientSounds.forEach(AmbientSound::tick);
            }
        }, 0L, 20L);

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Started ambient sound loop.");
    }


}
