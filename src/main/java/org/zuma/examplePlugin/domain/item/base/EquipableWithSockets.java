package org.zuma.examplePlugin.domain.item.base;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;

import javax.annotation.Nullable;
import java.util.ArrayList;

public abstract class EquipableWithSockets extends EquipableItem implements Stateful {
    private GearGemSocketsState socketsState;

    public EquipableWithSockets(AbstractItemMeta meta) {
        super(meta);
    }

    public void setGemState(GearGemSocketsState state) {
        this.socketsState = state;

        state.getInsertedGemIds().forEach(id -> {
            AbstractItem abstractGem = RuneQuestPlugin.getItemRegistry().getItem(id);

            if (abstractGem == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could find gem to apply modifiers to an item! Invalid ID: " + id);
            }
        });
    }

    @Override
    public AbstractState getInitialState() {
        return new GearGemSocketsState(new ArrayList<>(socketsState.getInsertedGemIds()), socketsState.getGemSlots());
    }

    @Nullable
    public GearGemSocketsState getState(ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state != null && state.getStateType() == StateType.GEAR_GEM_SOCKETS) {
            return (GearGemSocketsState) state;
        }

        return null;
    }
}
