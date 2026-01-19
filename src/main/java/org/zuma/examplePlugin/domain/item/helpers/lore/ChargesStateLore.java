package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.state.states.ChargesState;

import java.util.ArrayList;
import java.util.List;

public class ChargesStateLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public ChargesStateLore(ChargesState state) {
        lore.add(Component.empty());
        lore.add(Icon.CHARGE.asComponent().append(Component.text(state.getCharges() + " charges", Color.CHARGES.asColor())));
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
