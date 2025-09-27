package org.zuma.examplePlugin.core.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.Restrictable;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.territory.Territory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PlayerUtils {

    public static void teleportToClosestCity(Player player) {
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
        Location spawnPoint = player.getWorld().getSpawnLocation();

        if (runePlayer == null) {
            player.teleport(spawnPoint);
            return;
        }

        Territory closestTerritory = PlayerUtils.getClosestDiscoveredTerritory(runePlayer);

        if (closestTerritory != null && closestTerritory.getSpawnPoint() != null) {
            spawnPoint = closestTerritory.getSpawnPoint();
        }

        player.teleport(spawnPoint);
    }

    @Nullable
    public static Territory getClosestDiscoveredTerritory(RunePlayer player) {
        Territory closestTerritory = null;
        double closestDistance = Double.MAX_VALUE;

        for (Territory territory : RuneQuestPlugin.getTerritoryHandler().getTerritoryList()) {
            // skip undiscovered territories
            if (!player.getDiscoveredTerritoryIds().contains(territory.getId())) continue;

            // skip territories with no spawn point
            if (territory.getSpawnPoint() == null) continue;

            double distance = MathUtils.getLocationsDistanceXZ(player.asBukkitPlayer().getLocation(), territory.getCenterPoint());

            if (distance < closestDistance) {
                closestTerritory = territory;
                closestDistance = distance;
            }
        }

        return closestTerritory;
    }

    public static boolean hasCustomInventoryOpen(RunePlayer player) {
        InventoryType openType = player.asBukkitPlayer().getOpenInventory().getType();

        return openType != InventoryType.CRAFTING &&
                openType != InventoryType.CREATIVE;
    }

    public static ItemStack getItemStackInHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    public static int getLastSkillPercent(RunePlayer player) {
        player.asBukkitPlayer().setLevel(player.getCombatLvl());

        SkillType lastXPGainedSkillType = player.getLastXPGainedSkillType();

        if (lastXPGainedSkillType == null) {
            return 0;
        }

        Skill skill = player.getSkill(lastXPGainedSkillType);

        if (skill == null) {
            return 0;
        }

        return XpUtils.getProgressPercent((int) skill.getXp(), skill.getBaseLevel());
    }

    public static boolean hasItems(RunePlayer player, List<ItemRecord> requiredItems) {
        Map<Integer, Integer> itemCounts = new HashMap<>();
        NamespacedKey itemIdKey = PersistenceDataKey.ITEM_ID.key(RuneQuestPlugin.getInstance());

        // Count all item IDs in the player's main inventory (excluding armor/offhand)
        for (ItemStack stack : player.asBukkitPlayer().getInventory().getStorageContents()) {
            if (stack == null || stack.getType().isAir()) continue;

            ItemMeta meta = stack.getItemMeta();
            if (meta == null) continue;

            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (!container.has(itemIdKey, PersistentDataType.INTEGER)) continue;

            Integer id = container.get(itemIdKey, PersistentDataType.INTEGER);
            if (id == null) continue;

            int amount = stack.getAmount();
            itemCounts.put(id, itemCounts.getOrDefault(id, 0) + amount);
        }

        // Also count the item on the player's cursor
        ItemStack cursor = player.asBukkitPlayer().getItemOnCursor();
        if (!cursor.getType().isAir()) {
            ItemMeta meta = cursor.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(itemIdKey, PersistentDataType.INTEGER)) {
                    Integer id = container.get(itemIdKey, PersistentDataType.INTEGER);
                    if (id != null) {
                        int amount = cursor.getAmount();
                        itemCounts.put(id, itemCounts.getOrDefault(id, 0) + amount);
                    }
                }
            }
        }

        // Check if all required items are available in sufficient quantity
        for (ItemRecord record : requiredItems) {
            int requiredId = record.getItemId();
            int requiredAmount = record.getAmount();

            if (itemCounts.getOrDefault(requiredId, 0) < requiredAmount) {
                return false;
            }
        }

        return true;
    }


    public static boolean removeItems(RunePlayer player, List<ItemRecord> requiredItems) {
        if (!hasItems(player, requiredItems)) {
            return false;
        }

        NamespacedKey itemIdKey = PersistenceDataKey.ITEM_ID.key(RuneQuestPlugin.getInstance());
        PlayerInventory inventory = player.asBukkitPlayer().getInventory();
        ItemStack[] contents = inventory.getStorageContents();
        ItemStack cursor = player.asBukkitPlayer().getItemOnCursor();

        for (ItemRecord req : requiredItems) {
            int remainingToRemove = req.getAmount();
            int requiredId = req.getItemId();

            if (remainingToRemove <= 0) continue;

            // Step 1: Remove from main inventory
            for (int i = 0; i < contents.length; i++) {
                ItemStack stack = contents[i];
                if (stack == null || stack.getType().isAir()) continue;

                ItemMeta meta = stack.getItemMeta();
                if (meta == null) continue;

                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (!container.has(itemIdKey, PersistentDataType.INTEGER)) continue;

                Integer id = container.get(itemIdKey, PersistentDataType.INTEGER);
                if (id == null || id != requiredId) continue;

                int stackAmount = stack.getAmount();

                if (stackAmount <= remainingToRemove) {
                    remainingToRemove -= stackAmount;
                    contents[i] = null;
                } else {
                    stack.setAmount(stackAmount - remainingToRemove);
                    remainingToRemove = 0;
                    break;
                }

                if (remainingToRemove <= 0) break;
            }

            // Step 2: Remove from cursor if needed
            if (remainingToRemove > 0 && !cursor.getType().isAir()) {
                ItemMeta cursorMeta = cursor.getItemMeta();
                if (cursorMeta != null) {
                    PersistentDataContainer cursorContainer = cursorMeta.getPersistentDataContainer();
                    if (cursorContainer.has(itemIdKey, PersistentDataType.INTEGER)) {
                        Integer cursorId = cursorContainer.get(itemIdKey, PersistentDataType.INTEGER);
                        if (cursorId != null && cursorId == requiredId) {
                            int cursorAmount = cursor.getAmount();

                            if (cursorAmount <= remainingToRemove) {
                                remainingToRemove -= cursorAmount;
                                player.asBukkitPlayer().setItemOnCursor(null);
                            } else {
                                cursor.setAmount(cursorAmount - remainingToRemove);
                                remainingToRemove = 0;
                            }
                        }
                    }
                }
            }

            if (remainingToRemove > 0) {
                // Not enough items found, abort
                return false;
            }
        }

        inventory.setStorageContents(contents);
        player.asBukkitPlayer().updateInventory();
        return true;
    }


    public static void subtractOneFromMainHand(RunePlayer player) {
        ItemStack item = player.asBukkitPlayer().getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            return;
        }

        int amount = item.getAmount();

        if (amount <= 1) {
            player.asBukkitPlayer().getInventory().setItemInMainHand(null);
            return;
        }

        item.setAmount(amount - 1);
        player.asBukkitPlayer().getInventory().setItemInMainHand(item);

    }

    public static Location trunacePlayerFeetLocation(RunePlayer player) {
        if (player == null) return null;
        Location loc = player.asBukkitPlayer().getLocation();
        World world = loc.getWorld();

        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        return new Location(world, x, y, z, loc.getYaw(), loc.getPitch());
    }

    public static int getCraftSuccessChance(RunePlayer player, SkillRequirement requirement, int minPercentage) {
        int MAX_LEVEL = 99;
        int SAFE_OFFSET = 10;

        int requiredLevel = requirement.minimumLevel();
        int playerLevel = player.getSkill(requirement.type()).getBaseLevel();

        if (playerLevel >= MAX_LEVEL || playerLevel >= requiredLevel + SAFE_OFFSET) {
            return 100;
        }

        if (playerLevel < requiredLevel) {
            // Below requirement: minimum success chance (e.g. 30%)
            return minPercentage;
        }

        // Between [requiredLevel] and [requiredLevel + SAFE_OFFSET]
        int offset = playerLevel - requiredLevel;
        float progress = offset / (float) SAFE_OFFSET;
        return Math.round(minPercentage + ((100 - minPercentage) * progress)); // from minPercentage to 100%
    }

    public static boolean playerMeetsItemRequirements(RunePlayer player, Object item) {
        if (player.asBukkitPlayer().getGameMode() == GameMode.CREATIVE) {
            return true;
        }

        if (item instanceof Restrictable restrictable) {
            List<SkillRequirement> requirements = restrictable.getSkillRequirements();
            boolean hasRequiredSkills = requirements.stream().allMatch(req -> req.playerMeetsRequirement(player));
            boolean hasRequiredQuests = new HashSet<>(player.getCompletedQuestIds()).containsAll(restrictable.getRequiredCompletedQuests());

            return hasRequiredSkills && hasRequiredQuests;

        }
        return true;
    }

    public static void hideAllPlayersForPlayer(Player player) {
        for (Player p : player.getWorld().getPlayers()) {
            if (!p.equals(player)) {
                player.hidePlayer(RuneQuestPlugin.getInstance(), p);
            }
        }
    }

    public static void restorePlayersVisibilityForPlayer(Player player) {
        for (Player p : player.getWorld().getPlayers()) {
            if (!p.equals(player)) {
                player.showPlayer(RuneQuestPlugin.getInstance(), p);
            }
        }
    }

}
