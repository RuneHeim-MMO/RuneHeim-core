package org.zuma.examplePlugin.domain.block;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.base.ResourceNode;
import org.zuma.examplePlugin.domain.schematic.InteractiveElType;
import org.zuma.examplePlugin.domain.schematic.InteractiveElement;
import org.zuma.examplePlugin.domain.schematic.SchematicSpawner;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class ResourceNodeSchematicHandler {
    private final String schematicName;
    @Nullable
    private final String depletedSchematic;
    private final SchematicSpawner spawner;

    public ResourceNodeSchematicHandler(String schematicName, @Nullable String depletedSchematic) {
        this.schematicName = schematicName;
        this.depletedSchematic = depletedSchematic;
        this.spawner = new SchematicSpawner();
    }

    public void updateNodeModel(ResourceNode node) {
        if (node.getNodeType() == null || this.depletedSchematic == null && node.isDepleted()) {
            return;
        }

        Location location = node.getOrigin().clone(); // Player offset (during schematic creation he should be under the center point)
        String schematicName = node.isDepleted() && this.depletedSchematic != null ? this.depletedSchematic : this.schematicName;

        if (schematicName == null) {
            return;
        }

        spawner.clearLastPaste();
        Clipboard spawnClipboard = spawner.pasteSchematic(location, new File("plugins/RuneQuest/schematics/" + schematicName + ".schem"), true);


        if (spawnClipboard == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed spawning schematic for " + node.getNodeType().getLabel() + "! File: " + "plugins/RuneQuest/schematics/" + this.depletedSchematic + ".schem");
            return;
        }

        List<InteractiveElement> result = spawner.extractInteractiveElements();
        handleElementReassignation(result, node);
    }

    // Update interactive elements (e.g action block, holograms) positions according to schematic
    private void handleElementReassignation(List<InteractiveElement> elements, ResourceNode node) {
        elements.forEach(el -> {

            if (Objects.requireNonNull(el.type()) == InteractiveElType.ACTION) {
                Location newLocation = el.location().toBlockLocation();
                Location newHologramLocation = el.location().toBlockLocation();

                switch (el.face()) {
                    case BlockFace.NORTH -> {
                        newLocation.add(0, 0, 1);
                        newHologramLocation.add(0.5, 1.0, 0);
                    }
                    case BlockFace.SOUTH -> {
                        newLocation.subtract(0, 0, 1);
                        newHologramLocation.add(-0.5, 1.0, 0);
                    }
                    case BlockFace.WEST -> {
                        newLocation.subtract(-1, 0, 0);
                        newHologramLocation.add(-1, 1.0, 0.5);
                    }
                    case BlockFace.EAST -> {
                        newLocation.subtract(1, 0, 0);
                        newHologramLocation.add(1, 1.0, 0.5);
                    }
                }

                node.getManager().replaceBlockLocation(node.getLocation().toBlockLocation(), newLocation);
                node.setLocation(newLocation);
                node.setHologramLocation(newHologramLocation);
            }

        });
    }

    public void cleanUp() {
        this.spawner.clearLastPaste();
    }

}
