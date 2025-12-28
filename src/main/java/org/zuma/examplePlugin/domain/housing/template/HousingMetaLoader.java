package org.zuma.examplePlugin.domain.housing.template;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.config.EnvironmentType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.housing.HousingManager;
import org.zuma.examplePlugin.domain.housing.instance.HouseInstance;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.schematic.InteractiveElType;
import org.zuma.examplePlugin.domain.schematic.InteractiveElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HousingMetaLoader {
    private final Map<HouseTemplate, TemplateMeta> templateMetaMap;
    private final HousingManager manager;

    public HousingMetaLoader(HousingManager manager) {
        this.manager = manager;
        templateMetaMap = new HashMap<>();
    }

    private static @NotNull TemplateMeta getTemplateMeta(int pasteX, int pasteZ, List<InteractiveElement> elements) {
        int index = 0;
        int interactiveSlots = 0;
        int decorationSlots = 0;
        HashMap<Integer, HouseSlot> slotsMap = new HashMap<>();

        for (InteractiveElement el : elements) {
            Location absoluteLocation = el.location();
            absoluteLocation.subtract(pasteX, 0, pasteZ);

            if (el.type() == InteractiveElType.SLOT) {
                HouseSlot slot = new HouseSlot(absoluteLocation, HouseSlot.HouseSlotType.INTERACTIVE);
                slotsMap.put(index, slot);
                interactiveSlots++;
                index++;
            } else if (el.type() == InteractiveElType.DECORATION_SLOT) {
                HouseSlot slot = new HouseSlot(absoluteLocation, HouseSlot.HouseSlotType.DECORATION);
                slotsMap.put(index, slot);
                decorationSlots++;
                index++;
            }
        }

        return new TemplateMeta(interactiveSlots, decorationSlots, slotsMap);
    }

    // Load all house template, in order to validate them and extract metadata (interactive elements)
    public void loadTemplatesMeta() {
        if (ConfigManager.getIsPreviewServer() && !ConfigManager.environment.equals(EnvironmentType.DEVELOPMENT)) {
            return;
        }

        for (HouseTemplate template : HouseTemplate.values()) {
            try {
                HouseInstance testInstance = manager.getInstanceCreator().createInstance(UUID.randomUUID(), UUID.randomUUID(), "Template", template, true, new HashMap<>());

                List<InteractiveElement> elements = testInstance.getInteractiveElements();
                List<InteractiveElType> requiredElements = List.of(InteractiveElType.EXIT, InteractiveElType.SPAWN);

                for (InteractiveElType requiredType : requiredElements) {
                    boolean hasType = elements.stream().anyMatch(el -> el.type() == requiredType);

                    if (!hasType) {
                        throw new IllegalStateException("Template " + template.name() + " is missing required interactive element of type " + requiredType.name());
                    }
                }

                elements.stream()
                        .filter(el -> el.type() == InteractiveElType.SPAWN)
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Template " + template.name() + " is missing spawn point interactive element"));

                TemplateMeta meta = getTemplateMeta(testInstance.getGridX() * HousingManager.GRID_SIZE, testInstance.getGridZ() * HousingManager.GRID_SIZE, elements);

                this.templateMetaMap.put(template, meta);
                manager.getTemplatePreviewMap().put(template, testInstance);
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Loaded template " + template.name() + " successfully. Detected: " + (meta.interactiveSlotCount() + meta.decorationSlotCount()) + " slots.");

            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[PlayerHousing] Failed to load template " + template.name() + " metadata. Invalid template. ", e);
            }
        }

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Loaded " + this.templateMetaMap.size() + " house templates.");
    }

    public Map<HouseTemplate, TemplateMeta> getTemplateMetaMap() {
        return this.templateMetaMap;
    }

}
