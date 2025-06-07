package org.zuma.examplePlugin.domain.item.state;

import com.google.gson.Gson;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemHandler;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.constants.PersistanceDataKey;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StateManager {
    private final HashMap<UUID, AbstractState> itemStates = new HashMap<>(); // TODO: PERSISTENCE! delete states on item despawn & handle lifecycle properly
    private final StateFactory factory;
    private final ItemHandler itemHandler;
    private final StateRepository repository;

    public StateManager(StateFactory factory, ItemHandler itemHandler, StateRepository repository) {
        this.factory = factory;
        this.itemHandler = itemHandler;
        this.repository = repository;
    }

    @Nullable
    private UUID getItemStackStateUuid(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        NamespacedKey stateType = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistanceDataKey.STATE_UUID.asString());
        String uuidStr = meta.getPersistentDataContainer().get(stateType, PersistentDataType.STRING);

        if (uuidStr == null) {
            return null;
        }

        return UUID.fromString(uuidStr);
    }

    @Nullable
    public AbstractState getItemState(ItemStack item) {
        UUID uuid = getItemStackStateUuid(item);
        AbstractItem abstractItem = itemHandler.getRegistry().getItem(item);

        if (uuid == null) {
            return null;
        }

        if (!(abstractItem instanceof Stateful stateful)) {
            return null;
        }


        AbstractState result = itemStates.get(uuid);

        if (result == null) {

            // Try to load state
            AbstractState stateFromDb = this.initializeState(uuid);
            if (stateFromDb != null) {
                return stateFromDb;
            }


            // Create new if it doesn't exist.
            AbstractState newState = stateful.getInitialState();
            itemStates.put(uuid, newState);
            repository.create(uuid, newState.getStateType(), serializeState(uuid));
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

        return new Gson().toJson(matchingState);
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

    public void saveUpdatedState(AbstractState state) {
        UUID matchingKey = this.itemStates.entrySet().stream()
                .filter(entry -> entry.getValue().equals(state))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (matchingKey == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Cannot save state " + state.getStateType() + "! State is not in the registry.");
            return;
        }

        this.repository.update(matchingKey, serializeState(matchingKey));

    }
}
