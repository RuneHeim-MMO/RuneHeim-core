package org.zuma.examplePlugin.domain.item.base;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;

import javax.annotation.Nullable;

public abstract class SocketedEquipableItemType extends EquipableItemType implements Stateful {
    private GearGemSocketsState defaultStateTemplate;

    public SocketedEquipableItemType(AbstractItemMeta meta) {
        super(meta);
        this.setStateType(StateType.GEAR_GEM_SOCKETS);
    }

    public void setDefaultStateTemplate(GearGemSocketsState template) {
        this.defaultStateTemplate = template;
    }

    public AbstractState cloneDefaultStateTemplate() {
        return defaultStateTemplate.clone();
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
