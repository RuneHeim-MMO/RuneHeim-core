package org.zuma.examplePlugin.domain.gui.constants;

public enum InventoryId {
    ADMIN_NODE_CREATE(-100),
    BASIC_FURNACE(0),
    STOVE(1),
    FIRE(2),
    ANVIL(3);

    private final int id;

    InventoryId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
