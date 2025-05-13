package org.zuma.examplePlugin.item.constants;

public enum ItemId {
    HOLY_LOG(0);

    private final int id;

    ItemId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
