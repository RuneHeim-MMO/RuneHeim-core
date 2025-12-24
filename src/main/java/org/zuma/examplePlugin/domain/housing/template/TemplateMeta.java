package org.zuma.examplePlugin.domain.housing.template;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;

import java.util.HashMap;

public record TemplateMeta(int interactiveSlotCount, int decorationSlotCount,
                           HashMap<Integer, HouseSlot> slots) {
}
