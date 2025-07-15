package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.WeaponType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;

import java.util.ArrayList;
import java.util.List;

public class WeaponLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public WeaponLore(WeaponType item) {
        lore.add(Component.empty());
        lore.add(Icon.SWING_SPEED.asComponent()
                .append(Component.text("Speed: ", Color.ITEM_LORE_DESCRIPTION.asColor()))
                .append(Component.text(item.getSpeed().getLabel())));
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
