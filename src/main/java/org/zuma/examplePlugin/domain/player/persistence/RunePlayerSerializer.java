package org.zuma.examplePlugin.domain.player.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.core.adapters.LocationAdapter;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.serializer.ItemContainerContentSerializer;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.persistence.meta.v1.PlayerDataDeserializedJsonContainer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

import java.util.AbstractMap;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.ArrayList;

import static org.zuma.examplePlugin.domain.player.RunePlayerManager.defaultSkills;

public class RunePlayerSerializer {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Location.class, new LocationAdapter())
            .create();

    public static SerializedPlayerContainer serializePlayerSync(RunePlayer runePlayer, Player player, boolean clearStateCache) {
        PlayerDataDeserializedJsonContainer container = new PlayerDataDeserializedJsonContainer(
                runePlayer.getSkills().values().stream().map(s -> new Skill(s.getType(), s.getXp())).toList(),
                runePlayer.getPouchCoins(),
                runePlayer.getDiscoveredTerritoryIds(),
                runePlayer.getHealth(),
                (float) runePlayer.getSpecialEnergyPercent() / 100,
                runePlayer.getPrayerEnergy(),
                runePlayer.getCompletedQuestIds() == null ? new ArrayList<>() : runePlayer.getCompletedQuestIds()
        );

        VersionedJson versionedPlayerData = new VersionedJson(gson.toJson(container), 1);

        String serializedPlayerData = gson.toJson(versionedPlayerData);
        String serializedLocation = gson.toJson(runePlayer.getGetSafeLocation() != null ? runePlayer.getGetSafeLocation() : player.getLocation());
        String inventoryData = ItemContainerContentSerializer.serializeItemSlotPairsSync(getPlayerInventoryContent(player), clearStateCache);
        return new SerializedPlayerContainer(runePlayer.getUUID(), player.getName(), serializedLocation, serializedPlayerData, inventoryData);
    }

    public static AbstractMap.SimpleEntry<RunePlayer, DeserializedPlayerEntity> deserializePlayerSync(SerializedPlayerContainer container) {
        VersionedJson playerData = gson.fromJson(container.playerData(), VersionedJson.class);
        String inventoryData = container.inventoryData();

        switch (playerData.version()) {
            case 1 -> {
                PlayerDataDeserializedJsonContainer playerDataDeserializedJsonContainer = gson.fromJson(playerData.meta(), PlayerDataDeserializedJsonContainer.class);
                EnumMap<SkillType, Skill> mappedSkills = playerDataDeserializedJsonContainer.skills().stream().collect(Collectors.toMap(Skill::getType, Function.identity(), (a, b) -> b, () -> new EnumMap<>(SkillType.class)));
                RunePlayer player = new RunePlayer(container.uuid(), new EnumMap<>(defaultSkills));

                player.setDiscoveredTerritoryIds(playerDataDeserializedJsonContainer.discoveredTerritoryIds());
                player.setPouchCoins(playerDataDeserializedJsonContainer.pouchCoins(), true);
                player.setHealth(playerDataDeserializedJsonContainer.health(), RunePlayerHealthChangedEvent.Cause.INITIALIZATION);
                player.setSkills(mappedSkills);
                player.setSpecialEnergyPercent((int) (playerDataDeserializedJsonContainer.specialEnergy() * 100));
                player.setPrayerEnergy(playerDataDeserializedJsonContainer.prayerEnergy());
                player.setCompletedQuestIds(playerDataDeserializedJsonContainer.completedQuestIds());

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
