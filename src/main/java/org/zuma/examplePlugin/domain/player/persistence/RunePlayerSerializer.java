package org.zuma.examplePlugin.domain.player.persistence;

import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
    private static final Gson gson = new Gson();

    public static SerializedPlayerContainer serializePlayer(RunePlayer runePlayer, Player player) {
        PlayerDataDeserializedJsonContainer container = new PlayerDataDeserializedJsonContainer(
                runePlayer.getSkills().values().stream().map(s -> new Skill(s.getType(), s.getXp())).toList(),
                runePlayer.getPouchCoins(), runePlayer.getDiscoveredTerritoryIds(), runePlayer.getHealth()
        );

        VersionedJson versionedPlayerData = new VersionedJson(gson.toJson(container), 1);

        String serializedPlayerData = gson.toJson(versionedPlayerData);
        String inventoryData = ItemContainerContentSerializer.serializeItemSlotPairs(getPlayerInventoryContent(player));

        return new SerializedPlayerContainer(runePlayer.getUUID(), player.getName(), serializedPlayerData, inventoryData);
    }

    public static AbstractMap.SimpleEntry<RunePlayer, List<ItemStackSlotPair>> deserializePlayer(SerializedPlayerContainer container) {
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

                List<ItemStackSlotPair> inventory = ItemContainerContentSerializer.deserializeItemContainerContent(inventoryData);

                return new AbstractMap.SimpleEntry<>(player, inventory);
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
