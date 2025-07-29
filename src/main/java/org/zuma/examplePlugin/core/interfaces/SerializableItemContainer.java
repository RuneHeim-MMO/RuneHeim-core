package org.zuma.examplePlugin.core.interfaces;

import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;

import java.util.List;

public interface SerializableItemContainer {
    List<ItemStackSlotPair> getContent();

}
