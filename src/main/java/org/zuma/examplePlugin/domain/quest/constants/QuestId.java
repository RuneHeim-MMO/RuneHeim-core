package org.zuma.examplePlugin.domain.quest.constants;

public enum QuestId {
    TUTORIAL("tutorial"),
    SHEEP_SHEARER("sheep-shearer"),
    SAHARIJIN_ARENA_INTRO("saharijin-pvp-arena-intro"),
    BLACKSMITHS_RIGHT_HAND_I("blacksmiths-right-hand-i"),
    THE_SICK_CAMELS("the-sick-camels"),
    PROMISE_IN_A_BOX("promise-in-a-box"),

    WELCOME_TO_DRAKKAR_I("welcome-to-drakkar-i"),
    WELCOME_TO_DRAKKAR_II("welcome-to-drakkar-ii"),
    WELCOME_TO_DRAKKAR_III("welcome-to-drakkar-iii"),

    HOT_MESS_AT_THE_SMELTERY("smelters-assistant"),
    THREADS_OF_AID("threads-of-aid"),
    BONES_OF_LEVIATHAN("bones-of-leviathan"),

    SALTBOUND_SLEEP("saltbound-sleep");

    private final String id;

    QuestId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
