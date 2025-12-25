package org.zuma.examplePlugin.core.handlers.core;

import com.ticxo.modelengine.api.events.ModelRegistrationEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

public class ModelEngineWaiter implements Listener {
    private final static int THRESHOLD = 5;
    private final Runnable callback;
    private int loadedModels = 0;
    private boolean ranCallback = false;

    public ModelEngineWaiter(Runnable cb, int timeoutSeconds, Runnable timeoutCallback) {
        this.callback = cb;

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (!ranCallback) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[ModelEngineWaiter] ModelEngine did not load all models in time, running timeout callback.");
                timeoutCallback.run();
            }
        }, timeoutSeconds * 20L);

    }

    @EventHandler
    public void onModelRegister(ModelRegistrationEvent event) {
        loadedModels++;
        if (loadedModels > THRESHOLD && !ranCallback) {
            ranCallback = true;

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[ModelEngineWaiter] ModelEngine should be loaded by now, running callback.");
                this.callback.run();
            }, 2);
        } else {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[ModelEngineWaiter] Waiting for ModelEngine to load all of the models. Current/Threshold: " + loadedModels + "/" + THRESHOLD);
        }
    }
}
