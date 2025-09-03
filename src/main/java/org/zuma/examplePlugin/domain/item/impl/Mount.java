package org.zuma.examplePlugin.domain.item.impl;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.entity.Horse;
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
    private MountState defaultStateTemplate;

    @Nullable
    private final String model;
    @Nullable
    private final DisguiseType disguise;
    @Nullable
    private final Horse.Color color;
    @Nullable
    private final Horse.Style style;

    public Mount(AbstractItemMeta meta, @Nullable String model, @Nullable DisguiseType disguise, @Nullable Horse.Color color, @Nullable Horse.Style style) {
        super(meta);
        this.disguise = disguise;
        this.color = color;
        this.style = style;
        this.model = model;

        super.setTypeLore("Mount", Icon.FAIRY.get());
        this.setStateType(StateType.MOUNT_STATE);
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

    @Nullable
    public String getModel() {
        return model;
    }

    @Nullable
    public DisguiseType getDisguise() {
        return disguise;
    }

    @Nullable
    public Horse.Color getColor() {
        return color;
    }

    @Nullable
    public Horse.Style getStyle() {
        return style;
    }
}
