package org.zuma.examplePlugin.domain.housing.records;

import org.zuma.examplePlugin.domain.housing.HousingRegion;
import org.zuma.examplePlugin.domain.housing.template.HouseTemplate;

import java.util.HashMap;
import java.util.UUID;

public record HouseEntry(UUID uuid, UUID ownerUUID, HousingRegion region, HouseTemplate template,
                         HashMap<Integer, HouseFurnitureEntry> furniture) {
}
