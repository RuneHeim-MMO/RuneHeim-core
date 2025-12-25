package org.zuma.examplePlugin.domain.item.impl;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.entity.EntitySessionManager;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.MountState;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.UUID;


public class Mount extends EquipableItemType implements Stateful {
    private static final double HORSE_HEIGHT = 2.0;
    @Nullable
    private final String model;
    @Nullable
    private final EntityType baseEntity;
    @Nullable
    private final Horse.Color color;
    @Nullable
    private final Horse.Style style;
    private MountState defaultStateTemplate;
    private int maxLevel;
    private double baseSpeed, baseJump, speedLevelBonus, jumpLevelBonus;

    public Mount(AbstractItemMeta meta, @Nullable String model, @Nullable EntityType baseEntity, @Nullable Horse.Color color, @Nullable Horse.Style style) {
        super(meta);
        this.baseEntity = baseEntity;
        this.color = color;
        this.style = style;
        this.model = model;

        super.setTypeLore("Mount", Icon.MOUNT.get());
        this.setStateType(StateType.MOUNT_STATE);
    }

    private static boolean canSpawnAt(Location loc) {
        // Horse is roughly 1 block wide → check a 3x3 horizontal area centered on loc
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 0; y < HORSE_HEIGHT; y++) {
                    Material blockType = loc.getWorld().getBlockAt(loc.clone().add(x, y, z)).getType();

                    // Ignore non-solid blocks (like grass, flowers, water, etc.)
                    if (blockType.isSolid()) return false;
                }
            }
        }
        return true;
    }

    public void initializeStats(int maxLevel, double baseSpeed, double baseJump, double speedLevelBonus, double jumpLevelBonus) {
        this.maxLevel = maxLevel;
        this.baseSpeed = baseSpeed;
        this.baseJump = baseJump;
        this.speedLevelBonus = speedLevelBonus;
        this.jumpLevelBonus = jumpLevelBonus;
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.MOUNT;
    }

    public void setDefaultStateTemplate(MountState state) {
        this.defaultStateTemplate = state;
    }

    public AbstractState cloneDefaultStateTemplate() {
        return defaultStateTemplate.clone();
    }

    @Nullable
    public MountState getState(ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state != null && state.getStateType() == StateType.MOUNT_STATE) {
            return (MountState) state;
        }

        return null;
    }

    @Nullable
    public String getModel() {
        return model;
    }

    @Nullable
    public Horse.Color getColor() {
        return color;
    }

    @Nullable
    public Horse.Style getStyle() {
        return style;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    // During initialization or on level up
    public void onLevelChange(RunePlayer player, int newLevel, UUID entityUUID, boolean isLevelUp) {
        Entity entity = Bukkit.getEntity(entityUUID);

        if (isLevelUp) {
            HudService.sendInfoMessage(player, Component.text("Your mount has leveled up!", Color.INFO.asColor()));
        }

        if (!(entity instanceof AbstractHorse horse)) {
            return;
        }

        AttributeInstance speedAttr = horse.getAttribute(Attribute.MOVEMENT_SPEED);
        if (speedAttr != null) {
            speedAttr.setBaseValue(this.baseSpeed + ((Math.max(0, newLevel - 1)) * this.speedLevelBonus));
        }

        horse.setJumpStrength(this.baseJump + ((Math.max(0, newLevel - 1)) * this.jumpLevelBonus));
    }

    @Nullable
    public UUID spawn(RunePlayer player, MountState state) {
        if (!canSpawnAt(player.asBukkitPlayer().getLocation())) {
            HudService.sendFailMessage(player, Component.text("Not enough space for your mount!"));
            return null;
        }

        if (state.getName() == null) {
            state.setName(player.asBukkitPlayer().getName() + "'s " + this.displayName, false);
        }

        Location loc = player.asBukkitPlayer().getLocation();
        Entity horseEntity = loc.getWorld().spawnEntity(loc, baseEntity == null ? EntityType.HORSE : baseEntity);

        if (!(horseEntity instanceof AbstractHorse abstractHorse)) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot spawn mount " + this.displayName + "! Base model is not AbstractHorse.");
            return null;
        }

        UUID uuid = abstractHorse.getUniqueId();
        EntitySessionManager.tagEntity(abstractHorse);

        abstractHorse.setTamed(true);
        abstractHorse.setOwner(player.asBukkitPlayer());
        abstractHorse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        abstractHorse.addPassenger(player.asBukkitPlayer());
        abstractHorse.customName(Component.empty());

        if (horseEntity instanceof Horse horse) {
            horse.setStyle(this.style != null ? this.style : Horse.Style.BLACK_DOTS);
            horse.setColor(this.color != null ? this.color : Horse.Color.BLACK);
        }

        if (this.model != null) setModel(abstractHorse, this.model);

        onLevelChange(player, state.getLevel(this), uuid, false);
        return uuid;
    }

    private void setModel(LivingEntity entity, String model) {
        try {
            ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(entity);
            ActiveModel activeModel = ModelEngineAPI.createActiveModel(model);
            modeledEntity.addModel(activeModel, true);
            entity.setInvisible(true);
            entity.setSilent(true);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot set mounts '" + this.displayName + "' disguise!", e);
        }
    }

}
