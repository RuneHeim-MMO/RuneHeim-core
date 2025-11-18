package org.zuma.examplePlugin.domain.block.meta.v1;

import javax.annotation.Nullable;
import java.util.List;

public record QuestLockedMeta(@Nullable String lockedMessage,
                              @Nullable List<String> requiredCompletedQuestIds,
                              @Nullable List<String> requiredStartedQuestIds,
                              @Nullable List<String> requiredQuestStepActive) {
}
