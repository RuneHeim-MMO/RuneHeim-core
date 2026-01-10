package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.impl.EmptyContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmptyContainerLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public EmptyContainerLore(EmptyContainer container) {
        String formatted = container.getSupportedStoredItems().stream()
                .map(a -> a.getLabel().toLowerCase())
                .collect(Collectors.joining(", "));

        lore.add(Component.empty());
        lore.add(Component.text("Can hold: ", Color.ITEM_LORE_DESCRIPTION.asColor()).append(Component.text(formatted + ".", Color.ACCENT.asColor())));
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
