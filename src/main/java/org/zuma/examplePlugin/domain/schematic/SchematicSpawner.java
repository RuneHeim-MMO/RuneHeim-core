package org.zuma.examplePlugin.domain.schematic;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.zuma.examplePlugin.core.utils.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class SchematicSpawner {

    private BlockVector3 getCenterBottomPosition(Clipboard clipboard, Location targetLocation) {
        var region = clipboard.getRegion();
        BlockVector3 min = region.getMinimumPoint();
        BlockVector3 max = region.getMaximumPoint();

        int centerX = (min.getX() + max.getX()) / 2;
        int centerZ = (min.getZ() + max.getZ()) / 2;

        int targetX = targetLocation.getBlockX();
        int targetY = targetLocation.getBlockY();
        int targetZ = targetLocation.getBlockZ();

        int offsetX = centerX - min.getX();
        int offsetZ = centerZ - min.getZ();

        int pasteX = targetX - offsetX;
        int pasteZ = targetZ - offsetZ;

        int pasteY = targetY;

        System.out.println("Pasting schematic at: " + pasteX + ", " + pasteY + ", " + pasteZ);

        return BlockVector3.at(pasteX, pasteY, pasteZ);
    }


    public List<InteractiveElement> pasteSchematic(Location location, File schematicFile) {
        List<InteractiveElement> elements = new ArrayList<>();

        try {
            ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
            if (format == null) {
                throw new IllegalArgumentException("Unsupported schematic format.");
            }

            Clipboard clipboard;
            try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
                clipboard = reader.read();
            }

            World weWorld = BukkitAdapter.adapt(location.getWorld());
            BlockVector3 pastePosition = getCenterBottomPosition(clipboard, location);
            BlockVector3 origin = BlockVector3.at(
                    location.getBlockX(),
                    location.getBlockY() - 2, // Player offset
                    location.getBlockZ());


            try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(origin)
                        .ignoreAirBlocks(false)
                        .build();

                Operations.complete(operation);
            }

            // Loop through the clipboard region to find sign blocks
            clipboard.getRegion().forEach(blockPos -> {
                BlockVector3 worldBlockPos = blockPos.add(pastePosition.subtract(clipboard.getRegion().getMinimumPoint()));
                Block block = location.getWorld().getBlockAt(worldBlockPos.getBlockX(), worldBlockPos.getBlockY(), worldBlockPos.getBlockZ());

                if (block.getState() instanceof Sign sign && block.getBlockData() instanceof Directional directional) {
                    String firstLine = TextUtils.extractPlainText(sign.getSide(Side.FRONT).line(0));
                    try {
                        InteractiveElType type = InteractiveElType.valueOf(firstLine);
                        elements.add(new InteractiveElement(block.getLocation(), directional.getFacing(), type));
                        block.setType(Material.AIR);
                    } catch (IllegalArgumentException e) {
                        // No enum constant found for firstLine
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return elements;
    }
}
