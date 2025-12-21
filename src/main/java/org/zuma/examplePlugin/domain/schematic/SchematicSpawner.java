package org.zuma.examplePlugin.domain.schematic;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.sign.Side;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.TextUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class SchematicSpawner {
    @Nullable
    private CuboidRegion lastPaste = null;

    @Nullable
    public Clipboard pasteSchematic(Location location, File schematicFile, boolean ignoreAirBlocks, boolean ignoreOriginalOrigin) {
        try {
            ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
            if (format == null) {
                throw new IllegalArgumentException("Unsupported schematic format.");
            }

            Clipboard clipboard;
            try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
                clipboard = reader.read();
            }

            BlockVector3 origin;

            if (ignoreOriginalOrigin) {
                clipboard.setOrigin(clipboard.getRegion().getMinimumPoint());
                origin = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            } else {
                origin = BlockVector3.at(location.getBlockX(),
                        location.getBlockY() - 2, // Player offset
                        location.getBlockZ());
            }

            World weWorld = BukkitAdapter.adapt(location.getWorld());


            try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(origin)
                        .ignoreAirBlocks(ignoreAirBlocks)
                        .build();

                Operations.complete(operation);
            }

            BlockVector3 offset = origin.subtract(clipboard.getOrigin());
            BlockVector3 worldMin = clipboard.getRegion().getMinimumPoint().add(offset);
            BlockVector3 worldMax = clipboard.getRegion().getMaximumPoint().add(offset);
            lastPaste = new CuboidRegion(weWorld, worldMin, worldMax);

            return clipboard;


        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Unable to paste schematic " + schematicFile.getAbsolutePath() + "! ", e);
        }

        return null;
    }

    public List<InteractiveElement> extractInteractiveElements() {
        List<InteractiveElement> elements = new ArrayList<>();

        if (lastPaste == null) {
            return elements;
        }

        World weWorld = lastPaste.getWorld();

        lastPaste.forEach(pos -> {
            Location loc = new Location(BukkitAdapter.asBukkitWorld(weWorld).getWorld(), pos.x(), pos.y() + 1, pos.z());
            Block block = loc.getBlock();
            BlockData data = block.getBlockData();
            BlockFace facing = null;

            if (data instanceof Directional dir) {
                facing = dir.getFacing();
            } else if (data instanceof Rotatable rot) {
                facing = rot.getRotation();
            }

            if (block.getState() instanceof Sign sign && facing != null) {
                String firstLine = TextUtils.extractPlainText(sign.getSide(Side.FRONT).line(0));
                try {
                    InteractiveElType type = InteractiveElType.valueOf(firstLine);
                    elements.add(new InteractiveElement(block.getLocation(), facing, type));
                    block.setType(Material.AIR); // Remove the sign block
                } catch (IllegalArgumentException e) {
                    // Not a valid enum constant, ignore
                }
            }
        });

        return elements;
    }

    public void clearLastPaste() {
        if (lastPaste == null) return;

        World weWorld = lastPaste.getWorld();
        BlockVector3 min = lastPaste.getMinimumPoint();

        try (EditSession editSession = WorldEdit.getInstance()
                .newEditSessionBuilder()
                .world(weWorld)
                .maxBlocks(-1)
                .build()) {

            // Create an empty clipboard (all air) with same size as lastPaste
            BlockArrayClipboard emptyClipboard = new BlockArrayClipboard(lastPaste);

            // Paste empty clipboard exactly at the lastPaste minimum point to clear region
            Operation clearOperation = new ClipboardHolder(emptyClipboard)
                    .createPaste(editSession)
                    .to(min)
                    .ignoreAirBlocks(false)
                    .build();

            Operations.complete(clearOperation);

        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Unable to clear last paste schemtic", e);
        }
    }
}
