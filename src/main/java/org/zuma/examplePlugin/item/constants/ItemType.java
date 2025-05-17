package org.zuma.examplePlugin.item.constants;

public enum ItemType {
    MISC(0),
    GEAR(1),
    MATERIAL(2),
    QUEST(3);

    private final int key;

    ItemType(int key) {
        this.key = key;
    }

    public int asNumber() {
        return key;
    }


}
