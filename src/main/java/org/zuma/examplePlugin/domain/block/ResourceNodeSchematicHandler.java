package org.zuma.examplePlugin.domain.block;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.zuma.examplePlugin.domain.block.base.ResourceNode;
import org.zuma.examplePlugin.domain.schematic.InteractiveElType;
import org.zuma.examplePlugin.domain.schematic.InteractiveElement;
import org.zuma.examplePlugin.domain.schematic.SchematicSpawner;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class ResourceNodeSchematicHandler {
    private String schematicName;
    @Nullable
    private String depletedSchematic;

    public ResourceNodeSchematicHandler(String schematicName, @Nullable String depletedSchematic) {
        this.schematicName = schematicName;
        this.depletedSchematic = depletedSchematic;
    }

    public void updateNodeModel(ResourceNode node) {
        if (node.isDepleted() && this.depletedSchematic != null) {
            List<InteractiveElement> result = new SchematicSpawner().pasteSchematic(node.getOrigin(), new File("plugins/RuneQuest/schematics/" + this.depletedSchematic + ".schem"));
            handleElementReassignation(result, node);
            return;
        }

        if (this.schematicName != null) {
            List<InteractiveElement> result = new SchematicSpawner().pasteSchematic(node.getOrigin(), new File("plugins/RuneQuest/schematics/" + this.schematicName + ".schem"));
            handleElementReassignation(result, node);
            return;
        }
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

}
