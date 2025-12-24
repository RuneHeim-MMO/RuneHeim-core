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
    TOOL_WITH_GEM_SOCKETS(8),
    MELEE_WEAPON_WITH_GEM_SOCKETS(9),
    EQUIPMENT_WITH_GEM_SOCKETS(10),
    GEM(11),
    MATERIAL_SCROLL(12),
    FAIRY_FOOD(13),
    FAIRY_FRUIT(14),
    FAIRY(15),
    EMPTY_CONTAINER(16),
    COINS(17),
    RANGED_WEAPON(18),
    RANGED_WEAPON_WITH_SOCKETS(19),
    ARROW(20),
    CURRENCY(21),
    CHANCE_ITEM(22),
    XP_LAMP(23),
    ACCESSORY_AMULET(24),
    ACCESSORY_RING(25),
    REMAINS(26),
    MOUNT(27),
    COMPASS(28),
    STORAGE_ITEM(29),
    DECORATIVE_FURNITURE(30),
    INTERACTIVE_FURNITURE(31);

    private final int key;

    ItemType(int key) {
        this.key = key;
    }

    public int asNumber() {
        return key;
    }


}
