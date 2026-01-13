package org.zuma.examplePlugin.domain.housing.instance;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.housing.HousingManager;
import org.zuma.examplePlugin.domain.housing.records.HouseFurnitureEntry;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.housing.template.TemplateMeta;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.FurnitureItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.*;

public class HouseEditModeManager {
    private final HouseInstance instance;
    private final List<Hologram> editHolograms = new ArrayList<>();
    private boolean editMode;
    private Integer editingSlotIndex;

    public HouseEditModeManager(HouseInstance instance) {
        this.instance = instance;
        editingSlotIndex = null;
    }

    void updateHologramForSlot(Player player, int slotIndex, HouseSlot houseSlot) {
        if (editMode) {
            String hologramKey = "house_" + instance.getInstanceUUID() + "_" + slotIndex;
            HashMap<Integer, HouseFurnitureEntry> furnitureMap = RuneQuestPlugin.getHousingManager().getInstanceFurniture(instance.getInstanceUUID());

            if (houseSlot == null) {
                return;
            }

            Hologram existingHologram = DHAPI.getHologram(hologramKey);

            if (existingHologram != null && editHolograms.contains(existingHologram)) {
                editHolograms.remove(existingHologram);
                existingHologram.delete();
            }

            Location slotLocation = HousingManager.getLocationInHouse(houseSlot.absoluteLocation(), this.instance);
            HouseFurnitureEntry occupiedBy = furnitureMap.get(slotIndex);
            AbstractItemType type = occupiedBy != null ? RuneQuestPlugin.getItemRegistry().getType(occupiedBy.itemId()) : null;

            List<String> hologramLines = occupiedBy == null ?
                    List.of(houseSlot.type().getDisplayName() + " §7Slot") :
                    List.of(type != null ? TextUtils.hexToMc(type.rarity.getHexColor()) + "[" + type.displayName + "]" : "§4[UNKNOWN]");

            Location hologramLocation = slotLocation.clone().add(0.5, occupiedBy == null ? 0.5 : 1.5, 0.5);

            // If slot is occupied, offset hologram by 0.25 in the furniture's facing direction
            if (occupiedBy != null) {
               /* BlockFace face = occupiedBy.face();
                switch (face) {
                    case NORTH -> hologramLocation.add(0, 0, -0.25);
                    case SOUTH -> hologramLocation.add(0, 0, 0.25);
                    case EAST -> hologramLocation.add(0.25, 0, 0);
                    case WEST -> hologramLocation.add(-0.25, 0, 0);
                }*/

                if (occupiedBy.itemId() == ItemId.COMBAT_DUMMY_FURNITURE.asInt()) {
                    hologramLocation.add(0, 1.5, 0);
                }
            }

            Hologram hologram = DHAPI.createHologram(hologramKey, hologramLocation, hologramLines);
            hologram.setDefaultVisibleState(false);
            hologram.setShowPlayer(player);

            editHolograms.add(hologram);

        }
    }

    @Nullable
    public HouseSlot getSlotAtIndex(int slotIndex) {
        TemplateMeta meta = RuneQuestPlugin.getHousingManager().getTemplateMeta(instance.getTemplate());
        return meta.slots().get(slotIndex);
    }

    @Nullable
    public HashMap<Integer, HouseFurnitureEntry> getFurnitureMap() {
        return RuneQuestPlugin.getHousingManager().getInstanceFurniture(instance.getInstanceUUID());
    }

