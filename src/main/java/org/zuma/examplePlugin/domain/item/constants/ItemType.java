package org.zuma.examplePlugin.domain.item.constants;

public enum ItemType {
    MISC(0),
    EQUIPMENT(1),
    MATERIAL(2),
    QUEST(3),
    POTION(4),
    FOOD(5),
    MELEE_WEAPON(6),
    TOOL(7),
    MELEE_WEAPON_WITH_GEM_SOCKETS(8),
    EQUIPMENT_WITH_GEM_SOCKETS(9),
    GEM(10),
    MATERIAL_SCROLL(11),
    FAIRY_FOOD(12),
    FAIRY_FRUIT(13),
    FAIRY(14),
    EMPTY_CONTAINER(15),
    COINS(16),
    RANGED_WEAPON(17),
    ARROW(18),
    CURRENCY(19),
    CHANCE_ITEM(20);

    private final int key;

    ItemType(int key) {
        this.key = key;
    }

    public int asNumber() {
        return key;
    }


}
