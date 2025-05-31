package org.zuma.examplePlugin.domain.item.constants;

public enum ItemType {
    MISC(0),
    GEAR(1),
    MATERIAL(2),
    QUEST(3),
    MULTI_CONSUMABLE(4),
    SINGLE_CONSUMABLE(5);

    private final int key;

    ItemType(int key) {
        this.key = key;
    }

    public int asNumber() {
        return key;
    }


}
