package org.zuma.examplePlugin.domain.entity;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.events.state.StateDeleteEvent;

import java.util.UUID;

public class EntitySessionManager implements Listener {
    public static final UUID SESSION_UUID = UUID.randomUUID();
    public static final NamespacedKey SESSION_KEY =
            new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.SESSION_UUID.toString());

    public static void tagEntity(Entity entity) {
        entity.getPersistentDataContainer().set(
                EntitySessionManager.SESSION_KEY,
                PersistentDataType.STRING,
                EntitySessionManager.SESSION_UUID.toString()
        );
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            String storedSessionId = pdc.get(EntitySessionManager.SESSION_KEY, PersistentDataType.STRING);

            if (entity instanceof ArmorStand) {
                return;
            }

            if (storedSessionId != null && !storedSessionId.equals(EntitySessionManager.SESSION_UUID.toString())) {

                if (entity instanceof Item) {
                    String stateUUID = pdc.get(PersistenceDataKey.STATE_UUID.key(RuneQuestPlugin.getInstance()), PersistentDataType.STRING);

                    if (stateUUID != null) {
                        Bukkit.getServer().getPluginManager().callEvent(new StateDeleteEvent(UUID.fromString(stateUUID)));
                    }
                }

                entity.remove();
            }
        }
    }

}
