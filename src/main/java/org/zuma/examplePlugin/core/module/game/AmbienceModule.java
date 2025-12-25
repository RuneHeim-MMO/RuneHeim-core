package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.ambience.sound.AmbientSoundHandler;
import org.zuma.examplePlugin.domain.ambience.sound.AmbientSoundRepository;
import org.zuma.examplePlugin.domain.block.BlockManager;

public class AmbienceModule implements PluginModule {

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        AmbientSoundRepository ambientSoundRepository = new AmbientSoundRepository();
        AmbientSoundHandler ambientSoundHandler = new AmbientSoundHandler();

        ambientSoundHandler.registerSoundsFromRepository(ambientSoundRepository);
        ambientSoundHandler.startLoop();

        context.bind(AmbientSoundHandler.class, ambientSoundHandler);
    }


    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(BlockManager.class).onDisable();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
