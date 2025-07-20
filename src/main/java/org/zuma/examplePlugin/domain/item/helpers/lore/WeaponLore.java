package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.Weapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public WeaponLore(Weapon item) {
        lore.add(Component.empty());

        switch (item.getType()) {
            case MELEE -> lore.add(Icon.SWING_SPEED.asComponent()
                    .append(Component.text("Speed: ", Color.ITEM_LORE_DESCRIPTION.asColor()))
                    .append(Component.text(item.getSpeed().getLabel() + " (" + String.format("%.1f", item.getSpeed().getHitsPerSec()) + " HPS)")));
            case RANGED -> lore.add(Icon.SWING_SPEED.asComponent()
                    .append(Component.text("Speed: ", Color.ITEM_LORE_DESCRIPTION.asColor()))
                    .append(Component.text(item.getSpeed().getLabel() + " (" + String.format("%.1f", item.getSpeed().getRangedMinPower()) + " Min. draw)")));
        }
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
