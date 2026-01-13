package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.BulkItem;

import java.util.ArrayList;
import java.util.List;

public class BulkItemLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public BulkItemLore(BulkItem item) {
        lore.add(Component.empty());
        lore.add(Component.text("Contains:", Color.ITEM_LORE_DESCRIPTION.asColor()));

        item.getItems().forEach(record -> {
            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(record.getItemId());

            if (type == null) {
                return;
            }

            lore.add(Component.text("- " + record.getAmount() + "x ", TextColor.fromHexString(type.rarity.getHexColor()))
                    .append(Component.text(type.displayName, TextColor.fromHexString(type.rarity.getHexColor()))));
        });
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
