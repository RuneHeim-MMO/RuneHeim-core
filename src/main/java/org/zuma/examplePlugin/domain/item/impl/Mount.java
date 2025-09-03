package org.zuma.examplePlugin.domain.item.impl;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.item.state.states.MountState;

import javax.annotation.Nullable;


public class Mount extends EquipableItemType implements Stateful {
    private final String model;
    private MountState defaultStateTemplate;

    public Mount(AbstractItemMeta meta, String model) {
        super(meta);
        super.setTypeLore("Mount", Icon.FAIRY.get());
        this.setStateType(StateType.MOUNT_STATE);
        this.model = model;
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.MOUNT;
    }

    public void setDefaultStateTemplate(MountState state) {
        this.defaultStateTemplate = state;
    }

    public AbstractState cloneDefaultStateTemplate() {
        return defaultStateTemplate.clone();
    }

    @Nullable
    public MountState getState(ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state != null && state.getStateType() == StateType.MOUNT_STATE) {
            return (MountState) state;
        }

        return null;
    }

    public String getModel() {
        return model;
    }
}
