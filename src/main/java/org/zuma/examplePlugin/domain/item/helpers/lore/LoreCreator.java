package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;

import java.util.List;

public interface LoreCreator {
    List<Component> getLore();
}
