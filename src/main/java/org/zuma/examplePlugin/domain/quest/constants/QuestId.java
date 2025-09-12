package org.zuma.examplePlugin.domain.quest.constants;

public enum QuestId {
    SHEEP_SHEARER("sheep-shearer");

    private final String id;

    QuestId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
