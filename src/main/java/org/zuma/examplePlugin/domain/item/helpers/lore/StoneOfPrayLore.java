package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.StoneOfPrayState;

import java.util.ArrayList;
import java.util.List;

public class StoneOfPrayLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public StoneOfPrayLore(ItemStack item) {
        lore.add(Component.empty());
        lore.add(Component.text("Preset:", Color.ITEM_LORE_DESCRIPTION.asColor()));

        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (!(state instanceof StoneOfPrayState stoneOfPrayState)) {
            return;
        }

        stoneOfPrayState.getPreset().forEach(record -> {
            lore.add(Component.text("- " + record.getName(), Color.ACCENT.asColor()));
        });
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
