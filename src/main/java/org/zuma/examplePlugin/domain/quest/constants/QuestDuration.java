package org.zuma.examplePlugin.domain.quest.constants;

public enum QuestDuration {
    SHORT("§aShort"), MEDIUM("§eMedium"), LONG("§cLong"), VERY_LONG("§4Very Long");

    private final String label;

    QuestDuration(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
