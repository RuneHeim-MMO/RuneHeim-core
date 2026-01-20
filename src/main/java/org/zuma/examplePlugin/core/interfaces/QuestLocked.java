package org.zuma.examplePlugin.core.interfaces;

import org.zuma.examplePlugin.domain.player.RunePlayer;

public interface QuestLocked {
    boolean playerMeetsQuestRequirements(RunePlayer player);
}
