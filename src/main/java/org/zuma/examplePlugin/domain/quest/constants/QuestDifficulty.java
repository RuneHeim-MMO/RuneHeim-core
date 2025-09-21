package org.zuma.examplePlugin.domain.quest.constants;

public enum QuestDifficulty {
    EASY("§aEasy"), MEDIUM("§eMedium"), HARD("§cHard");

    private final String label;

    QuestDifficulty(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