    public void placeFurnitureItem(RunePlayer player, int slotIndex, Hologram clickedHologram) {
        HashMap<Integer, HouseFurnitureEntry> furnitureMap = getFurnitureMap();
        HouseSlot clickedSlot = getSlotAtIndex(slotIndex);

        if (!player.getUUID().equals(this.instance.getOwnerUUID())) {
            throw new IllegalStateException("Only the owner of the house can place furniture items.");
        }

        if (furnitureMap == null) {
            throw new IllegalStateException("Could not retrieve furniture map for house instance.");
        }

        if (clickedSlot == null) {
            throw new IllegalStateException("Slot index " + slotIndex + " does not exist in house template.");
        }

        if (furnitureMap.containsKey(slotIndex)) {
            throw new IllegalStateException("A furniture item is already placed in the specified slot.");
        }

        AbstractItemType inHand = RuneQuestPlugin.getItemRegistry().getType(player.asBukkitPlayer().getInventory().getItemInMainHand());

        if (!(inHand instanceof FurnitureItem furniture)) {
            throw new IllegalStateException("You must be holding a furniture item to place it in the slot.");
        }

        if (!clickedSlot.type().equals(furniture.getApplicableSlot())) {
            throw new IllegalStateException("This furniture item cannot be placed in the selected slot.");
        }

        BlockFace face = player.asBukkitPlayer().getFacing().getOppositeFace();

        HouseFurnitureEntry newEntry = new HouseFurnitureEntry(
                UUID.randomUUID(),
                inHand.id,
                slotIndex,
                face,
                clickedSlot.type(),
                "{}" // Default JSON, can be modified later
        );

        RuneQuestPlugin.getHousingManager().getInstanceCreator().placeFurnitureItem(clickedSlot, instance, face, furniture.id, slotIndex);

        boolean success = PlayerUtils.subtractOneFromMainHand(player);

        if (!success) {
            HudService.sendFailMessage(player, Component.text("Failed to consume item from your hand!", Color.ERROR.asColor()));
            return;
        }

        furnitureMap.put(slotIndex, newEntry);
        SoundType.PLACE_BLOCK_GENERIC.play(player, true);
        clickedHologram.delete();
        this.updateHologramForSlot(player.asBukkitPlayer(), slotIndex, clickedSlot);
        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.HOUSE_FURNITURE_PLACED, 1);
    }

    public void rotateFurnitureItem(Player player, int slotIndex) {
        HashMap<Integer, HouseFurnitureEntry> furnitureMap = getFurnitureMap();
        HouseSlot clickedSlot = getSlotAtIndex(slotIndex);

        if (!player.getUniqueId().equals(this.instance.getOwnerUUID())) {
            throw new IllegalStateException("Only the owner of the house can rotate furniture items.");
        }

        if (furnitureMap == null) {
            throw new IllegalStateException("Could not retrieve furniture map for house instance.");
        }

        if (!furnitureMap.containsKey(slotIndex)) {
            throw new IllegalStateException("No furniture item found in the specified slot to rotate.");
        }

        if (clickedSlot == null) {
            throw new IllegalStateException("Slot index " + slotIndex + " does not exist in house template.");
        }

        HouseFurnitureEntry entry = furnitureMap.get(slotIndex);
        BlockFace currentFace = entry.face();
        BlockFace newFace = BlockFace.NORTH;

        switch (currentFace) {
            case NORTH -> newFace = BlockFace.EAST;
            case EAST -> newFace = BlockFace.SOUTH;
            case SOUTH -> newFace = BlockFace.WEST;
        }

        entry.setFace(newFace);
        RuneQuestPlugin.getHousingManager().getInstanceCreator().rotateFurnitureItem(clickedSlot, instance, newFace, furnitureMap.get(slotIndex).itemId(), slotIndex);
        //updateHologramForSlot(player, slotIndex, clickedSlot);
        SoundType.ROTATE_BLOCK_GENERIC.play(player, true);
    }

    public void removeFurnitureItem(Player player, int slotIndex) {
        HashMap<Integer, HouseFurnitureEntry> furnitureMap = getFurnitureMap();
        HouseSlot clickedSlot = getSlotAtIndex(slotIndex);

        if (!player.getUniqueId().equals(this.instance.getOwnerUUID())) {
            throw new IllegalStateException("Only the owner of the house can rotate furniture items.");
        }

        if (furnitureMap == null) {
            throw new IllegalStateException("Could not retrieve furniture map for house instance.");
        }

        if (!furnitureMap.containsKey(slotIndex)) {
            throw new IllegalStateException("No furniture item found in the specified slot to remove.");
        }

        if (clickedSlot == null) {
            throw new IllegalStateException("Slot index " + slotIndex + " does not exist in house template.");
        }

        HouseFurnitureEntry entry = furnitureMap.get(slotIndex);
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            return;
        }

        Location clickedBlockLocation = HousingManager.getLocationInHouse(clickedSlot.absoluteLocation(), instance);
        AbstractInteractableBlock block = RuneQuestPlugin.getBlockManager().getBlockAt(clickedBlockLocation.getBlock());

        if (block != null) {
            RuneQuestPlugin.getBlockManager().unregisterBlock(block);
        } else {
            clickedBlockLocation.getBlock().setType(Material.AIR);
        }

        instance.getInteractableLocations().remove(clickedBlockLocation);
        runePlayer.giveItemOrDrop(new ItemRecord(entry.itemId(), 1));
        furnitureMap.remove(slotIndex);
        updateHologramForSlot(player, slotIndex, clickedSlot);
        SoundType.BREAK_BLOCK_GENERIC.play(player, true);
        AnalyticsUtils.incrementServerAndPlayer(runePlayer, AnalyticsKey.HOUSE_FURNITURE_REMOVED, 1);
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        Player owner = Bukkit.getPlayer(instance.getOwnerUUID());

        if (owner == null) {
            return;
        }

        this.editMode = editMode;

        if (!editMode) {
            for (Hologram hologram : editHolograms) {
                hologram.delete();
            }
            editHolograms.clear();
        }

        if (editMode) {
            TemplateMeta meta = RuneQuestPlugin.getHousingManager().getTemplateMeta(instance.getTemplate());

            for (Map.Entry<Integer, HouseSlot> slot : meta.slots().entrySet()) {
                updateHologramForSlot(owner, slot.getKey(), slot.getValue());
            }
        }
    }

    public void cleanup() {
        for (Hologram hologram : editHolograms) {
            hologram.delete();
        }
        editHolograms.clear();
    }

    public Integer getEditingSlotIndex() {
        return editingSlotIndex;
    }

    public void openSlotEditMenu(Integer editingSlotIndex, RunePlayer player) {
        this.editingSlotIndex = editingSlotIndex;
        RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.HOUSE_SLOT_EDITOR.asInt(), player, null);
    }
}
