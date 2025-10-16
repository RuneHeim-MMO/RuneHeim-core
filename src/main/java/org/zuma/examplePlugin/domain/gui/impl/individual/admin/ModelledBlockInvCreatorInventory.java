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
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.block.meta.v1.InventoryOpenerModelledMeta;
import org.zuma.examplePlugin.domain.block.persistence.BlockEntity;
import org.zuma.examplePlugin.domain.gui.GUIHandler;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.HashMap;
import java.util.List;


public class ModelledBlockInvCreatorInventory extends IndividualGUI {
    private final BlockLoader loader;
    private final GUIHandler guiHandler;

    private static final HashMap<Integer, InventoryOpenerModelledMeta> META_MAP = new HashMap<>() {{
        put(InventoryId.CONTRACT_BOARD.asInt(), new InventoryOpenerModelledMeta(InventoryId.CONTRACT_BOARD.asInt(), Material.BARRIER, null,
                "quest_board", 3, 3, 2, MarkerType.CONTRACT_BOARD, "Contract Board", "Contract Board", Icon.CONTRACT_SKULL.get()));

    }};

    public ModelledBlockInvCreatorInventory(String title, int id, BlockLoader loader, GUIHandler guiHandler) {
        super(title, id, 6);
        this.loader = loader;
        this.guiHandler = guiHandler;
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        if (!player.getRole().hasPermission(Permission.INTERACTABLE_SPAWN)) {
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
            InventoryOpenerModelledMeta meta = META_MAP.get(inventoryId);

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


        loader.insertNewBlock(new BlockEntity(
                new AbstractInteractableBlockMeta(0, BlockType.InventoryOpenerModelled, oppositeFace, result.getHitBlock().getLocation(), hologramLocation),
                new VersionedJson(new Gson().toJson(META_MAP.get(menuValue)), 1)));

    }


}
