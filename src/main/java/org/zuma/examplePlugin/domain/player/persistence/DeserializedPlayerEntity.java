package org.zuma.examplePlugin.domain.player.persistence;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;

import java.util.List;

public record DeserializedPlayerEntity(List<ItemStackSlotPair> inventory, Location location, boolean isFirstJoin) {
}
