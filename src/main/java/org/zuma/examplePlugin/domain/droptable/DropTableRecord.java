package org.zuma.examplePlugin.domain.droptable;

import org.zuma.examplePlugin.domain.quest.constants.QuestId;

import javax.annotation.Nullable;
import java.util.List;

// activeForQuest is used to indicate that this drop table is only active when the specified quest is active.
// This is useful for quests that require specific items to be dropped only during the quest.
public record DropTableRecord(List<DropTableEntry> drops,
                              @Nullable QuestId activeForQuest) {
}
