package org.zuma.examplePlugin.domain.player.persistence;

import org.zuma.examplePlugin.core.meta.VersionedJson;

import java.util.UUID;

public record SerializedPlayerContainer(UUID uuid, String username, String location, String playerData,
                                        String inventoryData) {
}
