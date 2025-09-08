package org.zuma.examplePlugin.domain.gui.impl.individual.admin;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.block.meta.v1.InventoryOpenerMeta;
import org.zuma.examplePlugin.domain.block.persistence.BlockEntity;
import org.zuma.examplePlugin.domain.gui.GUIHandler;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.HashMap;
import java.util.List;


public class BlockInvCreatorInventory extends IndividualGUI {
    private final BlockLoader loader;
    private final GUIHandler guiHandler;

    private static final HashMap<Integer, InventoryOpenerMeta> META_MAP = new HashMap<>() {{
        put(InventoryId.STOVE.asInt(), new InventoryOpenerMeta(InventoryId.STOVE.asInt(), Material.FURNACE, null, MarkerType.FIRE_SOURCE, "Stove", "Stove", Icon.FIRE.get()));
        put(InventoryId.FIRE.asInt(), new InventoryOpenerMeta(InventoryId.FIRE.asInt(), Material.CAMPFIRE, null, MarkerType.FIRE_SOURCE, "Campfire", "Campfire", Icon.FIRE.get()));
        put(InventoryId.ANVIL.asInt(), new InventoryOpenerMeta(InventoryId.ANVIL.asInt(), Material.ANVIL, null, MarkerType.ANVIL, "Anvil", "Anvil", Icon.ANVIL.get()));
        put(InventoryId.CRAFTING_TABLE.asInt(), new InventoryOpenerMeta(InventoryId.CRAFTING_TABLE.asInt(), Material.CRAFTING_TABLE, null, MarkerType.CRAFTING_TABLE, "Crafting Table", "Crafting Table", Icon.CRAFTING.get()));
        put(InventoryId.BASIC_FURNACE.asInt(), new InventoryOpenerMeta(InventoryId.BASIC_FURNACE.asInt(), Material.BLAST_FURNACE, null, MarkerType.SMELTING_FURNACE, "Smelting Furnace", "Smelting Furnace", Icon.FURNACE.get()));
        put(InventoryId.FUSION_MASTER.asInt(), new InventoryOpenerMeta(InventoryId.FUSION_MASTER.asInt(), Material.BARRIER, null, null, "Fusion Master", "Fusion Master", Icon.SCROLL_RED.get()));
        put(InventoryId.SPINNING_WHEEL.asInt(), new InventoryOpenerMeta(InventoryId.SPINNING_WHEEL.asInt(), Material.BARRIER, null, MarkerType.SPINNING_WHEEL, "Spinning Wheel", "Spinning Wheel", Icon.MATERIAL.get()));
    }};

    public BlockInvCreatorInventory(String title, int id, BlockLoader loader, GUIHandler guiHandler) {
        super(title, id, 6);
        this.loader = loader;
        this.guiHandler = guiHandler;
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        if (!player.asBukkitPlayer().isOp()) {
            return false;
        }

        Inventory inventory = getBaseInventory();
        List<Integer> inventories = guiHandler.getRegistry().getAllInventoryIds().stream().filter(META_MAP::containsKey).toList();

        for (int i = 0; i < inventories.size(); i++) {
            if (i >= super.getSlots() - 1) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, title + " inventory is overflowing.");
                continue;
            }

            int inventoryId = inventories.get(i);
            InventoryOpenerMeta meta = META_MAP.get(inventoryId);

            if (meta == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "No meta found for inventory ID: " + inventoryId);
                continue;
            }

            ItemStack item = new ItemStack(Material.BARRIER);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.displayName(Component.text(meta.markerLabel(), NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
            IllegalItemWatchdog.tagIllegalItem(itemMeta);
            item.setItemMeta(itemMeta);

            super.assignMenuValue(item, inventoryId);
            inventory.setItem(i, item);
        }


        ItemStack item = new ItemStack(Material.LECTERN);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text("Praying Table", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        super.assignMenuValue(item, 1337);
        inventory.setItem(inventory.getSize() - 2, item);

        ItemStack undo = new ItemStack(Material.BARRIER);
        ItemMeta meta = undo.getItemMeta();
        meta.displayName(Component.text("Undo", Color.ERROR.asColor()));
        undo.setItemMeta(meta);
        IllegalItemWatchdog.tagIllegalItem(undo);

        super.assignMenuValue(undo, -1);
        inventory.setItem(super.getSlots() - 1, undo);

        player.asBukkitPlayer().openInventory(inventory);
        return true;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue == null) {
            return;
        }

        if (menuValue == -1) { // Undo
            loader.undoLastInsertion();
            return;
        }

        RayTraceResult result = player.asBukkitPlayer().rayTraceBlocks(20);

        if (result == null || result.getHitBlock() == null || result.getHitPosition() == null) {
            return;
        }

        Block block = result.getHitBlock();
        Vector hitPos = result.getHitPosition();
        Location blockLoc = block.getLocation();

        // Determine the face based on where the hit position is relative to block center
        double dx = hitPos.getX() - blockLoc.getX();
        double dy = hitPos.getY() - blockLoc.getY();
        double dz = hitPos.getZ() - blockLoc.getZ();

        HologramSide side;

        if (Math.abs(dx - 0.5) > Math.abs(dy - 0.5) && Math.abs(dx - 0.5) > Math.abs(dz - 0.5)) {
            side = dx < 0.5 ? HologramSide.LEFT : HologramSide.RIGHT;
        } else if (Math.abs(dz - 0.5) > Math.abs(dy - 0.5)) {
            side = dz < 0.5 ? HologramSide.FRONT : HologramSide.BACK;
        } else {
            side = dy < 0.5 ? HologramSide.BOTTOM : HologramSide.TOP;
        }

        Location hologramLocation = result.getHitBlock().getLocation().clone().add(0.5, 1.5, 0.5);
        BlockFace oppositeFace = MathUtils.yawToFace(player.asBukkitPlayer().getYaw()).getOppositeFace();

        switch (side) {
            case TOP -> hologramLocation.add(0, 0, 0);
            case BOTTOM -> hologramLocation.add(0, 0, 0);
            case FRONT -> hologramLocation.add(0, 0, -1);
            case BACK -> hologramLocation.add(0, 0, 1);
            case LEFT -> hologramLocation.add(-1, 0, 0);
            case RIGHT -> hologramLocation.add(1, 0, 0);
        }

        // Praying table
        if (menuValue == 1337) {
            loader.insertNewBlock(new BlockEntity(
                    new AbstractInteractableBlockMeta(0, BlockType.Altar, oppositeFace, result.getHitBlock().getLocation(), hologramLocation),
                    null));

            return;
        }

        loader.insertNewBlock(new BlockEntity(
                new AbstractInteractableBlockMeta(0, BlockType.InventoryOpener, oppositeFace, result.getHitBlock().getLocation(), hologramLocation),
                new VersionedJson(new Gson().toJson(META_MAP.get(menuValue)), 1)));

    }


}
