package org.zuma.examplePlugin.domain.item.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.modifier.Modifier;

import java.util.ArrayList;
import java.util.List;

public abstract class EquipableWithSockets extends EquipableItem implements Stateful {
    private GearGemSocketsState socketsState;
    private List<Modifier> gemModifiers;

    public EquipableWithSockets(AbstractItemMeta meta) {
        super(meta);
        this.gemsLore = new ArrayList<>();
    }

    public void setGemState(GearGemSocketsState state) {
        this.socketsState = state;

        updateGemsLore();
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

    public void updateGemsLore() {
        List<Component> gemsLore = new ArrayList<>();
        gemsLore.add(Component.empty());
        gemsLore.add(Icon.GREEN_GEM.asComponent().append(Component.text("Gem slots (0/" + socketsState.getGemSlots() + ")", Color.SUCCESS.asColor())));
        gemsLore.add(Component.text(TextUtils.repeatString("-", 16), Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
        // Todo: append gems here

        for (int i = 0; i < socketsState.getGemSlots() - socketsState.getInsertedGemIds().size(); i++) {
            gemsLore.add(Component.text(" * Empty gem slot *", Color.ITEM_TYPE.asColor()));
        }
        gemsLore.add(Component.text(TextUtils.repeatString("-", 16), Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
        gemsLore.add(Component.empty());
        super.setGemsLore(gemsLore);

    }

    public List<Modifier> getGemModifiers() {
        return gemModifiers;
    }
}
