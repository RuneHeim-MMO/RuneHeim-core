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
import org.zuma.examplePlugin.domain.item.constants.ItemDataKey;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class StateManager {
    private final HashMap<UUID, AbstractState> itemStates = new HashMap<>(); // TODO: PERSISTENCE! delete states on item despawn & handle lifecycle properly
    private final StateFactory factory;
    private final ItemHandler itemHandler;

    public StateManager(StateFactory factory, ItemHandler itemHandler) {
        this.factory = factory;
        this.itemHandler = itemHandler;
    }

    @Nullable
    private UUID getItemStackStateUuid(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        NamespacedKey stateType = new NamespacedKey(RuneQuestPlugin.getInstance(), ItemDataKey.STATE_UUID.asString());
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
            AbstractState newState = stateful.getInitialState();
            itemStates.put(uuid, newState);
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

    public void initializeState(UUID uuid) {
        // TODO: Repo fetching stuff here
        String result = "";
        StateType resultType = StateType.CONSUMABLE;
        // Temp ^

        AbstractState newState = this.factory.createState(resultType, result);

        if (newState == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Cannot initialize state " + uuid.toString() + ". Invalid state type! Received: " + resultType.toString());
            return;
        }

        itemStates.put(uuid, newState);
    }
}
