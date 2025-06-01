package org.zuma.examplePlugin.domain.gui.constants;

public enum InventoryId {
    BASIC_FURNACE(0);
    
    private final int id;

    InventoryId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
