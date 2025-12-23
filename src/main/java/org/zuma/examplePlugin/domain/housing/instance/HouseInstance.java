package org.zuma.examplePlugin.domain.housing.instance;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.ItemUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.housing.records.HouseFurnitureEntry;
import org.zuma.examplePlugin.domain.housing.HousingManager;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.housing.template.HouseTemplate;
import org.zuma.examplePlugin.domain.housing.template.TemplateMeta;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.FurnitureItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.schematic.InteractiveElement;
import org.zuma.examplePlugin.events.housing.HouseSettingsUpdateEvent;

import java.util.*;

public class HouseInstance implements Listener {
    private final UUID ownerUUID, instanceUUID;
    private final String ownerName;
    private final List<InteractiveElement> interactiveElements;
    private final List<Location> interactableLocations;
    private final List<Hologram> editHolograms = new ArrayList<>();
    private final int gridX, gridZ;
    private final HouseTemplate template;
    private final Location spawnPoint;

    // Instance based configuration for a player's house
    private boolean openToPublic, editMode;
    private final List<UUID> invitedPlayers;
    private final List<UUID> currentVisitors;

    public HouseInstance(UUID ownerUUID, UUID instanceUUID, String ownerName, int gridX, int gridZ, List<InteractiveElement> interactiveElements, Location spawnPoint, HouseTemplate template) {
        this.ownerUUID = ownerUUID;
        this.instanceUUID = instanceUUID;
        this.ownerName = ownerName;
        this.gridX = gridX;
        this.gridZ = gridZ;
        this.interactiveElements = interactiveElements;
        this.spawnPoint = spawnPoint;
        this.template = template;

        this.openToPublic = false;
        this.invitedPlayers = new ArrayList<>();
        this.currentVisitors = new ArrayList<>();
        this.interactableLocations = new ArrayList<>();
        this.editMode = false;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridZ() {
        return gridZ;
    }

    public HashMap<Integer, HouseFurnitureEntry> getFurnitureMap() {
        return RuneQuestPlugin.getHousingManager().getInstanceFurniture(this.instanceUUID);
    }

    public List<InteractiveElement> getInteractiveElements() {
        return interactiveElements;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public HouseTemplate getTemplate() {
        return template;
    }

    // Housing settings

    public boolean isOpenToPublic() {
        return openToPublic;
    }

    public void setOpenToPublic(boolean openToPublic) {
        this.openToPublic = openToPublic;
        Bukkit.getServer().getPluginManager().callEvent(new HouseSettingsUpdateEvent(this));
    }

    public List<UUID> getInvitedPlayers() {
        return invitedPlayers;
    }

    public void invitePlayer(String playerName) {
        Player bukkitPlayer = RuneQuestPlugin.getInstance().getServer().getPlayer(playerName);

        if (bukkitPlayer == null) {
            throw new IllegalStateException("Player " + playerName + " is not online.");
        }

        if (invitedPlayers.contains(bukkitPlayer.getUniqueId())) {
            throw new IllegalStateException("Player " + bukkitPlayer.getName() + " is already invited to this house.");
        }

        if (bukkitPlayer.getUniqueId().equals(this.ownerUUID)) {
            throw new IllegalStateException("Owner of the house cannot be invited.");
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(bukkitPlayer);

        if (runePlayer == null) {
            return;
        }

        invitedPlayers.add(bukkitPlayer.getUniqueId());
        HudService.sendInfoMessage(runePlayer, Component.text("You have been invited to " + this.ownerName + " 's house in " + this.template.getRegion().getName() + "."));
        Bukkit.getServer().getPluginManager().callEvent(new HouseSettingsUpdateEvent(this));
    }

    public void uninvitePlayer(String playerName) {
        Player bukkitPlayer = RuneQuestPlugin.getInstance().getServer().getPlayer(playerName);

        if (bukkitPlayer == null) {
            throw new IllegalStateException("Player " + playerName + " is not online.");
        }

        uninvitePlayer(bukkitPlayer.getUniqueId());
    }

    public void uninvitePlayer(UUID uuid) {
        if (!invitedPlayers.contains(uuid)) {
            throw new IllegalStateException("This player is not in the invitations list.");
        }

        if (uuid.equals(this.ownerUUID)) {
            throw new IllegalStateException("Cannot uninvite the owner of the house.");
        }

        invitedPlayers.remove(uuid);

        if (currentVisitors.contains(uuid)) {
            kickVisitor(uuid, true);
        }

        Bukkit.getServer().getPluginManager().callEvent(new HouseSettingsUpdateEvent(this));
    }

    public void kickVisitor(UUID uuid, boolean callEvent) {
        Player bukkitPlayer = RuneQuestPlugin.getInstance().getServer().getPlayer(uuid);

        if (bukkitPlayer == null || !currentVisitors.contains(uuid)) {
            throw new IllegalStateException("This player is not in this house.");
        }

        if (uuid.equals(this.ownerUUID)) {
            throw new IllegalStateException("Cannot kick the owner of the house.");
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(bukkitPlayer);

        if (runePlayer == null) {
            return;
        }

        HudService.sendFailMessage(runePlayer, Component.text("You have been kicked from " + this.ownerName + "'s house."));
        handleLeave(runePlayer, callEvent);
    }

    public void kickAll() {
        for (UUID uuid : new ArrayList<>(currentVisitors)) {
            if (uuid.equals(this.ownerUUID)) {
                continue; // Don't kick the owner
            }
            kickVisitor(uuid, false);
        }

        Bukkit.getServer().getPluginManager().callEvent(new HouseSettingsUpdateEvent(this));
    }

    public boolean canJoin(RunePlayer visitor) {
        if (visitor.getUUID().equals(this.ownerUUID)) {
            return true; // Owner can always join
        }

        if (this.openToPublic) {
            return true; // Public house
        }

        return invitedPlayers.contains(visitor.getUUID());
    }

    public void teleportToHouse(RunePlayer visitor) {
        if (!visitor.getUUID().equals(this.ownerUUID)) { // Only check invitations for non-owners
            if (!this.openToPublic && !invitedPlayers.contains(visitor.getUUID())) {
                throw new IllegalStateException("Player " + visitor.asBukkitPlayer().getName() + " is not invited to this house.");
            }

            if (currentVisitors.contains(visitor.getUUID())) {
                throw new IllegalStateException("Player " + visitor.asBukkitPlayer().getName() + " is already in this house.");
            }
        }

        visitor.setSafeLocation(visitor.asBukkitPlayer().getLocation());
        currentVisitors.add(visitor.getUUID());
        visitor.asBukkitPlayer().teleport(this.spawnPoint);
        Bukkit.getServer().getPluginManager().callEvent(new HouseSettingsUpdateEvent(this));
    }

    public void exitHouse(RunePlayer visitor) {
        handleLeave(visitor, true);
    }

    // Is mutated by housing manager when player disconnects
    public List<UUID> getCurrentVisitors() {
        return currentVisitors;
    }

    private void handleLeave(RunePlayer visitor, boolean callEvent) {
        currentVisitors.remove(visitor.getUUID());
        visitor.asBukkitPlayer().teleport(this.template.getRegion().getExitLocation());
        visitor.setSafeLocation(null);

        if (callEvent) Bukkit.getServer().getPluginManager().callEvent(new HouseSettingsUpdateEvent(this));
    }

    public UUID getInstanceUUID() {
        return instanceUUID;
    }

    public void removeFromVisitors(RunePlayer visitor) {
        if (!currentVisitors.contains(visitor.getUUID())) {
            return;
        }

        currentVisitors.remove(visitor.getUUID());
        visitor.setSafeLocation(null);
    }

    public List<Location> getInteractableLocations() {
        return interactableLocations;
    }

    public void cleanup() {
        for (Location loc : interactableLocations) {
            AbstractInteractableBlock block = RuneQuestPlugin.getBlockManager().getBlockAt(loc.getBlock());
            if (block != null) {
                RuneQuestPlugin.getBlockManager().unregisterBlock(block);
            }
        }

        for (UUID visitor : currentVisitors) {
            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(visitor);

            if (runePlayer != null) {
                handleLeave(runePlayer, false);
                HudService.sendInfoMessage(runePlayer, Component.text("You have been teleported out of the house. The owner has left the game."));
            }
        }

        for (Hologram hologram : editHolograms) {
            hologram.delete();
        }

        interactiveElements.clear();
        interactableLocations.clear();
        invitedPlayers.clear();

        Bukkit.getServer().getPluginManager().callEvent(new HouseSettingsUpdateEvent(this));
    }

    public void setEditMode(boolean editMode) {
        Player owner = Bukkit.getPlayer(this.ownerUUID);

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
            TemplateMeta meta = RuneQuestPlugin.getHousingManager().getTemplateMeta(this.getTemplate());

            for (Map.Entry<Integer, HouseSlot> slot : meta.slots().entrySet()) {
                updateHologramForSlot(owner, slot.getKey(), slot.getValue());
            }
        }
    }

    private void updateHologramForSlot(Player player, int slotIndex, HouseSlot houseSlot) {
        if (editMode) {
            HashMap<Integer, HouseFurnitureEntry> furnitureMap = RuneQuestPlugin.getHousingManager().getInstanceFurniture(this.instanceUUID);

            if (houseSlot == null) {
                return;
            }

            Location slotLocation = HousingManager.getLocationInHouse(houseSlot.absoluteLocation(), this);

            HouseFurnitureEntry occupiedBy = furnitureMap.get(slotIndex);
            AbstractItemType type = occupiedBy != null ? RuneQuestPlugin.getItemRegistry().getType(occupiedBy.itemId()) : null;

            List<String> hologramLines = occupiedBy == null ?
                    List.of(houseSlot.type().getDisplayName() + " §7Slot") :
                    List.of(type != null ? "§e[" + type.displayName + "]" : "§4[UNKNOWN]");

            Hologram hologram = DHAPI.createHologram("house_" + this.instanceUUID + "_" + slotIndex, slotLocation.clone().add(0.5, occupiedBy == null ? 0.5 : 1.5, 0.5), hologramLines);
            hologram.setDefaultVisibleState(false);
            hologram.setShowPlayer(player);

            editHolograms.add(hologram);

        }
    }

    public void handleSlotClick(RunePlayer player, int slotIndex, Hologram clickedHologram) {
        try {
            TemplateMeta meta = RuneQuestPlugin.getHousingManager().getTemplateMeta(this.getTemplate());
            HouseSlot clickedSlot = meta.slots().get(slotIndex);
            HashMap<Integer, HouseFurnitureEntry> furnitureMap = RuneQuestPlugin.getHousingManager().getInstanceFurniture(this.instanceUUID);
            boolean isSlotEmpty = !furnitureMap.containsKey(slotIndex);

            if (!meta.slots().containsKey(slotIndex) || clickedSlot == null) {
                throw new IllegalStateException("Slot index " + slotIndex + " does not exist in house template.");
            }

            if (isSlotEmpty) {
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

                RuneQuestPlugin.getHousingManager().getInstanceCreator().placeFurnitureItem(clickedSlot, this, face, furniture.id, slotIndex);
                PlayerUtils.subtractOneFromMainHand(player);
                furnitureMap.put(slotIndex, newEntry);
                SoundType.PLACE_BLOCK_GENERIC.play(player, true);
                clickedHologram.delete();
                updateHologramForSlot(player.asBukkitPlayer(), slotIndex, clickedSlot);
            }
        } catch (Exception e) {
            HudService.sendFailMessage(player, Component.text(e.getMessage()));
        }
    }

    public void handleFurnitureEdit(RunePlayer player, int slotIndex, HouseFurnitureEntry newEntry) {
        TemplateMeta meta = RuneQuestPlugin.getHousingManager().getTemplateMeta(this.getTemplate());
        HashMap<Integer, HouseFurnitureEntry> furnitureMap = RuneQuestPlugin.getHousingManager().getInstanceFurniture(this.instanceUUID);

        if (!meta.slots().containsKey(slotIndex)) {
            throw new IllegalStateException("Slot index " + slotIndex + " does not exist in house template.");
        }

        furnitureMap.put(slotIndex, newEntry);

    }

    public boolean isEditMode() {
        return editMode;
    }
}
