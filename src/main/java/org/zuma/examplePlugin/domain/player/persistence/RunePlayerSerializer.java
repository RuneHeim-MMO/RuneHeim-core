package org.zuma.examplePlugin.domain.player.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.adapters.LocationAdapter;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.core.permissions.roles.BaseRole;
import org.zuma.examplePlugin.core.permissions.PlayerRole;
import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.serializer.ItemContainerContentSerializer;
import org.zuma.examplePlugin.domain.payments.SupporterLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.player.persistence.meta.v1.PlayerDataDeserializedJsonContainer;
import org.zuma.examplePlugin.domain.player.settings.PlayerSetting;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.territory.Territory;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RunePlayerSerializer {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Location.class, new LocationAdapter())
            .create();

    public static SerializedPlayerContainer serializePlayerSync(RunePlayer runePlayer, Player player, boolean clearStateCache) {
        PlayerDataDeserializedJsonContainer container = new PlayerDataDeserializedJsonContainer(
                runePlayer.getPouchCoins(),
                runePlayer.getDiscoveredTerritoryIds(),
                runePlayer.getHealth(),
                (float) runePlayer.getSpecialEnergyPercent() / 100,
                runePlayer.getPrayerEnergy(),
                runePlayer.getCompletedQuestIds() == null ? new ArrayList<>() : runePlayer.getCompletedQuestIds(),
                runePlayer.getActiveEffects()
        );

        VersionedJson versionedPlayerData = new VersionedJson(gson.toJson(container), 1);

        boolean locationVisibility = runePlayer.getSettings().getSetting(PlayerSetting.SHOW_LOCATION);
        @Nullable Territory lastTerritory = locationVisibility ? RuneQuestPlugin.getTerritoryHandler().getPlayersTerritory(runePlayer) : null;

        String serializedPlayerData = gson.toJson(versionedPlayerData);
        String serializedLocation = gson.toJson(runePlayer.getSafeLocation() != null ? runePlayer.getSafeLocation() : player.getLocation());
        String inventoryData = ItemContainerContentSerializer.serializeItemSlotPairsSync(getPlayerInventoryContent(player), clearStateCache);
        @Nullable String lastTerritoryId = lastTerritory != null ? lastTerritory.getId() : null;
        List<Skill> skills = runePlayer.getSkills().values().stream().toList();

        String baseRole = runePlayer.getRole().getBaseRole().toString();
        @Nullable String upgradedRole = runePlayer.getRole().getUpgradedRole() != null ? runePlayer.getRole().getUpgradedRole().toString() : null;

        return new SerializedPlayerContainer(
                runePlayer.getUUID(),
                player.getName(),
                skills,
                baseRole,
                upgradedRole,
                serializedLocation,
                lastTerritoryId,
                serializedPlayerData,
                inventoryData,
                runePlayer.getBombsContainer(),
                runePlayer.getSettings()
        );
    }

    public static AbstractMap.SimpleEntry<RunePlayer, DeserializedPlayerEntity> deserializePlayerSync(SerializedPlayerContainer container) {
        VersionedJson playerData = gson.fromJson(container.playerData(), VersionedJson.class);
        String inventoryData = container.inventoryData();

        switch (playerData.version()) {
            case 1 -> {
                PlayerDataDeserializedJsonContainer playerDataDeserializedJsonContainer = gson.fromJson(playerData.meta(), PlayerDataDeserializedJsonContainer.class);
                RunePlayer player = new RunePlayer(container.uuid(), RunePlayerManager.createDefaultSkills());
                PlayerRole role = new PlayerRole(BaseRole.valueOf(container.baseRole()), container.upgradedRole() != null ? UpgradedRole.valueOf(container.upgradedRole()) : null, SupporterLevel.LVL_0);

                // Map skills from container to EnumMap<SkillType, Skill>, filling missing skills with xp 0
                EnumMap<SkillType, Skill> mappedSkills = Arrays.stream(SkillType.values())
                        .collect(Collectors.toMap(
                                Function.identity(),
                                type -> container.skills().stream()
                                        .filter(s -> s.getType() == type)
                                        .findFirst()
                                        .orElse(new Skill(type, type.getDefaultXp())),
                                (a, b) -> b,
                                () -> new EnumMap<>(SkillType.class)
                        ));

                player.setDiscoveredTerritoryIds(playerDataDeserializedJsonContainer.discoveredTerritoryIds());
                player.setPouchCoins(playerDataDeserializedJsonContainer.pouchCoins(), true);
                player.setHealth(playerDataDeserializedJsonContainer.health(), RunePlayerHealthChangedEvent.Cause.INITIALIZATION);
                player.setSkills(mappedSkills);
                player.setSpecialEnergyPercent((int) (playerDataDeserializedJsonContainer.specialEnergy() * 100));
                player.setPrayerEnergy(playerDataDeserializedJsonContainer.prayerEnergy());
                player.setCompletedQuestIds(playerDataDeserializedJsonContainer.completedQuestIds());
                player.setActiveEffects(playerDataDeserializedJsonContainer.effects());
                player.setRole(role, false);
                player.setBombsContainer(container.bombsContainer());
                player.setSettings(container.settings());

                List<ItemStackSlotPair> inventory = ItemContainerContentSerializer.deserializeItemContainerContentSync(inventoryData);
                Location location = gson.fromJson(container.location(), Location.class);

                return new AbstractMap.SimpleEntry<>(player, new DeserializedPlayerEntity(inventory, location, false));
            }

            default ->
                    throw new IllegalArgumentException("Cannot deserialize player! Unknown json version: " + playerData.version());
        }

    }

    private static List<ItemStackSlotPair> getPlayerInventoryContent(Player player) {
        List<ItemStackSlotPair> itemPairs = new ArrayList<>();
        PlayerInventory inv = player.getInventory();

        for (int slot = 0; slot <= 40; slot++) {
            ItemStack item = inv.getItem(slot);

            if (item == null) {
                continue;
            }

            itemPairs.add(new ItemStackSlotPair(slot, item));
        }

        return itemPairs;
    }

    public static void setPlayerInventoryContent(Player player, List<ItemStackSlotPair> pairs) {
        PlayerInventory inv = player.getInventory();
        pairs.forEach(pair -> inv.setItem(pair.slot(), pair.itemStack()));
    }
}
