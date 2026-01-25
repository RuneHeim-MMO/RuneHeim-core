package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemSpawnBlock extends AbstractInteractableBlock {
    private ItemRecord itemRecord;
    private int respawnDurationSeconds;
    private boolean isAvailable;

    public ItemSpawnBlock(AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
        this.isAvailable = true;
    }

    public void initializeBlock(ItemRecord itemRecord, int respawnDurationSeconds) {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(itemRecord.getItemId());

        if (type == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "ItemSpawnBlock initialization failed: ItemType not found for item ID " + itemRecord.getItemId());
        }

        this.itemRecord = itemRecord;
        this.respawnDurationSeconds = respawnDurationSeconds;

        spawnFrame();
        super.spawn(Material.AIR);
    }

    public void spawnFrame() {
        if (super.getEntityChildUUID() != null) {
            return;
        }

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(itemRecord.getItemId());

        if (type == null) {
            return;
        }

        Location location = getLocation();

        ItemFrame frame = location.getWorld().spawn(location, ItemFrame.class, f -> {
            f.setFacingDirection(BlockFace.UP, true);
            ItemStack stack = type.createItemStackOfType(itemRecord.getAmount());
            ItemMeta meta = stack.getItemMeta();

            if (meta.displayName() != null)
                meta.displayName(Component.text(itemRecord.getAmount() + "x ", TextColor.fromHexString(type.rarity.getHexColor())).append(meta.displayName()));
            stack.setItemMeta(meta);

            f.setItem(stack);
            f.setRotation(Rotation.NONE);
        });

        super.setEntityChildUUID(frame.getUniqueId());
    }

    @Override
    public void onPlayerInteractEntityEvent(RunePlayer player, Entity entity, PlayerInteractEntityEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);

        if (!isAvailable) {
            return;
        }

        isAvailable = false;
        player.giveItemOrDrop(this.itemRecord);


        if (entity != null) {
            entity.remove();
            super.setEntityChildUUID(null);
        }


        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            isAvailable = true;
            spawnFrame();
        }, respawnDurationSeconds * 20L);
    }

    @Override
    public void onDisable() {
        if (super.getEntityChildUUID() != null) {
            Entity frame = Bukkit.getEntity(this.getEntityChildUUID());
            if (frame != null) {
                frame.remove();
                super.setEntityChildUUID(null);
            }
        }

        super.onDisable();
    }
}
