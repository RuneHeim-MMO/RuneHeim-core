package org.zuma.examplePlugin.domain.player.persistence.meta.v1;

import org.zuma.examplePlugin.domain.skill.Skill;

import java.util.List;

public record PlayerDatabaseContainer(List<Skill> skills, long pouchCoins, List<String> discoveredTerritoryIds,
                                      int health) {
}
