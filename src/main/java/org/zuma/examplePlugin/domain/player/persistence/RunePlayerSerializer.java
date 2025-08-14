package org.zuma.examplePlugin.domain.player.persistence;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.serializer.ItemContainerContentSerializer;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.persistence.meta.v1.PlayerDatabaseContainer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.zuma.examplePlugin.domain.player.RunePlayerManager.defaultSkills;

public class RunePlayerSerializer {
    private static final Gson gson = new Gson();

    public static VersionedJson serializePlayer(RunePlayer player) {
        PlayerDatabaseContainer container = new PlayerDatabaseContainer(
                player.getSkills().values().stream().map(s -> new Skill(s.getType(), s.getXp())).toList(),
                player.getPouchCoins(), player.getDiscoveredTerritoryIds(), player.getHealth()
        );

        return new VersionedJson(gson.toJson(container), 1);
    }

    public static RunePlayer deserializePlayer(UUID uuid, VersionedJson json) {

        switch (json.version()) {
            case 1 -> {
                PlayerDatabaseContainer container = gson.fromJson(json.meta(), PlayerDatabaseContainer.class);
                EnumMap<SkillType, Skill> mappedSkills = container.skills().stream().collect(Collectors.toMap(Skill::getType, Function.identity(), (a, b) -> b, () -> new EnumMap<>(SkillType.class)));
                RunePlayer player = new RunePlayer(uuid, new EnumMap<>(defaultSkills));

                player.setDiscoveredTerritoryIds(container.discoveredTerritoryIds());
                player.setPouchCoins(container.pouchCoins(), true);
                player.setHealth(container.health(), RunePlayerHealthChangedEvent.Cause.INITIALIZATION);
                player.setSkills(mappedSkills);
                return player;
            }

            default ->
                    throw new IllegalArgumentException("Cannot deserialize player! Unknown json version: " + json.version());
        }

    }
}
