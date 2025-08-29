package org.zuma.examplePlugin.domain.block.base;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.world.ChunkLoadEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.entity.EntitySessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public abstract class ModelledInteractableBlock extends AbstractInteractableBlock {
    private Entity armorStand = null;
    private final String model;
    private final int height, width;
    private final double scale;
    private final List<Location> interactableBlockLocations;

    public ModelledInteractableBlock(AbstractInteractableBlockMeta meta, int height, int width, double scale, String model) {
        super(meta, null, null);
        this.model = model;
        this.height = height;
        this.width = width;
        this.scale = scale;
        this.interactableBlockLocations = new ArrayList<>();

        this.getHologramLocation().add(0, (double) height / 2, 0);
    }

    private void calculateHitboxSize() {
        Location base = super.getLocation();
        BlockFace face = getFace();

        int halfWidth = this.width / 2;

        for (int w = -halfWidth; w <= halfWidth; w++) {
            for (int h = 0; h < this.height; h++) {
                Location nextLocation = base.clone();

                switch (face) {
                    case NORTH:
                        nextLocation.add(w, h, 0);
                        break;
                    case SOUTH:
                        nextLocation.add(-w, h, 0);
                        break;
                    case EAST:
                        nextLocation.add(0, h, w);
                        break;
                    case WEST:
                        nextLocation.add(0, h, -w);
                        break;
                }

                interactableBlockLocations.add(nextLocation);
            }
        }
    }

    public static float[] getRotationForFace(BlockFace face) {
        float yaw = 0f;
        float pitch = 0f;

        switch (face) {
            case NORTH -> yaw = 180f;
            case SOUTH -> yaw = 0f;
            case WEST -> yaw = 90f;
            case EAST -> yaw = -90f;
            case UP -> pitch = -90f;
            case DOWN -> pitch = 90f;
            default -> yaw = 0f;
        }

        return new float[]{yaw, pitch};
    }


    @Override
    public void spawn(Material material) {
        Location loc = super.getLocation().clone().add(0.5, 0, 0.5);
        Chunk chunk = loc.getChunk();

        chunk.load(true);
        chunk.setForceLoaded(true);

        calculateHitboxSize();
        interactableBlockLocations.forEach(location -> location.getBlock().setType(Material.BARRIER));

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            Location armorStandLoc = loc.clone();

            if (super.getFace() != null) {
                float[] rot = getRotationForFace(super.getFace());
                armorStandLoc.setYaw(rot[0]);
                armorStandLoc.setPitch(rot[1]);
            }

            try {
                Entity e = loc.getWorld().spawnEntity(armorStandLoc, EntityType.ARMOR_STAND);
                ArmorStand as = (ArmorStand) e;
                this.armorStand = as;

                ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(e);
                ActiveModel activeModel = ModelEngineAPI.createActiveModel(model);
                activeModel.setScale(this.scale);
                modeledEntity.addModel(activeModel, true);

                as.setInvisible(true);
                as.setMarker(true);
                as.setGravity(false);
                as.setInvulnerable(true);
            } catch (Exception ex) {
                this.onDisable();
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed setting entity model at interactable block ID " + this.getId() + "!");
                throw ex;
            } finally {
                chunk.setForceLoaded(false);
            }
        }, 2L);
    }


    @Override
    public void onDisable() {
        interactableBlockLocations.forEach(location -> location.getBlock().setType(Material.AIR));

        if (armorStand != null) {
            armorStand.remove();
        }
        super.onDisable();
    }

    @Override
    public List<Location> getInteractableBlocks() {
        return this.interactableBlockLocations;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
