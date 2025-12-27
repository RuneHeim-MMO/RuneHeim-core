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
    public Clipboard pasteSchematic(Location location, File schematicFile, boolean ignoreAirBlocks, boolean resetOrigin) {
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

            if (resetOrigin) {
                clipboard.setOrigin(clipboard.getRegion().getMinimumPoint());
                origin = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            } else {
                origin = BlockVector3.at(location.getBlockX(),
                        location.getBlockY() - 2, // Player offset
                        location.getBlockZ());
            }

            World weWorld = BukkitAdapter.adapt(location.getWorld());

            try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
                editSession.setFastMode(true);
                editSession.setBatchingChunks(true);

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

        if (lastPaste == null) return elements;

        World weWorld = lastPaste.getWorld();
        org.bukkit.World bukkitWorld = BukkitAdapter.asBukkitWorld(weWorld).getWorld();

        lastPaste.forEach(pos -> {
            Block block = bukkitWorld.getBlockAt(pos.x(), pos.y() + 1, pos.z());
            Material type = block.getType();

            if (!type.name().endsWith("_SIGN")) return; // cheap type check

            BlockData data = block.getBlockData();
            BlockFace facing = null;

            if (data instanceof Directional dir) facing = dir.getFacing();
            else if (data instanceof Rotatable rot) facing = rot.getRotation();

            if (facing == null) return;

            Sign sign = (Sign) block.getState();
            String firstLine = TextUtils.extractPlainText(sign.getSide(Side.FRONT).line(0));

            try {
                InteractiveElType typeEnum = InteractiveElType.valueOf(firstLine);
                elements.add(new InteractiveElement(block.getLocation(), facing, typeEnum));
                block.setType(Material.AIR); // optional: batch this later
            } catch (IllegalArgumentException ignored) {
            }
        });

        return elements;
    }


    public void clearLastPaste() {
        if (lastPaste == null) {
            return;
        }

        World weWorld = lastPaste.getWorld();

        try (EditSession editSession = WorldEdit.getInstance()
                .newEditSessionBuilder()
                .world(weWorld)
                .maxBlocks(-1)
                .build()) {

            editSession.setFastMode(true);
            editSession.setBatchingChunks(true);
            editSession.setBlocks(lastPaste, BukkitAdapter.adapt(Material.AIR.createBlockData()));
            
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Unable to clear last paste schematic", e);
        }
    }
}
