package org.zuma.examplePlugin.domain.player.persistence;

import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.core.permissions.PlayerRole;

import java.util.UUID;

public record SerializedPlayerContainer(UUID uuid, String username, String role, String location, String playerData,
                                        String inventoryData) {
}
