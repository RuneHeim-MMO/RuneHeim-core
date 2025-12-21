package org.zuma.examplePlugin.domain.housing.template;

import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.domain.housing.HousingRegion;

import java.io.File;
import java.nio.file.Path;

public enum HouseTemplate {
    DRAKKAR_STARTER("drakkar_starter", "Drakkar Starter House", HousingRegion.DRAKKAR, 15000, null);

    private final String schematic, name, requiredQuestId;
    private final HousingRegion region;
    private final int price;

    HouseTemplate(String schematic, String name, HousingRegion region, int price, @Nullable String requiredQuestId) {
        this.schematic = schematic;
        this.name = name;
        this.region = region;
        this.price = price;
        this.requiredQuestId = requiredQuestId;
    }

    public File getSchematic() {
        Path schematicPath = ConfigManager.HOUSE_SCHEMATICS_PATH.resolve(this.schematic + ".schem");
        return schematicPath.toFile();
    }

    public String getTemplateName() {
        return name;
    }

    public HousingRegion getRegion() {
        return region;
    }

    public int getPrice() {
        return price;
    }

    public String getRequiredQuestId() {
        return requiredQuestId;
    }
}
