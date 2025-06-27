package org.zuma.examplePlugin.domain.item.constants;

public enum ItemType {
    MISC(0),
    EQUIPMENT(1),
    MATERIAL(2),
    QUEST(3),
    POTION(4),
    FOOD(5),
    WEAPON(6),
    TOOL(7),
    WEAPON_WITH_GEM_SOCKETS(8),
    EQUIPMENT_WITH_GEM_SOCKETS(9);

    private final int key;

    ItemType(int key) {
        this.key = key;
    }

    public int asNumber() {
        return key;
    }


}
