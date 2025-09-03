package org.zuma.examplePlugin.domain.item.state;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemHandler;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.events.state.StateCreatedDefaultEvent;
import org.zuma.examplePlugin.events.state.StateLoadedEvent;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StateManager {
    private final ConcurrentHashMap<UUID, AbstractState> itemStates = new ConcurrentHashMap<>(); // TODO: PERSISTENCE! delete states on item despawn & handle lifecycle properly
    private final StateFactory factory;
    private final ItemHandler itemHandler;
    private final StateRepository repository;
    private final Gson gson = new Gson();

    public StateManager(StateFactory factory, ItemHandler itemHandler, StateRepository repository) {
        this.factory = factory;
        this.itemHandler = itemHandler;
        this.repository = repository;
    }

    @Nullable
    public UUID getItemStackStateUuid(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        String uuidStr = meta.getPersistentDataContainer().get(PersistenceDataKey.STATE_UUID.key(RuneQuestPlugin.getInstance()), PersistentDataType.STRING);

        if (uuidStr == null) {
            return null;
        }

        return UUID.fromString(uuidStr);
    }

    public void preloadItemStateAsync(UUID uuid) {
        if (uuid == null) {
            return;
        }

        AbstractState cached = itemStates.get(uuid);
        if (cached != null) {
            return;
        }

        AbstractState stateFromDb = this.initializeState(uuid);
        if (stateFromDb != null) {
            itemStates.put(uuid, stateFromDb);
            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> Bukkit.getServer().getPluginManager().callEvent(new StateLoadedEvent(uuid, stateFromDb)));
        }
    }

    public void removeItemStateFromCache(UUID uuid) {
        this.itemStates.remove(uuid);
    }

    public void insertDefaultItemState(UUID uuid, AbstractState state) {
        this.itemStates.put(uuid, state);
    }

    @Nullable
    public AbstractState getItemState(ItemStack item) {
        UUID uuid = getItemStackStateUuid(item);
        AbstractItemType abstractItemType = itemHandler.getRegistry().getType(item);

        if (uuid == null) {
            return null;
        }

        if (!(abstractItemType instanceof Stateful stateful)) {
            return null;
        }

        AbstractState result = itemStates.get(uuid);

        if (result == null) {

            // Try to load state
            AbstractState stateFromDb = this.initializeState(uuid);
            if (stateFromDb != null) {
                Bukkit.getServer().getPluginManager().callEvent(new StateLoadedEvent(uuid, stateFromDb));
                return stateFromDb;
            }

            // Create new if it doesn't exist.
            AbstractState newState = stateful.cloneDefaultStateTemplate();
            Bukkit.getServer().getPluginManager().callEvent(new StateCreatedDefaultEvent(uuid, newState));

            if (newState == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Tried to create new state for " + item.displayName() + ", but received null.");
            } else {
                itemStates.put(uuid, newState);
            }
            return newState;
        }

        return result;
    }

    @Nullable
    public String serializeState(UUID uuid) {
        AbstractState matchingState = itemStates.get(uuid);

        if (matchingState == null) {
            return null;
        }

        return gson.toJson(matchingState.serialize(gson));
    }

    @Nullable
    public AbstractState initializeState(UUID uuid) {
        AbstractStateMeta result = this.repository.getByUUID(uuid);

        if (result == null) {
            return null;
        }

        AbstractState newState = this.factory.createState(result);

        if (newState == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Cannot initialize state " + uuid + ". Invalid state type! Received: " + result.type().toString());
            return null;
        }

        itemStates.put(uuid, newState);
        return newState;
    }

    public void destroyStateIfExists(@Nullable UUID uuid) {
        if (uuid == null) return;
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            this.repository.deleteState(uuid);
            removeItemStateFromCache(uuid);
        });
    }

    public void destroyStateIfExists(ItemStack item) {
        UUID uuid = getItemStackStateUuid(item);
        destroyStateIfExists(uuid);
    }

    public void saveUpdatedState(AbstractState state, boolean clearCache, boolean runAsync) {
        UUID matchingKey = this.itemStates.entrySet().stream()
                .filter(entry -> entry.getValue().equals(state))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (matchingKey == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Cannot save state " + state.getStateType() + "! State is not in the registry.");
            return;
        }

        if (runAsync) {
            Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
                try {
                    this.repository.upsert(matchingKey, state.getStateType(), serializeState(matchingKey));

                    if (clearCache) {
                        RuneQuestPlugin.getItemStateManager().removeItemStateFromCache(matchingKey);
                    }
                } catch (Exception e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to update item state: ", e);
                }
            });

            return;
        }

        try {
            this.repository.upsert(matchingKey, state.getStateType(), serializeState(matchingKey));

            if (clearCache) {
                RuneQuestPlugin.getItemStateManager().removeItemStateFromCache(matchingKey);
            }
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to update item state: ", e);
        }


    }
}
