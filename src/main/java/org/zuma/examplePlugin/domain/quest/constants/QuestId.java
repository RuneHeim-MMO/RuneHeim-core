package org.zuma.examplePlugin.domain.quest.constants;

public enum QuestId {
    TUTORIAL("tutorial"),
    SHEEP_SHEARER("sheep-shearer"),
    SAHARIJIN_ARENA_INTRO("saharijin-pvp-arena-intro"),
    BLACKSMITHS_RIGHT_HAND_I("blacksmiths-right-hand-i"),
    THE_SICK_CAMELS("the-sick-camels");

    private final String id;

    QuestId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
