package org.zuma.examplePlugin.domain.gui.constants;

public enum InventoryId {
    BASIC_FURNACE(0),
    STOVE(1),
    FIRE(2);

    private final int id;

    InventoryId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
