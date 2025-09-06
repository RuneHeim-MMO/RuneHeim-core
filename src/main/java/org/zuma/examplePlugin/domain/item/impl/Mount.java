package org.zuma.examplePlugin.domain.item.impl;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
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

    private MountState defaultStateTemplate;
    private int maxLevel;
    private double baseSpeed;

    @Nullable
    private final String model;
    @Nullable
    private final Horse.Color color;
    @Nullable
    private final Horse.Style style;

    public Mount(AbstractItemMeta meta, @Nullable String model, @Nullable Horse.Color color, @Nullable Horse.Style style) {
        super(meta);
        this.color = color;
        this.style = style;
        this.model = model;

        super.setTypeLore("Mount", Icon.MOUNT.get());
        this.setStateType(StateType.MOUNT_STATE);
    }

    public void initializeStats(int maxLevel, double baseSpeed) {
        this.maxLevel = maxLevel;
        this.baseSpeed = baseSpeed;
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

    private static boolean canSpawnAt(Location loc) {
        // Horse is roughly 1 block wide → check a 3x3 horizontal area centered on loc
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 0; y < HORSE_HEIGHT; y++) {
                    Material blockType = loc.getWorld().getBlockAt(loc.clone().add(x, y, z)).getType();
                    if (!blockType.isAir()) return false;
                }
            }
        }
        return true;
    }

    // During initialization or on level up
    public void onLevelChange(RunePlayer player, int newLevel, UUID entityUUID, boolean isLevelUp) {
        Entity entity = Bukkit.getEntity(entityUUID);

        if (isLevelUp) {
            HudService.sendInfoMessage(player, Component.text("Your ", Color.INFO.asColor())
                    .append(Component.text(this.displayName, Color.INFO.asColor()))
                    .append(Component.text(" has leveled up!", Color.INFO.asColor())));
        }

        if (!(entity instanceof Horse horse)) {
            return;
        }

        AttributeInstance speedAttr = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (speedAttr != null) {
            speedAttr.setBaseValue(0.1125);
        }
        horse.setJumpStrength(0.5);
    }

    @Nullable
    public UUID spawn(RunePlayer player, MountState state) {
        if (!canSpawnAt(player.asBukkitPlayer().getLocation())) {
            HudService.sendFailMessage(player, Component.text("Not enough space for your mount!"));
            return null;
        }

        if (state.getName() == null) {
            state.setName(player.asBukkitPlayer().getName() + "'s Mount");
        }

        Horse horse = player.asBukkitPlayer().getLocation().getWorld().spawn(player.asBukkitPlayer().getLocation(), Horse.class);
        UUID uuid = horse.getUniqueId();
        EntitySessionManager.tagEntity(horse);

        horse.setTamed(true);
        horse.setOwner(player.asBukkitPlayer());
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.addPassenger(player.asBukkitPlayer());
        horse.customName(Component.empty());

        horse.setStyle(this.style != null ? this.style : Horse.Style.BLACK_DOTS);
        horse.setColor(this.color != null ? this.color : Horse.Color.BLACK);

        if (this.model != null) setModel(horse, this.model);

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
