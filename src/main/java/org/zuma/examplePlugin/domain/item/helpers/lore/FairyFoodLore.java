package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFood;

import java.util.ArrayList;
import java.util.List;

public class FairyFoodLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public FairyFoodLore(FairyFood fairyFood) {
        lore.add(Component.empty());
        lore.add(Component.text("Restores ", Color.SUCCESS.asColor())
                .append(Icon.STAMINA.asComponent())
                .append(Component.text(fairyFood.getEnergyRestoreAmount() + " Stamina", Color.SUCCESS.asColor())));
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
