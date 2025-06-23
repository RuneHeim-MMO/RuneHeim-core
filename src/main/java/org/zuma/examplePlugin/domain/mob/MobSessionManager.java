package org.zuma.examplePlugin.domain.mob;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;

import java.util.UUID;

public class MobSessionManager implements Listener {
    public static final UUID SESSION_UUID = UUID.randomUUID();
    public static final NamespacedKey SESSION_KEY =
            new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.SESSION_UUID.toString());

    public static void tagEntity(Entity entity) {
        entity.getPersistentDataContainer().set(
                MobSessionManager.SESSION_KEY,
                PersistentDataType.STRING,
                MobSessionManager.SESSION_UUID.toString()
        );
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            String storedSessionId = pdc.get(MobSessionManager.SESSION_KEY, PersistentDataType.STRING);

            if (storedSessionId != null && !storedSessionId.equals(MobSessionManager.SESSION_UUID.toString())) {
                entity.remove();
            }
        }
    }

}
