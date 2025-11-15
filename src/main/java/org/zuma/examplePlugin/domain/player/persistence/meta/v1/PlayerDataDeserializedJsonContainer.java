package org.zuma.examplePlugin.domain.player.persistence.meta.v1;

import org.zuma.examplePlugin.domain.player.TemporaryEffect;
import org.zuma.examplePlugin.domain.skill.Skill;

import java.util.List;

public record PlayerDataDeserializedJsonContainer(long pouchCoins, List<String> discoveredTerritoryIds,
                                                  int health, float specialEnergy, double prayerEnergy, List<String> completedQuestIds, List<TemporaryEffect> effects) {
}
