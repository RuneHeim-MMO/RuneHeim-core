package org.zuma.examplePlugin.domain.housing.template;

import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.domain.housing.HousingRegion;

import java.io.File;
import java.nio.file.Path;

public enum HouseTemplate {
    DRAKKAR_STARTER("drakkar_starter", "Drakkar Starter House", HousingRegion.DRAKKAR);

    private final String schematic, name;
    private final HousingRegion region;

    HouseTemplate(String schematic, String name, HousingRegion region) {
        this.schematic = schematic;
        this.name = name;
        this.region = region;
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
}
