package org.zuma.examplePlugin.domain.player.persistence;

import javax.annotation.Nullable;
import java.util.UUID;

public record SerializedPlayerContainer(UUID uuid, String username, String baseRole, @Nullable String upgradedRole,
                                        String location, String playerData,
                                        String inventoryData) {
}
