package org.zuma.examplePlugin.domain.player.persistence;

import org.zuma.examplePlugin.domain.player.PlayerBombsContainer;
import org.zuma.examplePlugin.domain.skill.Skill;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public record SerializedPlayerContainer(UUID uuid, String username, List<Skill> skills, String baseRole,
                                        @Nullable String upgradedRole,
                                        String location, String playerData,
                                        String inventoryData, PlayerBombsContainer bombsContainer) {
}
